package httpserver.util.thread;

import java.util.concurrent.Executor;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public interface ThreadPool extends Executor{

    public void join() throws InterruptedException;

    public int getThreads();

    public int getIdleThreads();

    public void stop();
}
