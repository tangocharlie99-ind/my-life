package com.mylife.app.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mylife.app.R;
import com.mylife.app.data.AppDatabase;
import com.mylife.app.data.Goal;

import java.util.List;

public class GoalsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        TextView title = v.findViewById(R.id.tv_title);
        LinearLayout list = v.findViewById(R.id.list);
        title.setText("Goals");

        new Thread(() -> {
            final List<Goal> goals =
                    AppDatabase.get(requireContext()).goalDao().getAll();
            if (getActivity() == null) return;
            requireActivity().runOnUiThread(() -> {
                list.removeAllViews();
                if (goals.isEmpty()) {
                    TextView empty = new TextView(requireContext());
                    empty.setText("No goals yet.\nGoal management arrives in Phase 2.");
                    empty.setPadding(16, 32, 16, 16);
                    empty.setGravity(Gravity.CENTER_HORIZONTAL);
                    list.addView(empty);
                } else {
                    for (Goal g : goals) {
                        TextView tv = new TextView(requireContext());
                        tv.setText(g.title + " — " + g.progressPercent + "% (streak " + g.streakDays + "d)");
                        tv.setPadding(16, 16, 16, 16);
                        list.addView(tv);
                    }
                }
            });
        }).start();
        return v;
    }
}
