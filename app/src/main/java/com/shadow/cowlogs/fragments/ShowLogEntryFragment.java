package com.shadow.cowlogs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.shadow.cowlogs.MainActivity;
import com.shadow.cowlogs.R;
import com.shadow.cowlogs.adapters.LogEntryAdapter;
import com.shadow.cowlogs.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowLogEntryFragment extends Fragment {


    public static final String COW_BREED = "current-breed";
    private List<LogEntry> logEntryList;

    private Button button;
    private ListView entryLogLV;
    private ShowLogEntryListener mListener;

    private String breed = null;

    public ShowLogEntryFragment() {
        logEntryList = new ArrayList<>();
    }

    public static ShowLogEntryFragment newInstance(String breed) {

        Bundle args = new Bundle();
        args.putString(COW_BREED, breed);
        ShowLogEntryFragment fragment = new ShowLogEntryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) breed = getArguments().getString(COW_BREED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_log_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        entryLogLV = view.findViewById(R.id.log_entry_lv);

        for (LogEntry entry : MainActivity.logEntryList) {
            if (entry.getBreed().equalsIgnoreCase(breed)) {
                logEntryList.add(entry);
            }
        }
        if (logEntryList.size() == 0) {
            Toast.makeText(getActivity(), "No Entries yet", Toast.LENGTH_SHORT).show();
        } else {
            LogEntryAdapter adapter = new LogEntryAdapter(getActivity(), logEntryList);

            entryLogLV.setAdapter(adapter);
        }

        button = view.findViewById(R.id.button);
        button.setText("Return to " + breed);
        button.setOnClickListener(v -> {
            mListener.returnToDataEntry(breed);
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        super.onAttach(context);

        if (context instanceof ShowLogEntryListener) mListener = (ShowLogEntryListener) context;
        else throw new ClassCastException(context.toString() + " must implement EntryListener");
    }

    public interface ShowLogEntryListener {
        void returnToDataEntry(String breed);
    }
}
