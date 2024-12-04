package kr.ac.changchang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Todo extends AppCompatActivity {
    ImageButton home, map, todo, shop, profile, book;
    Intent intent;
    List<Todo_checkList> checklist;
    ViewGroup.LayoutParams params;
    Todo_checkListAdapter adapter_todo; // todo 리스트에 대한 어뎁터
    Todo_textview_threeAdapter adapter_textview; // textview에대한 어뎁터
    Todo_textview_threeAdapter adapter_schedule; // schedule에대한 어뎁터
    private ApiService apiService; // api 설정하기 위한 변수
    List<Todo_textview_three> task; //남은 과제 리스트


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
        adapter_schedule = new Todo_textview_threeAdapter(this, today_class);
        listView_schedule.setAdapter(adapter_schedule);
        adapter_schedule.notifyDataSetChanged();

        params = listView_schedule.getLayoutParams(); //높이를 동적으로 할당
        params.height = (int) (44*today_class.size()* getResources().getDisplayMetrics().density); // dp를 px로 변환

        // 수업시간 끝

        // 과제 리스트 시작
        ListView listView_textview = findViewById(R.id.schedule); // 적절한 ListView ID 사용
        task = new ArrayList<>();
//        task.add(new Todo_textview_three("Item 1A", "Item 1B", "Item 1C"));
//        task.add(new Todo_textview_three("Item 2A", "Item 2B", "Item 2C"));
//        task.add(new Todo_textview_three("Item 3A", "Item 3B", "Item 3C"));
        // API로 과제 들고오기
        fetchAssignments(20213114, () -> {
            if (!task.isEmpty()) {
                Log.e("task TEST", "내용 확인: " + task.get(0).getText1());
                adapter_textview = new Todo_textview_threeAdapter(this, task);

                listView_textview.setAdapter(adapter_textview);

                adapter_textview.notifyDataSetChanged();
                params = listView_textview.getLayoutParams(); //높이를 동적으로 할당
                params.height = (int) (44*task.size()* getResources().getDisplayMetrics().density); // dp를 px로 변환
            }
        });

        // 과제 리스트 끝

        //todo list
        checklist = new ArrayList<>();
        checklist.add(new Todo_checkList("고급 자료구조", false));
        checklist.add(new Todo_checkList("운영체제", false));
        checklist.add(new Todo_checkList("컴퓨터 네트워크", true));
        checklist.add(new Todo_checkList("소프트웨어 공학", false));

        // ListView와 어댑터 설정
        ListView listView_todo = findViewById(R.id.listView1);
        adapter_todo = new Todo_checkListAdapter(this, checklist);
        listView_todo.setAdapter(adapter_todo);
        adapter_todo.notifyDataSetChanged();
        params = listView_todo.getLayoutParams(); //높이를 동적으로 할당
        params.height = (int) (60*checklist.size()* getResources().getDisplayMetrics().density); // dp를 px로 변환
        // todo list 끝

        //todo 할일 추가 dialog
        AppCompatButton inputTodoButton = findViewById(R.id.inputTodo);
        inputTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
                params = listView_todo.getLayoutParams(); //높이를 동적으로 할당

            }
        });
        // todo 할일 추가 dialog 끝
    }
    private void showAlertDialog() {
        ListView listView_todo = findViewById(R.id.listView1);
        // AlertDialog의 뷰 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("새 할 일 추가");

        // 입력 필드가 들어갈 레이아웃 생성
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_todo, null);
        builder.setView(dialogView);

        EditText input1 = dialogView.findViewById(R.id.input1);
        EditText input2 = dialogView.findViewById(R.id.input2);
        EditText input3 = dialogView.findViewById(R.id.input3);

        // 확인 버튼 설정
        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            String text1 = input1.getText().toString().trim();
            String text2 = input2.getText().toString().trim();
            String text3 = input3.getText().toString().trim();

            if (!text1.isEmpty() && !text2.isEmpty() && !text3.isEmpty()) {
                // 체크리스트에 새 항목 추가
                checklist.add(new Todo_checkList(text1 + " - " + text2 + " - " + text3, false));

                // 어댑터에 변경사항 알림
                adapter_todo.notifyDataSetChanged();

                // 높이 재계산 및 적용
                params.height = (int) (60 * checklist.size() * getResources().getDisplayMetrics().density);
                listView_todo.setLayoutParams(params); // 변경된 높이를 적용
            }
        });

        // 취소 버튼 설정
        builder.setNegativeButton("취소", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create().show();
    }
    private void fetchAssignments(int studentId, Runnable onComplete) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Todo_assignmentRespones>> call = apiService.getAssignments(studentId);

        call.enqueue(new Callback<List<Todo_assignmentRespones>>() {
            @Override
            public void onResponse(Call<List<Todo_assignmentRespones>> call, Response<List<Todo_assignmentRespones>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    task.clear();
                    String submitStat;
                    for (Todo_assignmentRespones assignment : response.body()) {
                        submitStat = "";
                        if(assignment.getSubmitted()){
                            submitStat = "제출 됨";
                        }
                        task.add(new Todo_textview_three(
                                assignment.getSubjectName(),
                                assignment.getDeadline(),
                                submitStat // 과제 ID 추가
                        ));
                    }

                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    Log.e("API", "Failed to get assignments");
                    Toast.makeText(Todo.this, "과제 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Todo_assignmentRespones>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
                Toast.makeText(Todo.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}