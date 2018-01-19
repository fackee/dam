package httpserver.net.nio;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class EventQueue {

    private static final Queue<NioChannel> eventQueue = new ArrayBlockingQueue<NioChannel>(1024);

    public static void enQueue(NioChannel nioChannel){
        eventQueue.offer(nioChannel);
    }

    public static NioChannel deQueue(){
        return eventQueue.poll();
    }


}