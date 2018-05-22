package org.dam.utils.util.thread;

import java.util.concurrent.ThreadFactory;

public class BasicThreadFactory implements ThreadFactory {

    private boolean deamon;
    private String name;
    private int priority;

    public BasicThreadFactory(Builder builder){
        this.deamon = builder.deamon;
        this.name = builder.name;
        this.priority = builder.priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(deamon);
        thread.setName(name);
        thread.setPriority(priority);
        return thread;
    }

    public static class Builder{
        private boolean deamon;
        private String name;
        private int priority;

        public Builder(){}

        public Builder deamon(boolean deamon) {
            this.deamon = deamon;
            return this;
        }

        public Builder namePattern(String name) {
            this.name = name;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }
        public ThreadFactory builde(){
            return new BasicThreadFactory(this);
        }
    }
}
