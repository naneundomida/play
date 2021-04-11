package me.domida.play.cuncurrency;

import me.domida.play.model.Shop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestCompletableFuture {

    @Test
    void simpleFutureCallableTest() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> submit = executor.submit(() -> {
            return new SecureRandom().nextInt();
        });
        System.out.println(new SecureRandom().nextInt());
        System.out.println(submit.isDone());
        try {
            System.out.println(submit.get());
            assertThat(submit.isDone()).isTrue();
            System.out.println(submit.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void simpleCompletableFutureTest() {
        Shop shop = new Shop();
        Future<Double> favoriteProduct = shop.getPriceAsync("my favorite product");

        try {
            System.out.println(favoriteProduct.isDone());
            Double ayncPrice = favoriteProduct.get();
            System.out.printf("Price is %.2f%n" , ayncPrice);
            assertThat(favoriteProduct.isDone()).isTrue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void simpleCompletableFutureErrorPropagationTest() {
        Shop shop = new Shop();
        Future<Double> product = shop.getPriceErrorAsync("my favortie product");
        System.out.println(product.isDone());
        assertThrows(ExecutionException.class, () -> product.get());

        try {
            product.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertThat(product.isDone()).isTrue();
    }

    @Test
    void supplyAyncFutureTest() {
        Shop shop = new Shop();
        Future<Double> product = shop.getSupplyAsync("my favorite product");

        try {
            Double aDouble = product.get();
            assertThat(aDouble).isNotNull();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
