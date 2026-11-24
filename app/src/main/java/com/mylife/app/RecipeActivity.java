package com.mylife.app;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class RecipeActivity extends BaseActivity {
    @Override protected int navId() { return R.id.nav_recipes; }
    // name | ingredients | kcal per serving | steps
    private static final String[] DATA = {
        "Egg Bhurji|egg,onion,tomato|220|Chop onion and tomato, saute, add beaten eggs and scramble with salt and chilli.",
        "Veg Pulao|rice,onion,carrot,peas|350|Saute onion and veg, add rice and water, cook covered until fluffy.",
        "Dal Tadka|dal,onion,tomato|250|Boil dal, temper with onion, tomato and spices, mix and simmer.",
        "Paneer Bhurji|paneer,onion,tomato|300|Crumble paneer, cook with onion, tomato and spices for 5 minutes.",
        "Chicken Curry|chicken,onion,tomato|400|Brown onion, add tomato and spices, add chicken and simmer 25 minutes.",
        "Aloo Sabzi|potato,onion,tomato|230|Boil and cube potato, saute with onion, tomato and spices.",
        "Poha|poha,onion,potato|250|Rinse poha, saute onion and potato, add poha, turmeric and salt.",
        "Masala Omelette|egg,onion,tomato|200|Whisk eggs with chopped onion, tomato and chilli, fry on a pan.",
        "Curd Rice|rice,curd|280|Mix cooked rice with curd and salt, temper with mustard seeds.",
        "Banana Milkshake|banana,milk|230|Blend banana with cold milk and a little sugar.",
        "Vegetable Khichdi|rice,dal,carrot,peas|330|Pressure cook rice, dal and veg with turmeric and salt.",
        "Palak Paneer|spinach,paneer,onion|320|Blanch and puree spinach, cook with onion and spices, add paneer.",
        "Chapati with Sabzi|atta,potato|300|Knead atta into dough, roll and cook chapatis, serve with aloo sabzi.",
        "Tomato Soup|tomato,onion|120|Boil tomato and onion, blend smooth, season and simmer.",
        "Upma|rava,onion,carrot|260|Roast rava, saute onion and carrot, add water and rava, stir till thick.",
        "Fruit Salad|apple,banana|180|Chop fruit, mix and add a squeeze of lemon."
    };
    private static class Rec { String name, steps; String[] ing; int kcal, have, exp; List<Item> used = new ArrayList<>(); List<String> missing = new ArrayList<>(); }
    private List<Rec> shown = new ArrayList<>();
    private ListView listView;
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_recipes);
        listView = findViewById(R.id.recipes);
        listView.setOnItemClickListener((p, v, pos, id) -> open(shown.get(pos)));
        refresh();
    }
    private void refresh() {
        List<Item> items = AppDb.get(this).items().all();
        long soon = System.currentTimeMillis() + 3L * 24 * 3600 * 1000;
        shown = new ArrayList<>();
        for (String line : DATA) {
            String[] f = line.split("\\|");
            Rec r = new Rec(); r.name = f[0]; r.ing = f[1].split(","); r.kcal = Integer.parseInt(f[2]); r.steps = f[3];
            for (String ing : r.ing) {
                Item found = null;
                for (Item i : items) {
                    String n = i.name.toLowerCase();
                    if (i.qty > 0 && (n.contains(ing) || ing.contains(n))) { found = i; break; }
                }
                if (found == null) r.missing.add(ing);
                else { r.have++; r.used.add(found); if (found.expiry > 0 && found.expiry <= soon) r.exp++; }
            }
            if (r.have > 0) shown.add(r);
        }
        Collections.sort(shown, (a, c) -> {
            if (a.exp != c.exp) return c.exp - a.exp;
            return Double.compare((double) c.have / c.ing.length, (double) a.have / a.ing.length);
        });
        List<String> rows = new ArrayList<>();
        for (Rec r : shown) rows.add(r.name + " - have " + r.have + "/" + r.ing.length + (r.exp > 0 ? "  (uses expiring items)" : ""));
        if (rows.isEmpty()) rows.add("No matches yet. Add items like egg, onion, tomato, rice in Kitchen.");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows));
    }
    private void open(Rec r) {
        String msg = "Ingredients: " + String.join(", ", r.ing)
                + (r.missing.isEmpty() ? "" : "\nMissing: " + String.join(", ", r.missing))
                + "\nAbout " + r.kcal + " kcal\n\n" + r.steps;
        new AlertDialog.Builder(this).setTitle(r.name).setMessage(msg)
            .setPositiveButton("Cook it (use ingredients)", (d, w) -> {
                for (Item i : r.used) AppDb.get(this).items().use(i.id);
                Entry e = new Entry(); e.type = "Diet"; e.text = "cooked " + r.name.toLowerCase(); e.value = r.kcal; e.time = System.currentTimeMillis();
                AppDb.get(this).dao().insert(e);
                refresh();
            }).setNegativeButton("Close", null).show();
    }
}
