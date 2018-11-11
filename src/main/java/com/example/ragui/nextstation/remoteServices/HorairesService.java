package com.example.ragui.nextstation.remoteServices;

import com.example.ragui.nextstation.model.StopTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HorairesService
{
    @GET("/api/routers/default/index/clusters/{codeArret}/stoptimes")
    Call<List<StopTime>> getStopTime(@Path("codeArret") String codeArret);
}
