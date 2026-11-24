package com.mylife.app;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** Offline smart parser: turns a sentence into Diet / Fitness / Study entries. */
public class AIManager {
    private static final Map<String, Integer> KCAL = new LinkedHashMap<>();
    static {
        KCAL.put("chapati", 100); KCAL.put("roti", 100); KCAL.put("rice", 200); KCAL.put("dal", 150);
        KCAL.put("chicken curry", 300); KCAL.put("paneer", 280); KCAL.put("dosa", 130); KCAL.put("idli", 60);
        KCAL.put("egg", 78); KCAL.put("banana", 105); KCAL.put("apple", 95); KCAL.put("milk", 120);
        KCAL.put("samosa", 260); KCAL.put("biryani", 450); KCAL.put("poha", 180); KCAL.put("tea", 50);
    }
    private static final Pattern MOVE = Pattern.compile("(walked|ran|cycled|jogged)\\s+(\\d+(?:\\.\\d+)?)\\s*km");
    private static final Pattern STUDY = Pattern.compile("(studied|worked|read)\\s+(?:for\\s+)?(\\d+(?:\\.\\d+)?)\\s*(hours?|hrs?|h|minutes?|mins?)");
    private static final Pattern QTY = Pattern.compile("(\\d+)\\s");

    public List<Entry> parse(String input) {
        List<Entry> out = new ArrayList<>();
        boolean lastFood = false;
        for (String raw : input.toLowerCase().split("\\s+and\\s+|,")) {
            String p = raw.trim();
            if (p.isEmpty()) continue;
            Matcher m = MOVE.matcher(p);
            if (m.find()) { out.add(make("Fitness", p, (int) Math.round(Double.parseDouble(m.group(2)) * 60))); lastFood = false; continue; }
            m = STUDY.matcher(p);
            if (m.find()) {
                double n = Double.parseDouble(m.group(2));
                int mins = m.group(3).startsWith("m") ? (int) n : (int) (n * 60);
                out.add(make("Study", p, mins)); lastFood = false; continue;
            }
            if (p.startsWith("ate") || p.startsWith("had") || lastFood) {
                int kcal = 150; int qty = 1;
                Matcher q = QTY.matcher(p);
                if (q.find()) qty = Integer.parseInt(q.group(1));
                for (Map.Entry<String, Integer> f : KCAL.entrySet()) {
                    if (p.contains(f.getKey())) { kcal = f.getValue(); break; }
                }
                out.add(make("Diet", p, kcal * qty)); lastFood = true; continue;
            }
            out.add(make("Note", p, 0)); lastFood = false;
        }
        return out;
    }
    private Entry make(String type, String text, int value) {
        Entry e = new Entry(); e.type = type; e.text = text; e.value = value; e.time = System.currentTimeMillis(); return e;
    }
}
