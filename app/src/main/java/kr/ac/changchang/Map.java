package kr.ac.changchang;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;

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
//        LatLng computSeience = new LatLng(35.241387,128.695727); // 컴공
//        LatLng legitimacy = new LatLng(35.242497,128.697048); // 정통
//        LatLng computSeience = new LatLng(35.241120,128.698693); // 건축학과
//        LatLng computSeience = new LatLng(35.241367,128.697756); // 산시공
//        LatLng computSeience = new LatLng(35.241120,128.698693); // 건축학과
//        LatLng computSeience = new LatLng(35.243345,128.697028); // 스모빌
//        LatLng computSeience = new LatLng(35.241715,128.699112); // 환경에너지공학
//        LatLng computSeience = new LatLng(35.241018,128.698978); // 건시공
//        LatLng computSeience = new LatLng(35.242439,128.697258); // 건축공학
//        LatLng computSeience = new LatLng(35.243482,128.697223); // 조선해양공학
        // 카메라를 창원으로 이동
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(basePosition, 17));
        // 줌 레벨은 1~21 사이. 15는 적당히 가까운 거리

        googleMap.addMarker(new MarkerOptions().position(basePosition).title("기본위치").snippet("기본위치 입니다"));
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
}
