package android.cse.diu.mehedi.serverimageupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dustu on 5/11/2018.
 */

public class ApiClient {

    private static final String BASE_URL = "https://314253.000webhostapp.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
