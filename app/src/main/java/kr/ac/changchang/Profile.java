package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    ImageButton home, map, todo, shop, profile, book;
    Intent intent;
    View stressBar, happinessBar, focusBar, academicBar;
    TextView point, grade, studentId;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);; // api 설정하기 위한 변수

    private String username;
    private int ugrade = 2;
    private int health;
    private int intel;
    private int stress;
    private int happiness;
    private int focus;
    private int academicAbility;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        home = (ImageButton) findViewById(R.id.btn_home);
        map = (ImageButton) findViewById(R.id.btn_map);
        todo = (ImageButton) findViewById(R.id.btn_todo);
        shop = (ImageButton) findViewById(R.id.btn_shop);
        profile = (ImageButton) findViewById(R.id.btn_profile);
        book = (ImageButton) findViewById(R.id.btn_drawingbook);

        map.setOnClickListener(new View.OnClickListener() { // 맵 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Map.class);
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() { // todo list 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Todo.class);
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() { // 도감 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Book.class);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() { // 상점 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Shop.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        stressBar = (View) findViewById(R.id.stress_bar);
        happinessBar = (View) findViewById(R.id.happiness_bar);
        focusBar = (View) findViewById(R.id.focus_bar);
        academicBar = (View) findViewById(R.id.academic_bar);

        point = (TextView) findViewById(R.id.point);
        grade = (TextView) findViewById(R.id.grade);
        studentId = (TextView) findViewById(R.id.studno);

        getUserStat(20213114);


    }
    private void getUserStat(int userId) { // 유저 정보 들고오기
        Call<UserStatusResponse> call = apiService.getUserStatus(userId);

        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserStatusResponse userData = response.body();
                    username = userData.getUsername();
                    ugrade = userData.getGrade();
                    health = userData.getHealth();
                    intel = userData.getIntel();
                    stress = userData.getStress();
                    happiness = userData.getHappiness();
                    focus = userData.getFocus();
                    academicAbility = userData.getAcademicAbility();

                    grade.setText("Grade : "+ugrade);

                } else {
                    Log.e("API_ERROR", "Response failed");
                    Toast.makeText(Profile.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(Profile.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
