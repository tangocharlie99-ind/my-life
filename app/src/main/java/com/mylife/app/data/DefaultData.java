package com.mylife.app.data;

import java.util.ArrayList;
import java.util.List;

public class DefaultData {
    public static List<FoodDbEntry> foods() {
        List<FoodDbEntry> list = new ArrayList<>();
        add(list, "Roti / Chapati", 104, 3, 22, "1 piece (40g)");
        add(list, "White Rice (cooked)", 206, 4, 45, "1 cup (158g)");
        add(list, "Brown Rice (cooked)", 216, 5, 45, "1 cup (195g)");
        add(list, "Dal / Toor Dal (cooked)", 198, 12, 28, "1 cup (198g)");
        add(list, "Rajma (cooked)", 216, 15, 39, "1 cup (177g)");
        add(list, "Chana / Chickpeas (cooked)", 269, 15, 45, "1 cup (164g)");
        add(list, "Paneer", 296, 20, 6, "100 g");
        add(list, "Chicken Curry (home)", 260, 22, 8, "1 serving (200g)");
        add(list, "Egg (boiled)", 78, 6, 1, "1 large");
        add(list, "Idli", 39, 2, 8, "1 piece");
        add(list, "Dosa (plain)", 133, 3, 21, "1 medium");
        add(list, "Poha", 250, 5, 45, "1 bowl (150g)");
        add(list, "Upma", 260, 6, 42, "1 bowl (200g)");
        add(list, "Samosa", 262, 4, 32, "1 medium");
        add(list, "Biryani (chicken)", 480, 26, 55, "1 plate (350g)");
        add(list, "Curd / Yogurt", 98, 5, 12, "1 cup (245g)");
        add(list, "Milk (full cream)", 148, 8, 12, "1 cup (244ml)");
        add(list, "Tea with milk & sugar", 90, 2, 15, "1 cup (240ml)");
        add(list, "Coffee with milk", 110, 3, 17, "1 cup (240ml)");
        add(list, "Banana", 105, 1, 27, "1 medium (118g)");
        add(list, "Apple", 95, 0, 25, "1 medium (182g)");
        add(list, "Orange", 62, 1, 15, "1 medium (131g)");
        add(list, "Almonds", 164, 6, 6, "28 g (~23 nuts)");
        add(list, "Gulab Jamun", 143, 2, 20, "1 piece");
        add(list, "Chai + Parle-G", 220, 3, 35, "1 cup + 4 biscuits");
        add(list, "Pav Bhaji", 400, 9, 55, "1 plate (300g)");
        add(list, "Vada Pav", 290, 6, 42, "1 piece");
        add(list, "Mango (fresh)", 99, 1, 25, "1 cup sliced");
        add(list, "Palak Paneer", 220, 11, 10, "1 serving (200g)");
        add(list, "Chicken Tikka", 190, 25, 4, "100 g");
        add(list, "Fish Curry (home)", 210, 22, 6, "1 serving (200g)");
        add(list, "Oats (cooked)", 166, 6, 28, "1 cup");
        add(list, "Whole Wheat Bread", 80, 4, 14, "1 slice");
        add(list, "Peanut Butter", 188, 8, 6, "2 tbsp");
        return list;
    }

    private static void add(List<FoodDbEntry> l, String n, int c, int p, int cb, String s) {
        FoodDbEntry e = new FoodDbEntry();
        e.name = n;
        e.calories = c;
        e.proteinGrams = p;
        e.carbGrams = cb;
        e.serving = s;
        l.add(e);
    }
}
