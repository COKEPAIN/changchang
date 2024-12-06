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

public class Login extends AppCompatActivity {
    // 배경음악
    private MediaPlayer mediaPlayer = new MediaPlayer();

    EditText id, pwd;
    Button login;
    Intent intent;

    String t1 = "abc", t2 = "123";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // 배경음악
        try {
            mediaPlayer = MediaPlayer.create(this,R.raw.backsong);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        id = findViewById(R.id.login_Username);
        pwd = findViewById(R.id.login_Password);
        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.getText().toString().equals(t1)&& pwd.getText().toString().equals(t2)){
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
