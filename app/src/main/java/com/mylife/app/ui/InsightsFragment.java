package com.mylife.app.ui;

import android.os.Bundle;
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

public class InsightsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        TextView title = v.findViewById(R.id.tv_title);
        LinearLayout list = v.findViewById(R.id.list);
        title.setText("Insights");

        new Thread(() -> {
            final int entries = AppDatabase.get(requireContext()).journalDao().count();
            final int goals = AppDatabase.get(requireContext()).goalDao().count();
            final int foods = AppDatabase.get(requireContext()).foodDao().count();
            if (getActivity() == null) return;
            requireActivity().runOnUiThread(() -> {
                list.removeAllViews();
                addRow(list, "Total journal entries logged", String.valueOf(entries));
                addRow(list, "Total goals tracked", String.valueOf(goals));
                addRow(list, "Foods in local calorie database", String.valueOf(foods));
                addRow(list, "Chart visualisations", "Arriving in Phase 3");
            });
        }).start();
        return v;
    }

    private void addRow(LinearLayout parent, String label, String value) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(16, 16, 16, 16);
        TextView l = new TextView(requireContext());
        l.setText(label);
        l.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        TextView r = new TextView(requireContext());
        r.setText(value);
        row.addView(l);
        row.addView(r);
        parent.addView(row);
    }
}
