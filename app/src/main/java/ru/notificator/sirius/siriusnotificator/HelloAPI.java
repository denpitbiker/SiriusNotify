package ru.notificator.sirius.siriusnotificator;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HelloAPI {
    @GET("/api/v1/hello")
    Single<Hellomsg> gethellomsg();

}