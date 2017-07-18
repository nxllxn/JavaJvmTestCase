package com.jvm.memorymgn;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * VMArgs -XX:PermSize=10M-XX:MaxPermSize=10M
 *
 * Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
 * Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0
 */
public class JavaMethodAreaOutOfMemory {
    public static void main(String[] args) {
        try {
            while(true){
                Enhancer enhancer=new Enhancer();
                enhancer.setSuperclass(OOMObject.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor(){
                    public Object intercept(Object obj, Method method, Object[]args, MethodProxy proxy)throws Throwable{
                        return proxy.invokeSuper(obj,args);
                    }
                });
                enhancer.create();
            }
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
    }

    private static class OOMObject{
        public OOMObject() {
        }
    }
}
