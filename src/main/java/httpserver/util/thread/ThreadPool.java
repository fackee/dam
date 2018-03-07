package httpserver.util.thread;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public interface ThreadPool {

    public abstract boolean dispatch(Runnable runnable);

    public void join() throws InterruptedException;

    public int getThreads();

    public int getIdleThreads();

    public void stop();
}
