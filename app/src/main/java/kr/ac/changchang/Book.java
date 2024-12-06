package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Book extends AppCompatActivity {
    ImageButton home, map, todo, shop, profile, book;
    Intent intent;
    int userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Profile.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Shop.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Todo.class);
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
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),Map.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

    }
}
