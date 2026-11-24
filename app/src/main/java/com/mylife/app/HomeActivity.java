package com.mylife.app;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class HomeActivity extends BaseActivity {
    @Override protected int navId() { return R.id.nav_home; }
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_home);
        findViewById(R.id.settings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
    @Override protected void onResume() {
        super.onResume();
        ((TextView) findViewById(R.id.date)).setText(new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(new Date()));
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        long midnight = c.getTimeInMillis();
        int cal = 0; boolean ate = false;
        List<Entry> entries = AppDb.get(this).dao().all();
        for (Entry e : entries) if (e.type.equals("Diet") && e.time >= midnight) { cal += e.value; ate = true; }
        ((TextView) findViewById(R.id.tvCal)).setText(cal + " / 2000 kcal");
        ((ProgressBar) findViewById(R.id.pbCal)).setProgress(Math.min(cal, 2000));
        StringBuilder exp = new StringBuilder(); String first = null;
        long now = System.currentTimeMillis(), DAY = 24L * 3600 * 1000;
        for (Item i : AppDb.get(this).items().all()) {
            if (i.expiry > 0 && i.expiry - now <= 3 * DAY) {
                long left = (long) Math.ceil((i.expiry - now) / (double) DAY);
                exp.append(i.name).append(left < 0 ? " (expired)" : " (" + left + "d)").append("\n");
                if (first == null) first = i.name;
            }
        }
        ((TextView) findViewById(R.id.tvExp)).setText(exp.length() == 0 ? "Nothing expiring in the next 3 days." : exp.toString().trim());
        String next;
        if (first != null) next = "Cook something with " + first + " before it expires. Open Recipes.";
        else if (hour >= 5 && hour < 11 && !ate) next = "Have breakfast and log it in the Journal.";
        else if (hour >= 11 && hour < 16 && !ate) next = "Time for lunch. Log what you eat.";
        else if (hour >= 16 && hour < 19) next = "Take a walk or study session, then plan dinner.";
        else if (hour >= 19 && hour < 22) next = "Have dinner, then write today's journal.";
        else if (hour >= 22 || hour < 5) next = "Wind down and get ready for sleep.";
        else next = "Log your latest activity in the Journal.";
        ((TextView) findViewById(R.id.tvNext)).setText(next);
    }
}
