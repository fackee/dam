package com.dam.core;

/**
 * Created by geeche on 2018/1/25.
 */
public interface LifeCycle {

    void start();
    void stop();

    boolean isRunning();
    boolean isStarted();
    boolean isStarting();
    boolean isStoped();
    boolean isStopping();
    boolean isFailed();


    void addLifeCycleListener(LifeCycle.Listener listener);
    void removeLifeCycleListener(LifeCycle.Listener listener);

    public interface Listener {
        public void LifeCycleFail(LifeCycle event, Throwable cause);
        public void LifeCycleStarting(LifeCycle event);
        public void LifeCycleStarted(LifeCycle event);
        public void LifeCycleStopping(LifeCycle event);
        public void LifeCycleStopped(LifeCycle event);
    }
}
