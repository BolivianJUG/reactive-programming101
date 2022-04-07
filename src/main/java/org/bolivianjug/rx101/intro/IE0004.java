package org.bolivianjug.rx101.intro;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.MultiEmitter;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class IE0004 {
    public static void main(String[] args) {
        createProducer()// publisher
                .filter(IE0004::esPar) // filter
                .map(IE0004::doblarRestar) //mapper
                .subscribe()
                           //  data           // error              // completado
                .with(System.out::println, System.out::println, () -> System.out.println("completado"));//subscriber
    }

    private static Multi<Integer> createProducer() {
        return Multi.createFrom().emitter(e -> emitMulti(e));
    }

    private static void emitMulti(MultiEmitter<? super Integer> emitter) {
        System.out.println("Creando emitter...");
        int count = 0;
        while (count <= 10) {
            emitter.emit(count++);// emitiendo la data
        }
        System.out.println("enviando mensaje de completado");
        emitter.complete();// se completo la data

        emitter.emit(777);
    }

    public static boolean esPar(long n) {
        return n % 2 == 0;
    }

    private static long doblarRestar(long n) {
        System.out.println("numero entrante " + n);
        return n * 2 - 1;
    }
}
