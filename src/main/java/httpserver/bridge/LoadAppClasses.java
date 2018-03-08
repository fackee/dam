package httpserver.bridge;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class LoadAppClasses extends ClassLoader{



    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}
