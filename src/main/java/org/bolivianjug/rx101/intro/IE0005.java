package org.bolivianjug.rx101.intro;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.smallrye.mutiny.subscription.Cancellable;
import io.smallrye.mutiny.subscription.MultiEmitter;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class IE0005 {
    public static void main(String[] args) {
        Multi<Integer> producer = createProducer();// publisher
        Cancellable completado = producer
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                //.filter(IE0005::esPar) // filter
                .map(IE0005::doblarRestar) //mapper
                .subscribe()
                .with(e -> {//  data
                            System.out.println(e);
                            System.out.println(Thread.currentThread());
                        }, System.out::println,// error
                        () -> System.out.println("completado")// completado
                );//subscriber

        sleep(5_000);
        completado.cancel();// finaliza la suscripcion
        System.out.println("suscripcion finalizada");
        sleep(10_000);
        System.exit(0);
    }

    private static Multi<Integer> createProducer() {
        return Multi.createFrom().emitter(e -> emitMulti(e));
    }

    private static void emitMulti(MultiEmitter<? super Integer> emitter) {
        System.out.println("Creando emitter...");
        int count = 0;
        while (count <= 10) {
            emitter.emit(count++);// emitiendo la data
            sleep(1_000);
        }
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
