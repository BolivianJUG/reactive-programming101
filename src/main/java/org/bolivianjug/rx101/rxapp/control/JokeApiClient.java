package org.bolivianjug.rx101.rxapp.control;

import org.bolivianjug.rx101.rxapp.entity.Joke;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
@Path("/joke/Any")
@RegisterRestClient(configKey = "rest-api-client")
public interface JokeApiClient {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Joke getByLang(@QueryParam("lang") String lang);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    CompletionStage<Joke> getByLangAsync(@QueryParam("lang") String lang);
}
