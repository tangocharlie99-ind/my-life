package com.mylife.app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
public class SettingsActivity extends BaseActivity {
    @Override protected int navId() { return R.id.nav_home; }
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_settings);
        SharedPreferences sp = getSharedPreferences("mylife", MODE_PRIVATE);
        Spinner mode = findViewById(R.id.mode), accent = findViewById(R.id.accent);
        mode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Follow system", "Light", "Dark", "AMOLED pure black"}));
        accent.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Emerald", "Ocean Blue", "Sunset Amber", "Royal Purple", "Crimson"}));
        mode.setSelection(sp.getInt("mode", 0)); accent.setSelection(sp.getInt("accent", 0));
        findViewById(R.id.apply).setOnClickListener(v -> {
            sp.edit().putInt("mode", mode.getSelectedItemPosition()).putInt("accent", accent.getSelectedItemPosition()).apply();
            startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        });
    }
}
