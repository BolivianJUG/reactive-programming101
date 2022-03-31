package org.bolivianjug.rx101.intro;

import io.reactivex.Flowable;
import io.smallrye.mutiny.Multi;

import java.util.stream.Stream;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
// Java Streams vs Reactive Streams
public class IE0001 {
    public static void main(String[] args) {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8};
        //Java Stream
        Stream.of(integers)
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .forEach(System.out::println);
        System.out.println("************* RxJava");
        Flowable.fromArray(integers)
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .subscribe(System.out::println);
        System.out.println("************* Mutiny");
        Multi.createFrom().items(integers)
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .subscribe().with(System.out::println);
    }
}
