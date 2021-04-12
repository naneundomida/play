package me.domida.play.cuncurrency;

import me.domida.play.model.Shop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TestNonBlockFuture {

    static List<Shop> shops;

    @BeforeAll
    static void beforeAll() {
        shops = Arrays.asList(new Shop("BestPractice"),
                new Shop("saveus"),
                new Shop("deliberyus"),
                new Shop("buyItAll"),
                new Shop("kyokyokyo"));
    }

    @Test
    void simpleBlockStreamTest() {
        //getPrice block이 있다. 500mlisecond
        List<String> shopPriceStr = shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(shop.getName())))
                .collect(toList());

        shopPriceStr.stream().forEach(System.out::println);
    }

    @Test
    void simpleParallelStreamTest() {
        //getPrice block이 있다. 500mlisecond
        List<String> shopPriceStr = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(shop.getName())))
                .collect(toList());

        shopPriceStr.stream().forEach(System.out::println);
    }

    @Test
    void futureFindPriceTest() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<String>> collect = shops.stream()
                .map(shop -> executorService.submit(() -> {
                    return String.format("%s price is %.2f", shop.getName(), shop.getPrice(shop.getName()));
                }))
                .collect(toList());

        List<String> prices = collect.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(toList());

        prices.forEach(System.out::println);
    }


    @Test
    void completableFutureFindPriceTest() {
        List<CompletableFuture<String>> priceFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(shop.getName()))))
                .collect(toList());

        List<String> collect = priceFuture.stream()
                .map(CompletableFuture::join) //get과 join의 차이는 checked exception 던지냐 안던지냐 차이
                .collect(toList());

        collect.forEach(System.out::println);
    }

}
