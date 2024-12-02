package kr.ac.changchang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.changchang.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBottomNavigationView();

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.getBottomNavigationView().setSelectedItemId(R.id.fragment_home);
        }
    }

    private void setBottomNavigationView() {
        binding.getBottomNavigationView().setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.activity_home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new HomeFragment())
                            .commit();
                    return true;
                case R.id.fragment_search:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new SearchFragment())
                            .commit();
                    return true;
                case R.id.fragment_favorite:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new FavoriteFragment())
                            .commit();
                    return true;
                case R.id.fragment_settings:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new SettingsFragment())
                            .commit();
                    return true;
                default:
                    return false;
            }
        });
    }
}