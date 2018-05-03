package org.dam.bridge;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class LoadAppClasses{

//    public static void main(String[] args) {
//
//        //ApplicationClassLoader applicationClassLoader = new ApplicationClassLoader("F:/toylet/httpserver/");
//        try {
//            LoadAppJar.loadJars("F:/toylet/httpserver/");
//            LoadAppJar.getJarFiles().forEach(jarFile -> {
//                LoadAppJar.readJar(jarFile);
//            });
//            Map map = LoadAppJar.getClazzMap();
//            LoadAppJar.getClazzMap().entrySet().forEach(action ->{
//                action.getValue().entrySet().forEach(subAction ->{
//                    ApplicationClassLoader classLoader = new ApplicationClassLoader(subAction.getValue());
//                    try {
//                        Object obj = classLoader.loadClass(subAction.getKey()).newInstance();
//                        Method[] methods = obj.getClass().getMethods();
//                        for(Method method : methods){
//                            if(method.getName().equals("init")){
//                                String req = "nio req";
//                                String resp = "nio resp";
//                                try {
//                                    method.invoke(obj,req,resp);
//                                } catch (InvocationTargetException e) {
//
//                                }
//                            }
//                        }
//                    } catch (ClassNotFoundException e) {
//
//                    } catch (IllegalAccessException e) {
//
//                    } catch (InstantiationException e) {
//
//                    }
//                });
//            });
//        } catch (IOException e) {
//
//        }
////        try {
////           // Object obj = applicationClassLoader.loadClass("CostomClass").newInstance();
////           // System.out.println(obj.getClass().getName());
////        } catch (ClassNotFoundException e) {
////
////        } catch (IllegalAccessException e) {
////
////        } catch (InstantiationException e) {
////
////        }
//    }
}
