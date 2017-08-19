package com.jvm.memorymgn;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-XX:PermSize=10M-XX:MaxPermSize=10M  笔者测试环境为java8,无法发生溢出
 */
public class RuntimeConstantPoolOutOfMemory {
    public static void main(String[] args) {
        showJavaMagic();

        //使用List保持着常量池引用,避免Full GC回收常量池行为
        List<String> strPool = new ArrayList<String>();

        long byteCount = 0;

        try {
            //10MB的PermSize在integer范围内足够产生OOM了
            int i = 0;

            String str;
            while (true) {
                str = String.valueOf(i++);

                byteCount += str.length();

                strPool.add(str.intern());
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();

            System.out.println(String.format("共创建了%s个字符串，共%s字节", strPool.size(), byteCount));
        }
    }

    public static void showJavaMagic() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
