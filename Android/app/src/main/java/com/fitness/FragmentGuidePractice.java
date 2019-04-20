package com.fitness;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import model.Practice;

public class FragmentGuidePractice extends Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_practice,container, false);
        initView(view);
        Bundle getPractice = getArguments();
        Practice practice = (Practice) getPractice.getSerializable("practice");
        textView.setText(practice.getName());
        return view;
    }
    public void initView(View view) {
        textView = view.findViewById(R.id.textViewGuideScreen);
    }
}
