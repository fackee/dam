

import java.lang.reflect.Method;

public class Test {


    public static void main(String[] args) {
        Method[] methods = Ex.class.getDeclaredMethods();
        for(Method method : methods){
            Class clazz[] = method.getParameterTypes();
            for(Class c : clazz){
                try {
                    if(c.newInstance() instanceof String){
                        System.out.println(c.getName());
                    }
                } catch (InstantiationException e) {

                } catch (IllegalAccessException e) {

                }
            }
        }
    }

    class Ex{

        void t(String s){}
    }
}