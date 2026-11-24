package com.mylife.app;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private EditText input; private ListView list; private TextView summary;
    private final AIManager ai = new AIManager();
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input); list = findViewById(R.id.list); summary = findViewById(R.id.summary);
        Button save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            String s = input.getText().toString().trim();
            if (s.isEmpty()) return;
            for (Entry e : ai.parse(s)) AppDb.get(this).dao().insert(e);
            input.setText("");
            refresh();
        });
        findViewById(R.id.openKitchen).setOnClickListener(v -> startActivity(new android.content.Intent(this, KitchenActivity.class)));
        findViewById(R.id.openRecipes).setOnClickListener(v -> startActivity(new android.content.Intent(this, RecipeActivity.class)));
        refresh();
    }
    @Override protected void onResume() { super.onResume(); refresh(); }
    private void refresh() {
        List<Entry> all = AppDb.get(this).dao().all();
        SimpleDateFormat f = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
        List<String> rows = new ArrayList<>();
        int kcal = 0; long today = System.currentTimeMillis() - 24L * 3600 * 1000;
        for (Entry e : all) {
            String unit = e.type.equals("Diet") ? " kcal" : e.type.equals("Fitness") || e.type.equals("Study") ? " min" : "";
            rows.add(f.format(new Date(e.time)) + "  [" + e.type + "] " + e.text + (e.value > 0 ? " (" + e.value + unit + ")" : ""));
            if (e.type.equals("Diet") && e.time > today) kcal += e.value;
        }
        summary.setText("Calories (last 24h): " + kcal);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows));
    }
}
