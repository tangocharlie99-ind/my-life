package com.mylife.app.ai;

import android.content.Context;

import java.util.Calendar;

public class AIManager {
    private static AIManager instance;
    private final Context ctx;

    private AIManager(Context c) {
        this.ctx = c.getApplicationContext();
    }

    public static synchronized AIManager get(Context c) {
        if (instance == null) instance = new AIManager(c);
        return instance;
    }

    public String suggestNextAction() {
        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        String timeContext;
        if (h < 9) {
            timeContext = "Start your day — log breakfast and set today's top goal.";
        } else if (h < 12) {
            timeContext = "Morning focus block: tackle your highest-priority task for 45 minutes.";
        } else if (h < 14) {
            timeContext = "Lunch time — log your meal and take a 10-minute walk.";
        } else if (h < 17) {
            timeContext = "Afternoon work block — check pending journal entries and clear any overdue task.";
        } else if (h < 20) {
            timeContext = "Evening — log dinner, review goals, and prep tomorrow's schedule.";
        } else {
            timeContext = "Wind down — journal your day, rate your mood, and get ready for sleep.";
        }
        return timeContext + "\n\n(" + String.format("%02d:%02d", h, m) + " local time)";
    }
}
