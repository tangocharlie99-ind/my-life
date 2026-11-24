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
import com.mylife.app.data.JournalEntry;

import java.util.List;

public class JournalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        TextView title = v.findViewById(R.id.tv_title);
        LinearLayout list = v.findViewById(R.id.list);
        title.setText("Journal");

        new Thread(() -> {
            final List<JournalEntry> entries =
                    AppDatabase.get(requireContext()).journalDao().getAll();
            if (getActivity() == null) return;
            requireActivity().runOnUiThread(() -> {
                list.removeAllViews();
                if (entries.isEmpty()) {
                    TextView empty = new TextView(requireContext());
                    empty.setText("No entries yet.\nQuick-Capture arrives in Phase 1.");
                    empty.setPadding(16, 32, 16, 16);
                    empty.setGravity(Gravity.CENTER_HORIZONTAL);
                    list.addView(empty);
                } else {
                    for (JournalEntry e : entries) {
                        TextView tv = new TextView(requireContext());
                        tv.setText(e.category + " — " + e.description);
                        tv.setPadding(16, 16, 16, 16);
                        list.addView(tv);
                    }
                }
            });
        }).start();
        return v;
    }
}
