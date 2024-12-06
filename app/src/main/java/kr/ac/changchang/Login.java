package kr.ac.changchang;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {


    EditText id, pwd;
    Button login;
    Intent intent;
    int userid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.login_Username);
        pwd = findViewById(R.id.login_Password);
        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = Integer.parseInt(id.getText().toString());
                String password = pwd.getText().toString();

                loginUser(userid, password);
            }
        });
    }
    private void loginUser(int userid, String password) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        // ApiService 인터페이스 생성
        ApiService apiService = retrofit.create(ApiService.class);

        // getUser() 호출 (서버에서 유저 상태 가져오기)
        Call<UserStatusResponse> call = apiService.getUser(userid);
        // 응답 처리
        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserStatusResponse userStatusResponse = response.body();
                    // 서버에서 받은 password와 현재 입력 password 비교
                    if (userStatusResponse.getPassword().equals(password)) {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("username", userStatusResponse.getUsername());
                        intent.putExtra("grade", userStatusResponse.getGrade());
                        intent.putExtra("health", userStatusResponse.getHealth());
                        intent.putExtra("intel", userStatusResponse.getIntel());
                        intent.putExtra("stress", userStatusResponse.getStress());
                        intent.putExtra("happiness", userStatusResponse.getHappiness());
                        intent.putExtra("focus", userStatusResponse.getFocus());
                        intent.putExtra("academicAbility", userStatusResponse.getAcademicAbility());
                        intent.putExtra("title", userStatusResponse.getTitle().getName());  // Title의 name만 전달
                        intent.putExtra("userid",userid);
                        startActivity(intent);
                        finish();
                    } else {
                        // 로그인 실패
                        Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 서버 응답 실패
                    Toast.makeText(Login.this, "서버 오류. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Toast.makeText(Login.this, "네트워크 오류. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
