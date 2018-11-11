package com.example.ragui.nextstation.remoteServices;

import com.example.ragui.nextstation.model.Route;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RouteService
{
    @GET("/api/routers/default/index/routes/{code}/clusters")
    Call<List<Route>> getRouteByCode(@Path("code") String code);
}
