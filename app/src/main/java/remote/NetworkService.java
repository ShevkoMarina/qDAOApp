package remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "http://192.168.1.45:5001";

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit =  new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
