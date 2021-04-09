package me.domida.play.model;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    Random random = new Random();

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    public Future<Double> getPriceErrorAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                throw new RuntimeException("Exception propagation");
            } catch (RuntimeException r) {
                futurePrice.completeExceptionally(r);
            }
        }).start();
        return futurePrice;
    }

    private double calculatePrice(String product) {
        Delay.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
