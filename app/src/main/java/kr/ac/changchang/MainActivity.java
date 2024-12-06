package kr.ac.changchang;

import android.content.Intent;
import android.media.MediaPlayer;
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

    ImageButton home, map, todo, shop, profile, book;
    ImageView changchang;
    Intent intent;
    TextView changsay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Intent로부터 전달받은 데이터 가져오기
        intent = getIntent();
        String username = intent.getStringExtra("username");
        int grade = intent.getIntExtra("grade", 0);
        int health = intent.getIntExtra("health", 0);
        int intel = intent.getIntExtra("intel", 0);
        int stress = intent.getIntExtra("stress", 0);
        int happiness = intent.getIntExtra("happiness", 0);
        int focus = intent.getIntExtra("focus", 0);
        int academicAbility = intent.getIntExtra("academicAbility", 0);
        String title = intent.getStringExtra("title");

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
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() { // 프로필 액티비티 전환
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Profile.class);
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

        changchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changsay.setText("test");
                changsay.setHeight(80);
            }
        });
    }
}