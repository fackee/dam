package httpserver;

import httpserver.message.ByteBufferHandler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferWriter implements Runnable{

    private ByteBuffer byteBuffer;

    private String message;

    private  ByteBufferHandler byteBufferHandler;

    public BufferWriter(ByteBuffer byteBuffer,String message,ByteBufferHandler byteBufferHandler){
        this.byteBuffer = byteBuffer;
        this.message = message;
        this.byteBufferHandler = byteBufferHandler;
    }


    @Override
    public void run() {
        synchronized (byteBuffer){
            if(byteBufferHandler.expendMessage(message.getBytes())){
                System.out.println("writeMessage:" + message);
                byteBuffer.notifyAll();
            }else{
                try {
                    byteBuffer.wait();
                } catch (InterruptedException e) {

                }
            }
        }
    }

    static class Pro{
        List<String> list;
        Pro(List<String> list){
            this.list = list;
        }
        public void prod(String s){
            if(list.size() > 10){
                try {
                    synchronized (list){
                        list.wait();
                    }
                } catch (InterruptedException e) {

                }
            }else{
                System.out.println("add"+list.size());
                list.add(s);
                synchronized (list){
                    list.notifyAll();
                }

            }
        }
    }
    static class Com{
        List<String> list;
        Com(List<String> list){
            this.list = list;
        }
        public void coms(){
            if(list.size() > 5){
                list.remove(list.size()-1);
                System.out.println("remove" + list.size());
            }else if(list.size() < 10){
                synchronized (list){
                    list.notifyAll();
                }

            }else if(list.size()==0){
                try {
                    synchronized (list){
                        list.wait();
                    }

                } catch (InterruptedException e) {

                }
            }
        }
    }

    static class Curr{

        public void w(){
            synchronized (this){
                try {
                    this.wait();
                    System.out.println("wait");
                } catch (InterruptedException e) {

                }
            }
        }

        public void n(){
            System.out.println("notify");
            this.notifyAll();
        }

    }

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>(10);
//        Pro p = new Pro(list);
//        Com c = new Com(list);
//        for(int i=0;i<100;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    p.prod("ppp");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    c.coms();
//                }
//            }).start();
//        }
        Curr curr = new Curr();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    curr.w();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    curr.n();
                }
            }).start();
        }
    }
}