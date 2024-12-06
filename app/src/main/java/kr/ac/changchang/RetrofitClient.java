package kr.ac.changchang;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jubilant-disco-pwvjvpj66x6frxj-8080.app.github.dev/") // 성민이꺼
//                    .baseUrl("https://crispy-enigma-5r77r65q65qcvgwr-8080.app.github.dev/") // 영필이꺼
//                    .baseUrl("http://localhost:8085/") // 로컬 테스트
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
