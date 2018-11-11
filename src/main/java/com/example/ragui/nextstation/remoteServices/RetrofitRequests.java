package com.example.ragui.nextstation.remoteServices;

public class RetrofitRequests
{
    private static final String Base_URL = "https://data.metromobilite.fr";

    /*
        retourne la liste des lignes TAG
     */
    public static LigneService getTAGLignes()
    {
        return ClientRetrofit.getClient(Base_URL).create(LigneService.class);
    }

    public static RouteService getRouteByCode()
    {
        return ClientRetrofit.getClient(Base_URL).create(RouteService.class);
    }

    public static HorairesService getStopTime()
    {
        return ClientRetrofit.getClient(Base_URL).create(HorairesService.class);
    }
}
