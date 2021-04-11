package me.domida.play.cuncurrency;

import me.domida.play.model.Shop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .collect(Collectors.toList());

        shopPriceStr.stream().forEach(System.out::println);
    }

    @Test
    void simpleParallelStreamTest() {
        //getPrice block이 있다. 500mlisecond
        List<String> shopPriceStr = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(shop.getName())))
                .collect(Collectors.toList());

        shopPriceStr.stream().forEach(System.out::println);
    }

}
