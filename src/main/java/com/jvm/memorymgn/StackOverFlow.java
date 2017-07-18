package com.jvm.memorymgn;


/**
 * VM Args:-Xss128k 当指定为128k，报异常：The stack size specified is too small, Specify at least 228k，笔者最小使用256k
 */
public class StackOverFlow {
    private int stackDeep;

    private void recursiveCall() {
        this.stackDeep++;

        recursiveCall();
    }

    private static class RecursiveReference {
        private RecursiveReference another;

        private static int referCount = 0;

        private void refer() {
            referCount++;

            if (another != null) {
                another.refer();
            }
        }
    }

    private static void recursiveReference() {
        RecursiveReference currentRecursiveReference = new RecursiveReference();
        RecursiveReference anotherRecursiveReference = new RecursiveReference();

        currentRecursiveReference.another = anotherRecursiveReference;
        anotherRecursiveReference.another = currentRecursiveReference;

        currentRecursiveReference.refer();
    }

    private void recursiveCallWithBigStackFrame() {
        this.stackDeep++;

        double occupyPositionA;
        double occupyPositionB;
        double occupyPositionC;

        recursiveCallWithBigStackFrame();
    }

    private static class RecursiveReferenceWithBigStackFrame {
        private RecursiveReferenceWithBigStackFrame another;

        private static int referCount = 0;

        private void refer() {
            referCount++;

            double occupyPositionA;
            double occupyPositionB;
            double occupyPositionC;

            if (another != null) {
                another.refer();
            }
        }
    }

    private static void recursiveReferenceWithBigStackFrame() {
        RecursiveReferenceWithBigStackFrame currentRecursiveReference = new RecursiveReferenceWithBigStackFrame();
        RecursiveReferenceWithBigStackFrame anotherRecursiveReference = new RecursiveReferenceWithBigStackFrame();

        currentRecursiveReference.another = anotherRecursiveReference;
        anotherRecursiveReference.another = currentRecursiveReference;

        currentRecursiveReference.refer();
    }

    public static void main(String[] args) {
        StackOverFlow stackOverFlow = null;
        try {
            stackOverFlow = new StackOverFlow();
            stackOverFlow.recursiveCall();
        }catch (StackOverflowError e){
            //e.printStackTrace();

            System.out.println("使用方法循环调用栈深度：" + (stackOverFlow == null ? "null" : stackOverFlow.stackDeep));
        }

        try {
            recursiveReference();
        }catch (StackOverflowError e){
            //e.printStackTrace();

            System.out.println("使用对象循环引用栈深度：" + RecursiveReference.referCount);
        }


        try {
            stackOverFlow = new StackOverFlow();
            stackOverFlow.recursiveCallWithBigStackFrame();
        }catch (StackOverflowError e){
            //e.printStackTrace();

            System.out.println("使用方法循环调用(分配较大的方法栈帧)栈深度：" + (stackOverFlow == null ? "null" : stackOverFlow.stackDeep));
        }

        try {
            recursiveReferenceWithBigStackFrame();
        }catch (StackOverflowError e){
            //e.printStackTrace();

            System.out.println("使用对象循环引用(分配较大的方法栈帧)栈深度：" + RecursiveReferenceWithBigStackFrame.referCount);
        }
    }
}
