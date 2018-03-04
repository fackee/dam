package httpserver.connector.nio;

import httpserver.connector.EndPoint;
import httpserver.connector.SelectChannelEndPoint;
import httpserver.core.AbstractLifeCycle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by geeche on 2018/1/25.
 */
public abstract class SelectorManager extends AbstractLifeCycle{

    private SelectorManager.SelectorWorker[] workers;
    private int workLength;
    private volatile int work = 0;


    public void setWorkLength(int workLength) {
        this.workLength = workLength;
    }

    public int getWorkLength() {
        return workLength;
    }

    public SelectorWorker getWorker() {
        return workers[work];
    }

    public void rigister(SocketChannel channel,Object att){
        int w = work++;
        if(w <0){
            w = -w;
        }
        w = w%workLength;
        final SelectorWorker[] selectorWorkers = this.workers;
        if(selectorWorkers != null){
            SelectorWorker worker = selectorWorkers[w];
            if(att == null){
                worker.addWork(channel);
            }else{
                worker.addWord(channel,att);
            }
            worker.wakeUp();
        }
    }

    public void register(SocketChannel channel){
        rigister(channel,null);
    }

    @Override
    protected void doStart() throws Exception {
        workers = new SelectorWorker[workLength];
        for(int i=0;i<workers.length;i++){
            workers[i] = new SelectorWorker(i);
        }
        super.doStart();
        for(int i=0;i<getWorkLength();i++){
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SelectorWorker[] workerArr = workers;
                        if(workerArr == null){
                            return;
                        }
                        SelectorWorker worker = workerArr[id];
                        while (isRunning()){
                            try {
                                worker.doWork();
                            }catch (IOException e){

                            }catch (Exception e){

                            }
                        }
                    }finally {

                    }
                }
            }).start();

        }
    }

    public abstract void endPointUpgraded(SelectChannelEndPoint selectChannelEndPoint, Connection old);

    public class SelectorWorker{
        private final int id;

        private final ConcurrentLinkedQueue<Object> workQueue = new ConcurrentLinkedQueue<Object>();

        private volatile Selector selector;

        private volatile Thread selectedThread;

        SelectorWorker(int acceptorId) throws IOException{
            id = acceptorId;
            selector = Selector.open();
        }

        public void addWork(Object work){
            System.out.println("addword");
            workQueue.add(work);
        }

        public void addWord(SelectableChannel channel,Object att){
            if(att == null){
                addWork(channel);
            }
            else if(att instanceof EndPoint){
                addWork(att);
            }else {
                addWork(new ChannelAndAttachment(channel,att));
            }
        }

        public void doWork() throws IOException{
            try {
                selectedThread = Thread.currentThread();
                final Selector currentSelector = selector;
                if(currentSelector == null){
                    return;
                }
                Object work;
                int works = workQueue.size();
                while ( works-- >0 && (work = workQueue.poll())!=null){
                    Channel channel = null;
                    SelectionKey key = null;
                    if(work instanceof EndPoint){
                        System.out.println("read");
                        final SelectChannelEndPoint endPoint = (SelectChannelEndPoint)work;
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel = endPoint.getChannel();
                        ((SocketChannel)channel).read(byteBuffer);
                        System.out.println(new String(byteBuffer.array()));
                        byteBuffer.clear();
                        endPoint.doUpdateKey();
                    }else if(work instanceof ChannelAndAttachment){
                        System.out.println("regread");
                        final ChannelAndAttachment caa = (ChannelAndAttachment) work;
                        final SelectableChannel sc = caa.channel;
                        channel = sc;
                        final Object att = caa.attachment;
                        if( (sc instanceof SocketChannel) && ((SocketChannel)sc).isConnected() ){
                            key = ((SocketChannel) sc).register(selector,SelectionKey.OP_READ,att);
                            SelectChannelEndPoint endPoint = createEndPoint((SocketChannel)sc,key);
                            key.attach(endPoint);
                            endPoint.excute();
                        }else if(channel.isOpen()){
                            key = sc.register(selector,SelectionKey.OP_CONNECT);
                        }
                    }else if(work instanceof SocketChannel){
                        System.out.println("regread");
                        final SocketChannel socketChannel = (SocketChannel)work;
                        channel = socketChannel;
                        key = socketChannel.register(selector,SelectionKey.OP_READ,null);
                        SelectChannelEndPoint endPoint = createEndPoint(socketChannel,key);
                        key.attach(endPoint);
                        endPoint.excute();
                    }
                }
            }catch (IOException e){

            }

            int select = selector.selectNow();
            if(select == 0 && selector.selectedKeys().isEmpty()){

            }

            if(selector == null || !selector.isOpen()){
                return;
            }
            for(SelectionKey selectionKey : selector.selectedKeys()){
                SocketChannel socketChannel = null;
                try {
                    if(!selectionKey.isValid()){
                        selectionKey.cancel();
                        SelectChannelEndPoint endPoint = (SelectChannelEndPoint)selectionKey.attachment();
                        if(endPoint!=null){
                            endPoint.doUpdateKey();
                        }
                        continue;
                    }
                    Object attachment = selectionKey.attachment();
                    if(attachment instanceof SelectChannelEndPoint){
                        if(selectionKey.isReadable() || selectionKey.isWritable()){
                            ((SelectChannelEndPoint)attachment).excute();
                        }
                    }else if(selectionKey.isConnectable()){
                        socketChannel = (SocketChannel) selectionKey.channel();

                    }
                }catch (Exception e){

                }
            }
        }

        private SelectChannelEndPoint createEndPoint(SocketChannel channel, SelectionKey key) throws IOException{
            return new SelectChannelEndPoint(channel,this,key);
        }

        public void wakeUp() {
            try {
                final Selector s = selector;
                if(s != null){
                    s.wakeup();
                }
            }catch (Exception e){
                renewSelector();
            }
            renewSelector();
        }

        private void renewSelector() {
            try {
                synchronized (this){
                    Selector s = selector;
                    if(s == null){
                        return;
                    }
                    final Selector newSelector = Selector.open();
                    for(SelectionKey key : s.keys()){
                        if(!key.isValid() || key.interestOps() == 0){
                            continue;
                        }
                        final SelectableChannel channel = key.channel();
                        final Object att = key.attachment();

                        if(att == null){
                            addWork(channel);
                        }else{
                            addWord(channel,att);
                        }
                        selector.close();
                        selector = newSelector;
                    }
                }
            }catch (Exception e){
                throw new RuntimeException("recreating selector",e);
            }
        }
    }


    private static class ChannelAndAttachment{
        final SelectableChannel channel;
        final Object attachment;

        public ChannelAndAttachment(SelectableChannel channel, Object attachment)
        {
            this.channel = channel;
            this.attachment = attachment;
        }
    }

}
