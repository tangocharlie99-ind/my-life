package com.mylife.app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public abstract class BaseActivity extends AppCompatActivity {
    static final int[] ACCENTS = {R.style.Accent_Emerald, R.style.Accent_Ocean, R.style.Accent_Amber, R.style.Accent_Purple, R.style.Accent_Crimson};
    protected abstract int navId();
    @Override protected void onCreate(Bundle b) {
        SharedPreferences sp = getSharedPreferences("mylife", MODE_PRIVATE);
        int mode = sp.getInt("mode", 0), accent = sp.getInt("accent", 0);
        AppCompatDelegate.setDefaultNightMode(mode == 1 ? AppCompatDelegate.MODE_NIGHT_NO
                : mode == 0 ? AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM : AppCompatDelegate.MODE_NIGHT_YES);
        setTheme(R.style.Theme_MyLife);
        getTheme().applyStyle(ACCENTS[accent], true);
        if (mode == 3) getTheme().applyStyle(R.style.Amoled, true);
        super.onCreate(b);
    }
    @Override public void setContentView(int layoutRes) {
        super.setContentView(R.layout.base);
        FrameLayout content = findViewById(R.id.content);
        LayoutInflater.from(this).inflate(layoutRes, content, true);
        BottomNavigationView nav = findViewById(R.id.nav);
        nav.setSelectedItemId(navId());
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == navId()) return true;
            Class<?> t = id == R.id.nav_home ? HomeActivity.class : id == R.id.nav_journal ? MainActivity.class
                    : id == R.id.nav_kitchen ? KitchenActivity.class : RecipeActivity.class;
            startActivity(new Intent(this, t));
            overridePendingTransition(0, 0);
            finish();
            return true;
        });
    }
}
