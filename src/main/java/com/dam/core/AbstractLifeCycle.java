package com.dam.core;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by geeche on 2018/1/25.
 */
public abstract class AbstractLifeCycle implements LifeCycle {

    private static final LifeState STARTED = LifeState.STARTED;
    private static final LifeState STARTING = LifeState.STARTING;
    private static final LifeState STOPPED = LifeState.STOPPED;
    private static final LifeState STOPPING = LifeState.STOPPING;
    private static final LifeState FAILED = LifeState.FAILED;

    private final Object lock = new Object();

    private final int S_FAILED = -1,S_STOPPED = 0,S_STOPPING = 1,S_STARTING = 2,S_STARTED = 3;

    private volatile int state = S_STOPPED;

    protected final CopyOnWriteArrayList<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    protected void doStart() throws Exception{}

    protected void doStop() throws Exception{}

    @Override
    public void start() {
        synchronized (lock){
            try {
                if(state == S_STARTED || state == S_STARTING){
                    return;
                }
                setStarting();
                doStart();
                setStarted();
            }catch (Exception e){
                setFailed(e);
            }catch (Error e){
                setFailed(e);
            }
        }
    }

    @Override
    public void stop() {
        synchronized (lock){
            try {
                if(state == S_STOPPED || state == S_STOPPING){
                    return;
                }
                setStopping();
                doStop();
                setStopped();
            }catch (Exception e){
                setFailed(e);
            }catch (Error e){
                setFailed(e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return state == S_STARTED || state == S_STARTING;
    }

    @Override
    public boolean isStarted() {
        return state == S_STARTED;
    }

    @Override
    public boolean isStarting() {
        return state == S_STARTING;
    }

    @Override
    public boolean isStoped() {
        return state == S_STOPPED;
    }

    @Override
    public boolean isStopping() {
        return state == S_STOPPING;
    }

    @Override
    public boolean isFailed() {
        return state == S_FAILED;
    }

    @Override
    public void addLifeCycleListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLifeCycleListener(Listener listener) {
        listeners.remove(listener);
    }

    public String getState(){
        switch (state){
            case S_FAILED :
                return FAILED.name();
            case S_STARTED :
                return STARTED.name();
            case S_STARTING :
                return STARTING.name();
            case S_STOPPED :
                return STOPPED.name();
            case S_STOPPING :
                return STOPPING.name();
            default:
        }
        return null;
    }

    public static String getState(LifeCycle lifeCycle){
        if(lifeCycle.isStarted()){
            return STARTED.name();
        }
        if(lifeCycle.isStarting()){
            return STARTING.name();
        }
        if(lifeCycle.isStoped()){
            return STOPPED.name();
        }
        if(lifeCycle.isStopping()){
            return STOPPING.name();
        }
        return FAILED.name();
    }

    private void setStarting(){
        state = S_STARTING;
        for(Listener listener : listeners){
            listener.LifeCycleStarting(this);
        }
    }

    private void setStarted(){
        state = S_STARTED;
        for(Listener listener : listeners){
            listener.LifeCycleStarted(this);
        }
    }

    private void setStopping(){
        state = S_STOPPING;
        for(Listener listener : listeners){
            listener.LifeCycleStopping(this);
        }
    }

    private void setStopped(){
        state = S_STOPPED;
        for(Listener listener : listeners){
            listener.LifeCycleStopped(this);
        }
    }

    private void setFailed(Throwable cause){
        state = S_FAILED;
        for (Listener listener : listeners){
            listener.LifeCycleFail(this,cause);
        }
    }

    enum LifeState{
        STARTED, STARTING, STOPPED, STOPPING, FAILED;
    }

    private static abstract class AbstractLifeCycleListener implements Listener{
        @Override
        public void LifeCycleFail(LifeCycle event, Throwable cause) {

        }

        @Override
        public void LifeCycleStarting(LifeCycle event) {

        }

        @Override
        public void LifeCycleStarted(LifeCycle event) {

        }

        @Override
        public void LifeCycleStopping(LifeCycle event) {

        }

        @Override
        public void LifeCycleStopped(LifeCycle event) {

        }
    }

}
