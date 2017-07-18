package com.jvm.memorymgn;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by icekredit on 17-7-18.
 */
public class DirectMemoryOutOfMemory {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        int totalMemoryAllocated = 0;
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);

            while (true) {
                unsafe.allocateMemory(_1MB);

                totalMemoryAllocated ++;
            }
        }catch (OutOfMemoryError e){
            e.printStackTrace();

            System.out.println("目前一共分配了" + totalMemoryAllocated + "MB的内存！");
        }
    }
}


/*
java.lang.OutOfMemoryError
at sun.misc.Unsafe.allocateMemory(Native Method)
at com.jvm.memorymgn.DirectMemoryOutOfMemory.main(DirectMemoryOutOfMemory.java:21)
Java HotSpot(TM) 64-Bit Server VM warning: Attempt to deallocate stack guard pages failed.
Java HotSpot(TM) 64-Bit Server VM warning: INFO: os::commit_memory(0x00007f0fa3d98000, 12288, 0) failed; error='Cannot allocate memory' (errno=12)
目前一共分配了2124252MB的内存！
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 12288 bytes for committing reserved memory.
# An error report file with more information is saved as:
# /home/icekredit/Documents/workplace/JvmTestCase/hs_err_pid9932.log
*/
