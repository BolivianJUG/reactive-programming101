package org.bolivianjug.rx101.rxapp.boundary;

import io.smallrye.mutiny.Multi;
import org.bolivianjug.rx101.rxapp.control.JokeApiClient;
import org.bolivianjug.rx101.rxapp.entity.Joke;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.Objects;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
@Path("")
public class IE0007 {
    private static final Logger LOG = LoggerFactory.getLogger(IE0007.class);
    @RestClient
    JokeApiClient jokeApiClient;

    @GET
    @Path("/joke/lang/{lang}")
    @Produces(MediaType.APPLICATION_JSON)
    public Joke getJoke(@PathParam("lang") String lang) {
        return jokeApiClient.getByLang(lang);
    }

    @GET
    @Path("/joke/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Joke> getJokeStream() {
        return getEventJokeStream();
    }

    private Multi<Joke> getEventJokeStream() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(10))
                .map(c -> {
                    LOG.info("counter: {}", c);
                    if (c % 5 == 0) {
                        return "es";
                    } else {
                        return "en";
                    }
                })
                .onItem().transform(lang -> jokeApiClient.getByLang(lang))
                .onItem().transform(x -> {
                    LOG.info("Message consumed : {}", x);
                    if (Objects.isNull(x.setup) && Objects.isNull(x.delivery)) {
                        LOG.info("not a good joke!!");
                        throw new RuntimeException("not a good joke!!");
                    }
                    return x;
                })
                .onFailure().invoke(e -> {
                    LOG.warn("Exception on stream processing, {}", e.getMessage());
                })
                .onFailure()
                .retry()
                .withBackOff(Duration.ofMillis(100), Duration.ofSeconds(10))
                //.indefinitely()
                .atMost(5)
                //.recoverWithItem( () -> return new Joke())
                ;
    }
}
