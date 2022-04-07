package org.bolivianjug.rx101.intro;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import io.smallrye.mutiny.subscription.MultiEmitter;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class IE0006 {
    public static void main(String[] args) {
        createProducer()
                //.skip().first(4)
                //.skip().where(n -> n < 5)
                //.select().first(6)
                .select().where(e -> e <= 7)
                .subscribe()
                .with(System.out::println, System.out::println, () -> System.out.println("completado"));//subscriber
    }

    private static Multi<Integer> createProducer() {
        return Multi.createFrom().emitter(e -> emitMulti(e), BackPressureStrategy.DROP);
    }

    private static void emitMulti(MultiEmitter<? super Integer> emitter) {
        System.out.println("Creando emitter...");
        int count = 0;
        while (count <= 10) {
            emitter.emit(count++);// emitiendo la data
        }
        emitter.complete();
    }
}
