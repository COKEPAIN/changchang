package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Todo extends AppCompatActivity {
    ImageButton home, map, todo, shop, profile, book;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        // 기본 버튼 구현
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
        // 기본 버튼 구현 끝
        
        
        
        // 남은 수업 시작 시간
        List<Todo_textview_three> today_class = new ArrayList<>();
        today_class.add(new Todo_textview_three("고자구", "10:30", "12:00"));
        today_class.add(new Todo_textview_three("논리설계", "13:00", "14:00"));
        today_class.add(new Todo_textview_three("집가고싶다", "15:00", "16:00"));

        // ListView와 어댑터 설정
        ListView listView_schedule = findViewById(R.id.assignment); // 적절한 ListView ID 사용
        Todo_textview_threeAdapter adapter_schedule = new Todo_textview_threeAdapter(this, today_class);
        listView_schedule.setAdapter(adapter_schedule);
        adapter_schedule.notifyDataSetChanged();
        // 수업시간 끝

        // 과제 리스트 시작
        List<Todo_textview_three> task = new ArrayList<>();
        task.add(new Todo_textview_three("Item 1A", "Item 1B", "Item 1C"));
        task.add(new Todo_textview_three("Item 2A", "Item 2B", "Item 2C"));
        task.add(new Todo_textview_three("Item 3A", "Item 3B", "Item 3C"));

        ListView listView_textview = findViewById(R.id.schedule); // 적절한 ListView ID 사용
        Todo_textview_threeAdapter adapter_textview = new Todo_textview_threeAdapter(this, task);
        listView_textview.setAdapter(adapter_textview);
        adapter_textview.notifyDataSetChanged();
        // 과제 리스트 끝

        //todo list
        List<Todo_checkList> checklist = new ArrayList<>();
        checklist.add(new Todo_checkList("고급 자료구조", false));
        checklist.add(new Todo_checkList("운영체제", false));
        checklist.add(new Todo_checkList("컴퓨터 네트워크", true));
        checklist.add(new Todo_checkList("소프트웨어 공학", false));

        // ListView와 어댑터 설정
        ListView listView_todo = findViewById(R.id.listView1);
        Todo_checkListAdapter adapter_todo = new Todo_checkListAdapter(this, checklist);
        listView_todo.setAdapter(adapter_todo);
        adapter_todo.notifyDataSetChanged();
        // todo list 끝

    }
}
