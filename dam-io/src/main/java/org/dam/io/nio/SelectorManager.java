package org.dam.io.nio;

import org.dam.io.EndPoint;
import org.dam.io.connection.Connection;
import org.dam.utils.lifecycle.AbstractLifeCycle;
import org.dam.utils.util.log.Logger;


import java.io.IOException;
import java.nio.channels.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by geeche on 2018/1/25.
 */
public abstract class SelectorManager extends AbstractLifeCycle {

    private SelectorWorker[] workers;
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
        for(int i=0;i<1;i++){
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



    public abstract boolean dispatch(Runnable runnable);

    public abstract void endPointUpgraded(SelectChannelEndPoint selectChannelEndPoint, Connection old);

    public abstract void closeEndPoint(SelectChannelEndPoint endPoint);

    public abstract void endPointOpend(SelectChannelEndPoint endPoint);

    public abstract Connection newConnection(SocketChannel channel, EndPoint endPoint, Object att);

    public abstract SelectChannelEndPoint newEndPoint(SocketChannel channel, SelectorWorker worker, SelectionKey key) throws IOException;

    public class SelectorWorker{

        private final int id;

        private final ConcurrentLinkedQueue<Object> workQueue = new ConcurrentLinkedQueue<Object>();

        private volatile Selector selector;

        private volatile Thread selectedThread;

        private Map<SelectChannelEndPoint,Object> endPoints = new ConcurrentHashMap<SelectChannelEndPoint,Object>(16);

        SelectorWorker(int acceptorId) throws IOException{
            id = acceptorId;
            selector = Selector.open();
        }

        public void addWork(Object work){
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
                        System.out.println(work + "endpoint doUpdatekey->registerSelf");
                        final SelectChannelEndPoint endPoint = (SelectChannelEndPoint)work;
                        channel = endPoint.getChannel();
                        endPoint.doUpdateKey();
                    }else if(work instanceof ChannelAndAttachment){
                        final ChannelAndAttachment caa = (ChannelAndAttachment) work;
                        final SelectableChannel sc = caa.channel;
                        channel = sc;
                        final Object att = caa.attachment;
                        if( (sc instanceof SocketChannel) && ((SocketChannel)sc).isConnected() ){
                            key = ((SocketChannel) sc).register(selector,SelectionKey.OP_READ,att);
                            SelectChannelEndPoint endPoint = createEndPoint((SocketChannel)sc,key);
                            key.attach(endPoint);
                            endPoint.schedule();
                        }else if(channel.isOpen()){
                            key = sc.register(selector,SelectionKey.OP_CONNECT);
                        }
                    }else if(work instanceof SocketChannel){
                        System.out.println("newly request,newly endpoint");
                        final SocketChannel socketChannel = (SocketChannel)work;
                        channel = socketChannel;
                        key = socketChannel.register(selector,SelectionKey.OP_READ,null);
                        SelectChannelEndPoint endPoint = createEndPoint(socketChannel,key);
                        key.attach(endPoint);
                        endPoint.schedule();
                    }else if(work instanceof ChangeTask){
                        ((Runnable)work).run();
                    }
                }
                int select = selector.selectNow();
                if(select == 0 && selector.selectedKeys().isEmpty()){

                }

                if(selector == null || !selector.isOpen()){
                    return;
                }
                for(SelectionKey selectionKey : selector.selectedKeys()){
                    System.out.println("selectedKeySiez:"+selector.selectedKeys().size());
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
                            if(selectionKey.isWritable() || selectionKey.isReadable()){
                                final SelectChannelEndPoint endPoint = (SelectChannelEndPoint)attachment;
                                System.out.println("foreach selectionKey    isReadable:"+selectionKey.isReadable()+"     isWritable:"+selectionKey.isWritable());
                                System.out.println("foreach selectionKey:" + endPoint + "   readBlock:"+endPoint.isReadBloking()+"  writeBlock:"+endPoint.isWriteBloking());
                                endPoint.schedule();
                            }
                        }else if(selectionKey.isConnectable()){
                            socketChannel = (SocketChannel) selectionKey.channel();
                            boolean connected = false;
                            try {
                                connected = socketChannel.finishConnect();
                            }catch (Exception e){
                                Logger.ERROR("");
                            }finally {
                                if(connected){
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                    SelectChannelEndPoint endPoint = createEndPoint(socketChannel,selectionKey);
                                    selectionKey.attach(endPoint);
                                    endPoint.schedule();
                                }else{
                                    selectionKey.cancel();
                                    socketChannel.close();
                                }
                            }
                        }else{
                            socketChannel = (SocketChannel)selectionKey.channel();
                            SelectChannelEndPoint endPoint = createEndPoint(socketChannel,selectionKey);
                            selectionKey.attach(endPoint);
                            if(selectionKey.isReadable()){
                                endPoint.schedule();
                            }
                            selectionKey = null;
                        }
                    }catch (Exception e){
                        if(selectionKey != null && !(selectionKey.channel() instanceof ServerSocketChannel) &&
                                selectionKey.isValid()){
                            selectionKey.cancel();
                        }
                    }
                }

                currentSelector.selectedKeys().clear();

            }catch (IOException e){
                //TODO doWork IOException
            }

        }

        private SelectChannelEndPoint createEndPoint(SocketChannel channel, SelectionKey key) throws IOException{
            SelectChannelEndPoint endPoint = newEndPoint(channel,this,key);
            endPointOpend(endPoint);
            endPoints.put(endPoint,this);
            return endPoint;
        }

        public void destroyEndPoint(SelectChannelEndPoint endPoint) {
            endPoints.remove(endPoint);
            closeEndPoint(endPoint);
        }

        public void wakeUp() {
            try {
                final Selector s = selector;
                if(s != null){
                    s.wakeup();
                }
            }catch (Exception e){
                addWork(new ChangeTask() {
                    @Override
                    public void run() {
                        renewSelector();
                    }
                });
                renewSelector();
            }

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

        public Selector getSelector() {
            return selector;
        }

        public SelectorManager getManager(){
            return SelectorManager.this;
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


    private interface ChangeTask extends Runnable{}
}
