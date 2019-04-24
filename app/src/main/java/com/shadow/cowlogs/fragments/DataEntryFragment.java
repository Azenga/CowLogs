package com.shadow.cowlogs.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shadow.cowlogs.MainActivity;
import com.shadow.cowlogs.R;
import com.shadow.cowlogs.models.LogEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */


public class DataEntryFragment extends Fragment {
    private static final String TAG = "DataEntryFragment";

    private static final String BREED_PARAM = "cow-breed-category";

    //Widgets
    private TextView breedTV;
    private EditText idET, weightET, ageET;
    private Spinner conditionSpinner;
    private Button saveLogBtn, showLogBtn, previousBtn, nextBtn, homeBtn;
    private ProgressDialog progressDialog;

    private List<LogEntry> logEntryList = null;

    private int currentEntry = 0;

    private Button.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previousBtn) {
                if (currentEntry < 0) return;
                else {
                    currentEntry -= 1;
                }
            } else if (v == nextBtn) {
                if (currentEntry == logEntryList.size() - 1){
                    currentEntry = 0;
                    return;
                }
                else {
                    currentEntry += 1;
                }
            }


            populateFields();
        }
    };
    private void populateFields() {
        LogEntry entry = logEntryList.get(currentEntry);

        idET.setText(String.valueOf(entry.getId()));
        weightET.setText(String.valueOf(entry.getWeight()));
        ageET.setText(String.valueOf(entry.getAge()));
    }


    private String breed = null;
    private EntryListener mListener;

    private SimpleDateFormat dateFormat = null;

    public DataEntryFragment() {
    }

    public static DataEntryFragment newInstance(String breed) {

        Bundle args = new Bundle();
        args.putString(BREED_PARAM, breed);
        DataEntryFragment fragment = new DataEntryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logEntryList = new ArrayList<>();

        if (getArguments() != null) breed = getArguments().getString(BREED_PARAM);

        for (LogEntry entry : MainActivity.logEntryList) {
            if (entry.getBreed().equalsIgnoreCase(breed)) {
                logEntryList.add(entry);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        // Required empty public constructor
        progressDialog = new ProgressDialog(getActivity());
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        breedTV = view.findViewById(R.id.breed_tv);
        if (breed != null) breedTV.setText(breed.toUpperCase());

        idET = view.findViewById(R.id.id_et);
        weightET = view.findViewById(R.id.weight_et);
        ageET = view.findViewById(R.id.age_et);

        conditionSpinner = view.findViewById(R.id.condition_spinner);

        saveLogBtn = view.findViewById(R.id.save_log_btn);
        saveLogBtn.setOnClickListener(v -> addLog());

        showLogBtn = view.findViewById(R.id.show_log_btn);
        showLogBtn.setOnClickListener(v -> {
            if (mListener == null) return;
            mListener.showLogEtryFrag(breed);
        });

        previousBtn = view.findViewById(R.id.prev_btn);
        previousBtn.setOnClickListener(btnListener);
        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(btnListener);
        homeBtn = view.findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(v -> {
            mListener.displayHomeFrag();
        });


        if(logEntryList.size() > 0){

            LogEntry entry = logEntryList.get(currentEntry);

            idET.setText(String.valueOf(entry.getId()));
            weightET.setText(String.valueOf(entry.getWeight()));
            ageET.setText(String.valueOf(entry.getAge()));
        }

    }

    private void addLog() {
        if (emptyFields()) {
            Toast.makeText(getActivity(), "Entry not saved, empty fields\nFill them and again later", Toast.LENGTH_SHORT).show();
        } else {

            String condition = conditionSpinner.getSelectedItem().toString().trim();

            Date now = new Date();
            String dateTime = dateFormat.format(now);
            int id = Integer.parseInt(idET.getText().toString().trim());
            double weight = Double.parseDouble(weightET.getText().toString().trim());
            int age = Integer.parseInt(ageET.getText().toString().trim());

            LogEntry entry = new LogEntry(condition, dateTime, breed, id, weight, age);

            if (mListener == null) return;

            mListener.saveEntry(entry);
            idET.setText("");
            weightET.setText("");
            ageET.setText("");

            Toast.makeText(getActivity(), "Entry saved", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean emptyFields() {

        if (idET.getText().toString().trim().isEmpty()) {
            idET.requestFocus();
            return true;
        }
        if (weightET.getText().toString().trim().isEmpty()) {
            weightET.requestFocus();
            return true;
        }
        if (ageET.getText().toString().trim().isEmpty()) {
            ageET.requestFocus();
            return true;
        }
        if (conditionSpinner.getSelectedItem().toString().trim().isEmpty()) return true;

        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof EntryListener) mListener = (EntryListener) context;
        else throw new ClassCastException(context.toString() + " must implement EntryListener");
    }

    public interface EntryListener {

        void saveEntry(LogEntry entry);

        void showLogEtryFrag(String breed);

        void displayHomeFrag();
    }
}
