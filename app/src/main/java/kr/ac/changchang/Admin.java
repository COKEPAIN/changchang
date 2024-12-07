package kr.ac.changchang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity {
    // TextView와 Button을 위한 변수 선언
    TextView administer, studentName1;
    Button studentAttend1, studentAbsence1;
    Button admin;
    EditText assignment, assignmentDeadline;
    

    ViewGroup.LayoutParams params;
    int userid;
    List<Todo_checkList> checklist;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    Intent intent;
    int grade;
    int stress;
    int happiness;
    int health;
    int focus;
    int academicAbility;
    Title currentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // 해당 XML 파일을 로드
        intent = getIntent();
        userid = intent.getIntExtra("userid",0);
        // XML 뷰와 연결
        administer = findViewById(R.id.administer);
        studentName1 = findViewById(R.id.student_name1);
        studentAttend1 = findViewById(R.id.student_attendent1);
        studentAbsence1 = findViewById(R.id.student_attendent2);
        admin = findViewById(R.id.admin);
        assignment = findViewById(R.id.assignment);
        assignmentDeadline = findViewById(R.id.deadline);

        getUserStat(userid);

        // "출석" 버튼 클릭 시 동작
        studentAttend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 출석 버튼 클릭 시, 메시지 토스트 출력
                if(stress > 5){
                    stress -= 5;
                }else{
                    stress =0;
                }
                if(happiness < 95){
                    happiness += 5;
                }else{
                    happiness =100;
                }
                if(focus > 5){
                    focus -= 5;
                }else{
                    focus =0;
                }


                updateUserStatus(userid, grade, stress, happiness, focus, academicAbility);
                updateUserPoints(userid, 50);
                Toast.makeText(Admin.this, "출석 반영 완료", Toast.LENGTH_SHORT).show();
            }
        });

        // "결석" 버튼 클릭 시 동작
        studentAbsence1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 출석 버튼 클릭 시, 메시지 토스트 출력
                if(stress < 95){
                    stress += 5;
                }else{
                    stress =100;
                }
                if(happiness > 5){
                    happiness -= 5;
                }else{
                    happiness =0;
                }
                if(focus < 95){
                    focus += 5;
                }else{
                    focus =100;
                }

                updateUserStatus(userid, grade, stress, happiness, focus, academicAbility);
                Toast.makeText(Admin.this, "결석 반영 완료", Toast.LENGTH_SHORT).show();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssignment(userid, assignment.getText().toString(), assignmentDeadline.getText().toString());
                Toast.makeText(Admin.this, "과제 추가 완료", Toast.LENGTH_SHORT).show();
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
                    grade = userData.getGrade();
                    health = userData.getHealth();
                    stress = userData.getStress();
                    happiness = userData.getHappiness();
                    currentTitle = userData.getTitle(); // 현재 칭호

                    // 새로운 칭호 목록 처리
                    List<Title> availableTitles = userData.getAvailableTitles();
                    if (availableTitles != null) {
                        for (Title title : availableTitles) {
                            Log.d("AVAILABLE_TITLE", "Name: " + title.getName() + ", Description: " + title.getDescription());
                        }

                    }
                } else {
                    Toast.makeText(Admin.this, "유저 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(Admin.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUserStatus(int studentId, int grade, int stress, int happiness, int focus, int academicAbility) {
        // API 요청에 보낼 데이터 생성
        UserStatusUpdateRequest request = new UserStatusUpdateRequest(grade, stress, happiness, focus, academicAbility);

        // API 호출
        Call<Void> call = apiService.updateUserStatus(studentId, request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin.this, "유저 상태가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin.this, "유저 상태 변경에 실패했습니다. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Admin.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void addAssignment(int studentId, String subjectName, String deadline) {
        AssignmentRequest request = new AssignmentRequest( studentId, subjectName, deadline);

        Call<Void> call = apiService.addAssignment(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin.this, "과제가 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin.this, "과제 추가 실패. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Admin.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }


        });
    }
    private void updateUserPoints(int userId, int points) {
        PointsRequest pointsRequest = new PointsRequest(points);

        Call<Void> call = apiService.updateUserPoints(userId, pointsRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin.this, "포인트가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin.this, "포인트 업데이트에 실패했습니다. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Admin.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
