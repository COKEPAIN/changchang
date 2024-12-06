package kr.ac.changchang;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book extends AppCompatActivity {
    ImageButton home, map, todo, shop, profile, book;
    Intent intent;
    int userid;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    Map<String, Integer> titleNum = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        intent = getIntent();
        userid = intent.getIntExtra("userid",0);
        intent.putExtra("userid",userid);
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


        titleNum.put("창대생", 1);
        titleNum.put("과대", 2);
        titleNum.put("학회장", 4);
        titleNum.put("휴학생", 3);
        titleNum.put("졸업생", 5);
        titleNum.put("부학회장", 6);
        titleNum.put("비둘기", 7);
        titleNum.put("동아리 회장", 8);
        titleNum.put("CASPER 회장", 9);
        titleNum.put("복학생", 10);
        titleNum.put("과탑", 11);
        titleNum.put("컴공 1과대", 12);
        titleNum.put("컴공 2과대", 13);
        titleNum.put("컴공 3과대", 14);
        titleNum.put("컴공 4과대", 15);

        setTitlebackGround();


    }
    private void setTitlebackGround() {
        Call<List<TitleResponse>> call = apiService.getTitles(userid); // Retrofit API 호출
        call.enqueue(new Callback<List<TitleResponse>>() {
            @Override
            public void onResponse(Call<List<TitleResponse>> call, Response<List<TitleResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TitleResponse> titles = response.body();

                    for (TitleResponse title : titles) {
                        int id = title.getId();
                        boolean owned = title.isOwned();

                        // 각 아이템의 백그라운드 ID를 동적으로 매핑
                        int layoutId = getResources().getIdentifier("item" + id + "_back", "id", getPackageName());
                        View itemBack = findViewById(layoutId);

                        if (itemBack != null) {
                            // 소유 여부에 따라 배경색 변경
                            if (owned) {
                                itemBack.setBackgroundColor(getResources().getColor(R.color.light_background)); // 밝은 배경색
                            } else {
                                itemBack.setBackgroundColor(getResources().getColor(R.color.dark_background)); // 어두운 배경색
                            }

                            // 클릭 이벤트 추가
                            itemBack.setOnClickListener(view -> {
                                int currentColor = ((ColorDrawable) view.getBackground()).getColor();

                                // 배경색에 따른 로직
                                if (currentColor == getResources().getColor(R.color.light_background)) {
                                    // 밝은 색: 칭호 변경 시도
                                    TextView titleText = view.findViewById(getResources().getIdentifier("item" + id, "id", getPackageName()));

                                    if (titleText != null) {
                                        String titleName = titleText.getText().toString(); // 텍스트 가져오기
                                        Integer titleId = titleNum.get(titleName); // titleNum에서 ID 가져오기

                                        if (titleId != null) {
                                            changeUserTitle(userid, titleId); // 칭호 변경 API 호출
                                        } else {
                                            Toast.makeText(Book.this, "칭호 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else if (currentColor == getResources().getColor(R.color.dark_background)) {
                                    // 어두운 색: 실패 메시지 출력
                                    Toast.makeText(Book.this, "칭호를 얻지 못했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(Book.this, "칭호 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TitleResponse>> call, Throwable t) {
                Toast.makeText(Book.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void changeUserTitle(int studentId, int titleId) {
        Call<Void> call = apiService.changeUserTitle(studentId, titleId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Book.this, "칭호가 성공적으로 변경되었습니다!", Toast.LENGTH_SHORT).show();
                    refreshHomeData(studentId);
                } else {
                    Toast.makeText(Book.this, "칭호 변경에 실패했습니다. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Book.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void refreshHomeData(int studentId) {
        Call<UserStatusResponse> call = apiService.getUserStatus(studentId);
        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserStatusResponse userData = response.body();
                    String updatedTitle = userData.getTitle().getName(); // 변경된 칭호 가져오기

                    // Intent로 변경된 데이터를 Home 액티비티로 전달
                    intent = new Intent(Book.this, MainActivity.class);
                    intent.putExtra("userid",userid);
                    intent.putExtra("updatedTitle", updatedTitle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 액티비티 스택 제거
                    startActivity(intent);
                    finish(); // 현재 액티비티 종료
                } else {
                    Toast.makeText(Book.this, "홈 화면 데이터를 갱신하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                Toast.makeText(Book.this, "홈 데이터 갱신 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
