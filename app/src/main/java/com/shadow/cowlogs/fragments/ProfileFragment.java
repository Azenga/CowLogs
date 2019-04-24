package com.shadow.cowlogs.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shadow.cowlogs.R;
import com.shadow.cowlogs.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private EditText usernameET, pwdET, cpwdET;
    private Button saveProfileBtn, cancelProfileBtn;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameET = view.findViewById(R.id.name_et);
        pwdET = view.findViewById(R.id.pwd_et);
        cpwdET = view.findViewById(R.id.cpwd_et);

        saveProfileBtn = view.findViewById(R.id.save_profile_btn);
        cancelProfileBtn = view.findViewById(R.id.cancel_profile_btn);

        return view;
    }

    private void saveUsername() {


        if (emptyFields()) return;
        User user = new User(usernameET.getText().toString().trim(), pwdET.getText().toString().trim());
        Toast.makeText(getActivity(), "User saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        saveProfileBtn.setOnClickListener(v -> saveUsername());
    }

    private boolean emptyFields() {
        if (usernameET.getText().toString().trim().isEmpty()) {
            usernameET.requestFocus();
            usernameET.setError("Username required");
            return true;
        } else if (pwdET.getText().toString().trim().isEmpty()) {
            pwdET.requestFocus();
            pwdET.setError("Password required");
            return true;
        } else if (cpwdET.getText().toString().trim().isEmpty()) {
            cpwdET.requestFocus();
            cpwdET.setError("Please Confirm Password");
            return true;
        } else if (!pwdET.getText().toString().trim().equalsIgnoreCase(cpwdET.getText().toString().trim())) {
            cpwdET.requestFocus();
            cpwdET.setError("Passwords Do not match");
            return true;
        } else {
            return false;
        }

    }
}
