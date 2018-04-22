package com.dam.core;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by geeche on 2018/2/3.
 */
public class ContainerLifeCycle extends AbstractLifeCycle implements Destroyable {

    private final List<Bean> beans = new CopyOnWriteArrayList<Bean>();

    private boolean started = false;

    @Override
    protected void doStart() throws Exception {
        for(Bean bean : beans){
            if(bean.bean instanceof LifeCycle){
                LifeCycle lc = (LifeCycle)bean.bean;
                if(!lc.isRunning()){
                    lc.start();
                }
            }
        }
        started = true;
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        started = false;
        super.doStop();
        final List<Bean> reverse = new ArrayList<Bean>(beans);
        Collections.reverse(reverse);
        for(Bean bean : reverse){
            if(bean.bean instanceof LifeCycle){
                LifeCycle lc = (LifeCycle)bean;
                if(!lc.isRunning()){
                    lc.stop();
                }
            }
        }
        started = true;
        super.doStart();
    }

    @Override
    public void destroy() {
        final List<Bean> reverse = new ArrayList<Bean>(beans);
        Collections.reverse(reverse);
        for(Bean bean : reverse){
            if(bean.bean instanceof Destroyable){
                Destroyable de = (Destroyable) bean;
                de.destroy();
            }
        }
    }

    public boolean Contains(Object obj){
        for(Bean bean : beans){
            if(bean.bean == obj){
                return true;
            }
        }
        return false;
    }


    public boolean addBean(Object obj){
        return addBean(obj,!((obj instanceof LifeCycle) && ((LifeCycle)obj).isStarted()));
    }
    public boolean addBean(Object obj,boolean managed){
        if(Contains(obj)){
           return false;
        }
        Bean bean = new Bean(obj);
        bean.managed = managed;
        beans.add(bean);

        if(obj instanceof LifeCycle){
            LifeCycle lc = (LifeCycle)obj;
            if(managed && started){
                try{
                    lc.start();
                }catch (Exception e){
                    throw new RuntimeException (e);
                }
            }
        }
        return true;
    }

    public void manage(Object bean){
        for(Bean b : beans){
            if(b.bean == bean){
                b.managed = true;
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public void ummanage(Object bean){
        for(Bean b : beans){
            if(b.bean == bean){
                b.managed = false;
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public Collection<Object> getBeans(){
        return getBeans(Object.class);
    }

    public <T>List<T> getBeans(Class<T> clazz){
        ArrayList<T> beanRes = new ArrayList<T>();
        for(Bean bean : beans){
            if(clazz.isInstance(bean.bean)){
                beanRes.add((T)bean.bean);
            }
        }
        return beanRes;
    }

    public <T>T getBean(Class<T> clazz){
        for(Bean bean : beans){
            if(clazz.isInstance(bean.bean)){
                return (T)bean.bean;
            }
        }
        return null;
    }

    public void removeBeans(){
        beans.clear();
    }

    public boolean removeBean(Object obj){
        Iterator<Bean> iterator = beans.iterator();
        while (iterator.hasNext()){
            Bean bean = iterator.next();
            if(bean.bean == obj){
                beans.remove(obj);
                return true;
            }
        }
        return false;
    }

    private class Bean{
        final Object bean;
        volatile boolean managed = true;
        Bean(Object bean){
            this.bean = bean;
        }
    }

}
