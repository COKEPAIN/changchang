package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // 승민 - 배경음악
    private long backBtnTime = 0;  // 클래스 상단에 멤버 변수로 선언

    ImageButton home, map, todo, shop, profile, book;
    ImageView changchang;
    Intent intent;
    TextView changsay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 승민
        startService(new Intent(getApplicationContext(), MusicService.class));



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
                // 승민
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() { // 프로필 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Profile.class);
                // 승민
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() { // todo list 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Todo.class);
                // 승민
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() { // 도감 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Book.class);
                // 승민
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() { // 상점 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Shop.class);
                // 승민
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });

        changchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changsay.setText("test");
            }
        });
    }

    // 승민 - 배경음악
    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(), MusicService.class));
        super.onDestroy();
    }

    // 승민 - 홈버튼으로 앱 나가면 음악 꺼지게
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        stopService(new Intent(getApplicationContext(), MusicService.class));
    }

    // 승민 - 뒤로가기 두 번 이벤트
    @Override
    public void onBackPressed(){
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if ( 0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();

        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한 번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        stopService(new Intent(getApplicationContext(), MusicService.class));
    }
}