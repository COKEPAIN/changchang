package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    ViewGroup.LayoutParams params;

    private String username;
    private int ugrade = 2;
    private int health;
    private int intel;
    private int stress;
    private int happiness;
    private int focus;
    private int academicAbility;
    int maxBarWidthInDp = 300; // 최대 너비 (dp)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        int userid = intent.getIntExtra("userid",0);
        String test = String.valueOf(userid);
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();

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
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() { // todo list 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Todo.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() { // 도감 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Book.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() { // 상점 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Shop.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userid",userid);
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

                    //스트레스 바에 대한 처리
                    params = stressBar.getLayoutParams();
                    params.width = (int) (stress / 100.0 * maxBarWidthInDp * getResources().getDisplayMetrics().density);
                    stressBar.setLayoutParams(params);

                    // 행복도바에 대한 처리
                    params = happinessBar.getLayoutParams();
                    params.width = (int) (happiness / 100.0 * maxBarWidthInDp * getResources().getDisplayMetrics().density);
                    happinessBar.setLayoutParams(params);

                    // 집중도 바에 대한 처리
                    params = focusBar.getLayoutParams();
                    params.width = (int) (focus / 100.0 * maxBarWidthInDp * getResources().getDisplayMetrics().density);
                    focusBar.setLayoutParams(params);

                    // 학업 능력 바에 대한 처리
                    params = academicBar.getLayoutParams();
                    params.width = (int) (academicAbility / 100.0 * maxBarWidthInDp * getResources().getDisplayMetrics().density);
                    academicBar.setLayoutParams(params);

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
