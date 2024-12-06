package kr.ac.changchang;

import android.content.Intent;
import android.os.Build;
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
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;

import okhttp3.ResponseBody;
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
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class); // api 설정하기 위한 변수
    List<Todo_textview_three> task; //남은 과제 리스트
    String today;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        intent = getIntent();
        int userid = intent.getIntExtra("userid",0);

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
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();
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
        // 기본 버튼 구현 끝

        // 유저 정보 들고오기
        getUserStat(20213114);
        getUserSubject();

        // 남은 수업 시작 시간
        List<Todo_textview_three> today_class = new ArrayList<>();
//        today_class.add(new Todo_textview_three("고자구", "10:30", "12:00"));
//        today_class.add(new Todo_textview_three("논리설계", "13:00", "14:00"));
//        today_class.add(new Todo_textview_three("집가고싶다", "15:00", "16:00"));

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
//        task.add(new Todo_textview_three("Item 1A", "Item 1B", "Item 1C")); // 예제
//        task.add(new Todo_textview_three("Item 2A", "Item 2B", "Item 2C"));
//        task.add(new Todo_textview_three("Item 3A", "Item 3B", "Item 3C"));
        // API로 과제 들고오기
        getUserTask(20213114, () -> {
            if (!task.isEmpty()) {
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
//        checklist.add(new Todo_checkList("고급 자료구조", false));
//        checklist.add(new Todo_checkList("운영체제", false));
//        checklist.add(new Todo_checkList("컴퓨터 네트워크", true));
//        checklist.add(new Todo_checkList("소프트웨어 공학", false));

        // ListView와 어댑터 설정
        ListView listView_todo = findViewById(R.id.listView1);
        adapter_todo = new Todo_checkListAdapter(this, checklist);
        listView_todo.setAdapter(adapter_todo);
        adapter_todo.notifyDataSetChanged();

        getUserTodoList(20213114, listView_todo);
//        params = listView_todo.getLayoutParams(); //높이를 동적으로 할당
//        params.height = (int) (60*checklist.size()* getResources().getDisplayMetrics().density); // dp를 px로 변환
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

        EditText inputContent = dialogView.findViewById(R.id.input1); // 입력 필드 하나만 사용

        // 확인 버튼 설정
        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            String content = inputContent.getText().toString().trim();

            if (!content.isEmpty()) {
                // API 호출로 서버에 데이터 추가
                int studentId = 20213114; // 고정된 학번 값
                TodoRequest todoRequest = new TodoRequest(content);

                Call<ResponseBody> call = apiService.addTodo(studentId, todoRequest);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("SERVER_RESPONSE_CODE", "Code: " + response.code());

                        if (response.isSuccessful()) {
                            try {
                                String serverMessage = response.body().string(); // 서버 응답 메시지 읽기
                                Log.d("SERVER_RESPONSE", "Message: " + serverMessage);

                                if (serverMessage.contains("Todo 리스트 생성되었습니다.")) {
                                    Toast.makeText(Todo.this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                                    // 리스트에 새 항목 추가
                                    checklist.add(new Todo_checkList(content, false, -1));
                                    adapter_todo.notifyDataSetChanged();

                                    // 높이 재조정
                                    params.height = (int) (60 * checklist.size() * getResources().getDisplayMetrics().density);
                                    listView_todo.setLayoutParams(params);
                                } else {
                                    Toast.makeText(Todo.this, "알 수 없는 응답: " + serverMessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("RESPONSE_ERROR", "Error parsing server response", e);
                                Toast.makeText(Todo.this, "응답 파싱에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Todo.this, "할 일 추가에 실패했습니다. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("API_ERROR_BODY", response.errorBody().string());
                                }
                            } catch (Exception e) {
                                Log.e("API_ERROR_BODY", "Error parsing error body", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Todo.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(this, "할 일 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 취소 버튼 설정
        builder.setNegativeButton("취소", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create().show();
    }


    private void getUserTask(int studentId, Runnable onComplete) {

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
    private void getUserStat(int userId) {
        Call<UserStatusResponse> call = apiService.getUserStatus(userId);

        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserStatusResponse userData = response.body();

                    // 기존 정보 저장
                    String username = userData.getUsername();
                    int grade = userData.getGrade();
                    int health = userData.getHealth();
                    int stress = userData.getStress();
                    int happiness = userData.getHappiness();
                    Title currentTitle = userData.getTitle(); // 현재 칭호

                    // 새로운 칭호 목록 처리
                    List<Title> availableTitles = userData.getAvailableTitles();
                    if (availableTitles != null) {
                        for (Title title : availableTitles) {
                            Log.d("AVAILABLE_TITLE", "Name: " + title.getName() + ", Description: " + title.getDescription());
                        }

                    }
                } else {
                    Toast.makeText(Todo.this, "유저 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(Todo.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserSubject() {
        Call<List<Subject>> call = apiService.getSubjects(20213114);

        Map<Integer, String> day = new HashMap<>();
        day.put(1,"Monday");
        day.put(2,"Tuesday");
        day.put(3,"Thursday");
        day.put(4,"Wednesday");
        day.put(5,"Friday");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = day.get(LocalDate.now().getDayOfWeek().getValue());
        }
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 데이터를 기존 리스트에 추가
                    List<Todo_textview_three> today_class = new ArrayList<>();
                    for (Subject subject : response.body()) { // 테스트용
                        for (Schedule schedule : subject.getSchedules()) {
                            Log.d("DEBUG_TODO", "Subject: " + subject.getSubjectName() +
                                    ", Day: " + schedule.getDayOfWeek() +
                                    ", Start: " + schedule.getStartTime() +
                                    ", End: " + schedule.getEndTime());
                            if(schedule.getDayOfWeek().equals("Monday")){ // 임시 수정

                                today_class.add(new Todo_textview_three(
                                        subject.getSubjectName(),
                                        schedule.getStartTime(),
                                        schedule.getEndTime()
                                ));
                            }

                        }
                    }

                    // 어댑터에 변경사항 알림
                    adapter_schedule = new Todo_textview_threeAdapter(Todo.this, today_class);
                    ListView listView_schedule = findViewById(R.id.assignment);
                    listView_schedule.setAdapter(adapter_schedule);
                    adapter_schedule.notifyDataSetChanged();

                    // 높이 조정
                    ViewGroup.LayoutParams params = listView_schedule.getLayoutParams();
                    params.height = (int) (44 * today_class.size() * getResources().getDisplayMetrics().density);
                    listView_schedule.setLayoutParams(params);
                } else {
                    Toast.makeText(Todo.this, "수업 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Toast.makeText(Todo.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void getUserTodoList(int userId, ListView listView) {
        Call<List<TodoResponse>> call = apiService.getTodos(userId);

        call.enqueue(new Callback<List<TodoResponse>>() {
            @Override
            public void onResponse(Call<List<TodoResponse>> call, Response<List<TodoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 기존 데이터 초기화
                    checklist.clear();

                    // 서버에서 받은 데이터를 리스트에 추가
                    for (TodoResponse todo : response.body()) {
                        String content = todo.getContent() != null ? todo.getContent() : "내용 없음";
                        int todoId = todo.getId(); // ID를 포함

                        checklist.add(new Todo_checkList(content, false, todoId)); // ID를 추가로 저장
                    }

                    // 어댑터에 변경사항 알림
                    adapter_todo.notifyDataSetChanged();

                    // 높이 조정
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    params.height = (int) (60 * checklist.size() * getResources().getDisplayMetrics().density);  // -1해야하나?
                    listView.setLayoutParams(params);
                } else {
                    Toast.makeText(Todo.this, "Todo 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TodoResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(Todo.this, "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}