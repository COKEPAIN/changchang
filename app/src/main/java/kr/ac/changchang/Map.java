package kr.ac.changchang;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.List;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;
    List<Map_homePageListview> homePageList;

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
                .position(basePosition)
                .title("창원")
                .snippet("여기는 창원입니다.")));

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
                    showAlertDialog();
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
    private void showAlertDialog() {
        // AlertDialog의 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("공지사항");

        // Dialog에 사용자 정의 레이아웃 설정
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_map_listview, null);
        builder.setView(dialogView);

        // ListView 설정
        ListView listView = dialogView.findViewById(R.id.homePageListview);
        homePageList = new ArrayList<>();
        homePageList.add(new Map_homePageListview("공지", "제목1", "https://www.naver.com"));
        homePageList.add(new Map_homePageListview("공지", "제목2", "https://www.daum.net"));
        homePageList.add(new Map_homePageListview("공지", "제목3", "https://www.youtube.com"));

        // 어댑터 연결
        Map_homePageListviewAdapter adapter = new Map_homePageListviewAdapter(this, homePageList);
        listView.setAdapter(adapter);

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
}
