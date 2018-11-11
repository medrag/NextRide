package com.example.ragui.nextstation.remoteServices;

import com.example.ragui.nextstation.model.Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LigneService
{
    @GET("/api/routers/default/index/routes")
    Call<List<Ligne>> getTAGLignes();
}
