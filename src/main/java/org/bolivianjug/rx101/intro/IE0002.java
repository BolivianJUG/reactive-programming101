package org.bolivianjug.rx101.intro;

import io.smallrye.mutiny.Multi;

import java.time.Duration;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class IE0002 {
    public static void main(String[] args) {
        Multi.createFrom().ticks().every(Duration.ofSeconds(1)) // ticks del publisher
                .filter(IE0002::esPar) // filter
                .map(IE0002::doblarRestar) //mapper
                .subscribe().with(System.out::println);//subscriber
        sleep(10_000);
    }

    public static boolean esPar(long n) {
        return n % 2 == 0;
    }

    private static long doblarRestar(long n) {
        System.out.println("numero entrante " + n);
        return n * 2 - 1;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
