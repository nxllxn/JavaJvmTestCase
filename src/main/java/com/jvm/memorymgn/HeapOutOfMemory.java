package com.jvm.memorymgn;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -verbose:gc -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class HeapOutOfMemory {
    public static class SomeClass{
        public SomeClass() {
        }
    }

    public static void main(String[] args) {
        List<SomeClass> someClasses = new ArrayList<SomeClass>();
        try {

            while (true){
                someClasses.add(new SomeClass());
            }
        }catch (OutOfMemoryError e){
            System.out.println("Total instance created:" + someClasses.size());
        }
    }
}
