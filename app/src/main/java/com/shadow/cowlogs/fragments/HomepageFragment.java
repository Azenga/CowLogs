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
import android.widget.Toast;

import com.shadow.cowlogs.MainActivity;
import com.shadow.cowlogs.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomepageFragment extends Fragment {
    private static final String TAG = "HomepageFragment";
    private HomepageListener mListener = null;

    private Button.OnClickListener btnsListener = v -> {
        String breed = null;

        switch (v.getId()) {
            case R.id.angus_btn:
                breed = "angus";
                break;
            case R.id.hereford_btn:
                breed = "hereford";
                break;
            case R.id.brahman_btn:
                breed = "brahman";
                break;
            case R.id.shorthorn_btn:
                breed = "shorthorn";
                break;
            case R.id.brangus_btn:
                breed = "brangus";
                break;
            default:
                Toast.makeText(getActivity(), "This will never show", Toast.LENGTH_SHORT).show();
        }

        mListener.loadDataEntry(breed);
    };


    public HomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        super.onAttach(context);

        if (context instanceof HomepageListener) mListener = (HomepageListener) context;
        else throw new ClassCastException(context.toString() + " must implement EntryListener");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button angusBtn, herefordBtn, brahmanBtn, shorthornBtn, brangusBtn;

        angusBtn = view.findViewById(R.id.angus_btn);
        angusBtn.setOnClickListener(btnsListener);

        herefordBtn = view.findViewById(R.id.hereford_btn);
        herefordBtn.setOnClickListener(btnsListener);

        brahmanBtn = view.findViewById(R.id.brahman_btn);
        brahmanBtn.setOnClickListener(btnsListener);

        shorthornBtn = view.findViewById(R.id.shorthorn_btn);
        shorthornBtn.setOnClickListener(btnsListener);

        brangusBtn = view.findViewById(R.id.brangus_btn);
        brangusBtn.setOnClickListener(btnsListener);
    }

    public interface HomepageListener{
        void loadDataEntry(String breed);
    }
}
