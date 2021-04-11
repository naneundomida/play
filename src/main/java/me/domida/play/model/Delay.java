package me.domida.play.model;

public class Delay {

    public static void delay() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
