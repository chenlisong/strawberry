package com.example.demo;

/**

 * @description: <p>内存分配比较</p>
 */
public class EscapeAnalysisTest {
    public static void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void allocMem() {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }

    private static class Foo {
        private int x;
        private static int counter;
        public Foo() {
            x = (++counter);
        }
    }

    public static void createObj() {
        long start = System.nanoTime();
        for (int i = 0; i < 1000 * 1000 * 10; ++i) {
            Foo foo = new Foo();
        }
        long end = System.nanoTime();
        System.out.println("Time cost is " + (end - start));
    }

    /**
     * -server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC
     * -server -Xmx10m -Xms10m -XX:-DoEscapeAnalysis -XX:+PrintGC
     * @param args
     */
    public static void main(String[] args) throws Exception{
//        allocMem();
        createObj();
        while(true){
            Thread.sleep(3000L);
        }
    }
}
