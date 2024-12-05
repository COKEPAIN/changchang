package kr.ac.changchang;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;
    List<Map_homePageListview> homePageList;
    ListView listView;
    java.util.Map<String, Integer> departmentNum = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);

        // MapView 초기화
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this); // MapView 준비 완료 시 호출
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Google Map이 준비되었을 때 실행
        this.googleMap = googleMap;



        departmentNum.put("컴퓨터 공학",1);
        departmentNum.put("정보통신",2);
        departmentNum.put("산업시스템공학",3);
        departmentNum.put("스마트 모빌리티",4);
        departmentNum.put("환경에너지공학",5);
        departmentNum.put("건축시스템",6);
        departmentNum.put("건축공학",7);
        departmentNum.put("건축학과",8);
        departmentNum.put("화학공학",9);
        departmentNum.put("조선해양공학",10);


        // 창원의 위치
        LatLng basePosition = new LatLng(35.242466, 128.696543);
        LatLng computSeience = new LatLng(35.241387,128.695727); // 컴공
        LatLng legitimacy = new LatLng(35.242497,128.697048); // 정통
        LatLng department = new LatLng(35.241120,128.698693); // 건축학과
        LatLng industrialSystem = new LatLng(35.241367,128.697756); // 산시공
        LatLng chemicalEngineering = new LatLng(35.241120,128.698693); // 화학공학
        LatLng smartOtion = new LatLng(35.243345,128.697028); // 스모빌
        LatLng environmentalEnergy  = new LatLng(35.241715,128.699112); // 환경에너지공학
        LatLng architecturalSystem = new LatLng(35.241018,128.698978); // 건시공
        LatLng departmentEngineering = new LatLng(35.242439,128.697258); // 건축공학
        LatLng shipbuilding = new LatLng(35.243482,128.697223); // 조선해양공학
        // 카메라를 창원으로 이동
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(basePosition, 17));
        // 줌 레벨은 1~21 사이. 15는 적당히 가까운 거리

        // 마커 정보를 저장할 리스트
        List<Marker> markers = new ArrayList<>();

        // 마커 추가

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(computSeience)
                .title("컴퓨터 공학")
                .snippet("컴퓨터 공학 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(legitimacy)
                .title("정보통신")
                .snippet("정보통신 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(department)
                .title("건축학과")
                .snippet("건축학과 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(industrialSystem)
                .title("산업시스템공학")
                .snippet("산업시스템공학 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(chemicalEngineering)
                .title("화학공학")
                .snippet("화학공학 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(smartOtion)
                .title("스마트 모빌리티")
                .snippet("스마트 모빌리티 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(environmentalEnergy)
                .title("환경에너지공학")
                .snippet("환경에너지공학 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(architecturalSystem)
                .title("건축시스템")
                .snippet("건축시스템 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(departmentEngineering)
                .title("건축공학")
                .snippet("건축공학 위치입니다.")));

        markers.add(googleMap.addMarker(new MarkerOptions()
                .position(shipbuilding)
                .title("조선해양공학")
                .snippet("조선해양공학 위치입니다.")));

        googleMap.setOnMarkerClickListener(clickedMarker -> {
            for (Marker marker : markers) {
                if (marker.equals(clickedMarker)) {

                    Log.e("MAP TEST", "이름: " + marker.getTitle());
                    showAlertDialog(marker.getTitle());
                    break;
                }
            }
            return false; // 기본 동작(정보창 표시)도 실행
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }
    private void showAlertDialog(String title) {
        // AlertDialog의 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title + " 공지사항");

        // Dialog에 사용자 정의 레이아웃 설정
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_map_listview, null);
        builder.setView(dialogView);

        // ListView 설정
        listView = dialogView.findViewById(R.id.homePageListview);
        homePageList = new ArrayList<>();

        // 어댑터 연결
        Map_homePageListviewAdapter adapter = new Map_homePageListviewAdapter(this, homePageList);
        listView.setAdapter(adapter);

        getTableInfo(departmentNum.get(title));

        // 항목 클릭 이벤트 처리
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Map_homePageListview clickedItem = homePageList.get(position);

            // 해당 항목의 링크 열기
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(clickedItem.getLink()));
            try {
                intent.setData(Uri.parse(clickedItem.getLink()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "URL을 열 수 없습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        });


        // AlertDialog 생성
        AlertDialog dialog = builder.create();

        // Dialog 창의 크기와 위치를 조정
        dialog.setOnShowListener(dialogInterface -> {
            // Dialog의 Window 가져오기
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT, // 너비를 화면 전체로 설정
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.8)   // 높이를 화면 전체로 설정
                );

                // Window의 속성 설정
                dialog.getWindow().setGravity(Gravity.BOTTOM); // 창을 아래로 붙이기
            }
        });
        dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        dialog.show();
    }
    private void getTableInfo(int id) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<MapNoticeResponse>> call = apiService.getMapNotices(id);

        call.enqueue(new Callback<List<MapNoticeResponse>>() {
            @Override
            public void onResponse(Call<List<MapNoticeResponse>> call, Response<List<MapNoticeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // API 응답 데이터를 리스트에 추가
                    homePageList.clear(); // 기존 데이터를 지웁니다.
                    for (MapNoticeResponse notice : response.body()) {
                        homePageList.add(new Map_homePageListview(notice.getType(), notice.getTitle(), notice.getLink()));
                    }

                    // 어댑터에 데이터 변경 알림
                    ((Map_homePageListviewAdapter) listView.getAdapter()).notifyDataSetChanged();
                } else {
                    Toast.makeText(Map.this, "공지 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MapNoticeResponse>> call, Throwable t) {
                Toast.makeText(Map.this, "서버 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


}
