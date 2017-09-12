package me.zeyuan.lib.adapter_nonrestful.example;

import me.zeyuan.adapter_nonrestful.NonRESTful;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Service {

    @NonRESTful
    @GET("/robot/index")
    Observable<Answer> ask(
            @Query("key") String key,
            @Query("info") String question
    );
}
