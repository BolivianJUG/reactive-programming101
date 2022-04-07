package org.bolivianjug.rx101.intro;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.MultiEmitter;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class IE0003 {
    public static void main(String[] args) {
        createProducer()// publisher
                .filter(IE0003::esPar) // filter
                .map(IE0003::doblarRestar) //mapper
                .subscribe().with(e -> {
                    System.out.println(Thread.currentThread());
                    System.out.println(e);
                });//subscriber
    }

    private static Multi<Integer> createProducer() {
        return Multi.createFrom().emitter(e -> emitMulti(e));
    }

    private static void emitMulti(MultiEmitter<? super Integer> emitter) {
        System.out.println("Creando emitter...");
        int count = 0;
        while (count <= 10) {
            emitter.emit(count++);
        }
    }

    public static boolean esPar(long n) {
        return n % 2 == 0;
    }

    private static long doblarRestar(long n) {
        System.out.println("numero entrante " + n);
        return n * 2 - 1;
    }
}
