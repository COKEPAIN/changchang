package kr.ac.changchang;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageButton home, map, todo, shop, profile, book;
    ImageView changchang;
    Intent intent;
    TextView changsay;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class); // api 설정하기 위한 변수
    int userid;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Intent로부터 전달받은 데이터 가져오기
        intent = getIntent();

        userid = intent.getIntExtra("userid",0);
        intent.putExtra("userid",userid);
        getUserStat(userid);

        home = (ImageButton) findViewById(R.id.btn_home);
        map = (ImageButton) findViewById(R.id.btn_map);
        todo = (ImageButton) findViewById(R.id.btn_todo);
        shop = (ImageButton) findViewById(R.id.btn_shop);
        profile = (ImageButton) findViewById(R.id.btn_profile);
        book = (ImageButton) findViewById(R.id.btn_drawingbook);
        changchang = (ImageView)findViewById(R.id.changchang);
        changsay = (TextView) findViewById(R.id.changchangsaid);

        map.setOnClickListener(new View.OnClickListener() { // 맵 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Map.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() { // 프로필 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Profile.class);
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

        changchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3가지 문장을 배열에 저장
                String[] messages = {"오늘 기분 좋다잉", "배고프다", "호관아 뭐하냐"};

                // 랜덤으로 문장 선택
                int randomIndex = (int) (Math.random() * messages.length);

                // 랜덤으로 선택된 문장을 changsay에 설정
                changsay.setText(messages[randomIndex]);

                // FrameLayout.LayoutParams 사용
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(80)
                );
                changsay.setLayoutParams(params);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changsay.setText("");
                        // FrameLayout.LayoutParams 사용
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(0)
                        );
                        changsay.setLayoutParams(params);
                    }
                }, 3000);
            }
        });
    }
    // dp를 픽셀로 변환하는 메소드
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    private void getUserStat(int userId) {
        Call<UserStatusResponse> call = apiService.getUserStatus(userId);

        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserStatusResponse userData = response.body();
                    title = userData.getTitle().getName(); // 현재 칭호
                    TextView changchangTItle = (TextView)findViewById(R.id.changchangtitle);
                    changchangTItle.setText(title);

                } else {
                    Toast.makeText(MainActivity.this, "유저 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}