package httpserver.bridge.classloader;

import java.io.*;

/**
 * Created by rocky on 2018/3/6.
 */
public class ApplicationClassLoader extends ClassLoader{

    private String webAppPath;

    private static final String CLASS_SUFFIX = ".class";

    public ApplicationClassLoader(String webAppPath){
        this.webAppPath = webAppPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        String className = getClassName(name);

        File file = new File(webAppPath,className);

        try {

            FileInputStream inputStream = inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = 0;
            while ((len = inputStream.read()) != -1) {
                outputStream.write(len);
            }
            byte[] classData = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();
            return defineClass(name,classData,0,classData.length);
        }catch (IOException ioe){

        }
        return super.loadClass(name);
    }

    private String getClassName(String name){
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name + CLASS_SUFFIX;
        }
        return name;
    }
}
