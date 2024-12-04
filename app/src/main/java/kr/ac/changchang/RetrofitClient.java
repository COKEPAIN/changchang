package kr.ac.changchang;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/") // Base URL
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 변환
                    .build();
        }
        return retrofit;
    }
}
