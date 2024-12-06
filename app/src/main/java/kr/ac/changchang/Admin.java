package kr.ac.changchang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    // TextView와 Button을 위한 변수 선언
    TextView administer, studentName1;
    Button studentAttend1, studentAbsence1, inputTodo;
    ListView assignmentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // 해당 XML 파일을 로드

        // XML 뷰와 연결
        administer = findViewById(R.id.administer);
        studentName1 = findViewById(R.id.student_name1);
        studentAttend1 = findViewById(R.id.student_attendent1);
        studentAbsence1 = findViewById(R.id.student_attendent1);
        inputTodo = findViewById(R.id.inputTodo);
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

        // Todo 추가 버튼 클릭 시 동작
        inputTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo 추가 버튼 클릭 시, 메시지 토스트 출력
                Toast.makeText(Admin.this, "할 일을 추가합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
