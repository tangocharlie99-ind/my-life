package com.mylife.app;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
public class KitchenActivity extends AppCompatActivity {
    private static final long DAY = 24L * 3600 * 1000;
    private EditText name, qty, unit, thr, days; private Spinner cat; private ListView stock, grocery;
    private List<Item> stockItems = new ArrayList<>(), groceryItems = new ArrayList<>();
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_kitchen);
        name = findViewById(R.id.name); qty = findViewById(R.id.qty); unit = findViewById(R.id.unit);
        thr = findViewById(R.id.thr); days = findViewById(R.id.days); cat = findViewById(R.id.cat);
        stock = findViewById(R.id.stock); grocery = findViewById(R.id.grocery);
        cat.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Fridge", "Pantry", "Spices", "Essentials"}));
        findViewById(R.id.add).setOnClickListener(v -> {
            String n = name.getText().toString().trim();
            if (n.isEmpty()) return;
            Item i = new Item();
            i.name = n; i.category = (String) cat.getSelectedItem();
            i.qty = num(qty, 1); i.unit = unit.getText().toString().trim();
            i.threshold = num(thr, 1);
            double d = num(days, -1);
            i.expiry = d < 0 ? 0 : System.currentTimeMillis() + (long) (d * DAY);
            AppDb.get(this).items().insert(i);
            name.setText(""); qty.setText(""); unit.setText(""); thr.setText(""); days.setText("");
            refresh();
        });
        stock.setOnItemClickListener((p, v, pos, id) -> { AppDb.get(this).items().delete(stockItems.get(pos).id); refresh(); });
        grocery.setOnItemClickListener((p, v, pos, id) -> {
            Item i = groceryItems.get(pos);
            AppDb.get(this).items().restock(i.id, Math.max(i.threshold * 2, 1));
            refresh();
        });
        refresh();
    }
    private double num(EditText e, double def) {
        try { return Double.parseDouble(e.getText().toString().trim()); } catch (Exception x) { return def; }
    }
    private void refresh() {
        stockItems = AppDb.get(this).items().all();
        groceryItems = new ArrayList<>();
        List<String> a = new ArrayList<>(), g = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (Item i : stockItems) {
            String exp = "";
            boolean soon = false;
            if (i.expiry > 0) {
                long left = (long) Math.ceil((i.expiry - now) / (double) DAY);
                exp = left < 0 ? "  EXPIRED" : "  expires in " + left + "d";
                soon = left <= 3;
            }
            boolean low = i.qty <= i.threshold;
            a.add("[" + i.category + "] " + i.name + " " + i.qty + " " + i.unit + exp + (low ? "  LOW" : ""));
            if (low || soon) { groceryItems.add(i); g.add("Buy: " + i.name + (soon ? " (expiring)" : " (low stock)")); }
        }
        stock.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, a));
        grocery.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, g));
    }
}
