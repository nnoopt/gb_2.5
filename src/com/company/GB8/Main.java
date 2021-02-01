package com.company.GB8;
import java.util.Arrays;

public class Main {


       static final int size = 10000000;
       static final int h = size / 2;


    public static void main(String[] args) {


        float[] arr = new float[size];

        fillArr(arr);
        formula1(arr);

        fillArr(arr);
        formula2(arr);
    }


    public static void fillArr (float arr[] ){
        Arrays.fill(arr, 1);
    }

    public static void formula1 (float [] arr){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Formula1 Total time: " + (endTime - startTime) + "ms");
        System.out.println(arr[h]);
    }

    public static void formula2 (float [] arr){
        long startTime = System.currentTimeMillis();
        float [] a1 = new float[h];
        float [] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a2[i] = (float)(arr[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long endTime = System.currentTimeMillis();

        System.out.println("Formula2 Total time: " + (endTime - startTime) + "ms");
        System.out.println(arr[h]);
    }
}
