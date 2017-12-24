package com.akhil.akhildixit.picmatrix.Fragments.camera;

/**
 * Created by Akhil Dixit on 12/19/2017.
 */ public class Wait {
    public static void oneSec() {
        try {
            Thread.currentThread().sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void manySec(long s) {
        try {
            Thread.currentThread().sleep(s * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}