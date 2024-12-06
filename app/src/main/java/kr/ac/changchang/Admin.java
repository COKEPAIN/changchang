package kr.ac.changchang;

import android.app.AlertDialog;
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
    ListView assignmentListView;
    AppCompatButton admin;

    Assignment_checkListAdapter adapter_assignment;
    ViewGroup.LayoutParams params;

    List<Todo_checkList> checklist;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // 해당 XML 파일을 로드

        // XML 뷰와 연결
        administer = findViewById(R.id.administer);
        studentName1 = findViewById(R.id.student_name1);
        studentAttend1 = findViewById(R.id.student_attendent1);
        studentAbsence1 = findViewById(R.id.student_attendent1);
        admin = findViewById(R.id.admin);
        assignmentListView = findViewById(R.id.assignment);

        // "출석" 버튼 클릭 시 동작
        studentAttend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 출석 버튼 클릭 시, 메시지 토스트 출력
                Toast.makeText(Admin.this, studentName1.getText() + "의 출석을 체크했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // "결석" 버튼 클릭 시 동작
        studentAbsence1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 결석 버튼 클릭 시, 메시지 토스트 출력
                Toast.makeText(Admin.this, studentName1.getText() + "의 결석을 체크했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // admin 과제 버튼 클릭 시 동작
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
                params = assignmentListView.getLayoutParams(); //높이를 동적으로 할당

            }
        });
        
        // admin 과제 추가 dialog 끝
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
                                        Toast.makeText(Admin.this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                                        // 리스트에 새 항목 추가
                                        checklist.add(new Todo_checkList(content, false, -1));
                                        adapter_assignment.notifyDataSetChanged();

                                        // 높이 재조정
                                        params.height = (int) (60 * checklist.size() * getResources().getDisplayMetrics().density);
                                        listView_todo.setLayoutParams(params);
                                    } else {
                                        Toast.makeText(Admin.this, "알 수 없는 응답: " + serverMessage, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("RESPONSE_ERROR", "Error parsing server response", e);
                                    Toast.makeText(Admin.this, "응답 파싱에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Admin.this, "할 일 추가에 실패했습니다. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Admin.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}
