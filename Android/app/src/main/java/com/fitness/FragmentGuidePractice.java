package com.fitness;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import model.Practice;

public class FragmentGuidePractice extends Fragment {
    private TextView textView;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageButton backButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_practice,container, false);
        initView(view);
        Bundle getPractice = getArguments();
        Practice practice = (Practice) getPractice.getSerializable("practice");
        textView.setText(practice.getName());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("backStackListPractice",1);
            }
        });

        return view;
    }
    public void initView(View view) {
        toolbarLayout = view.findViewById(R.id.toolbarGuidePractice);
        textView = toolbarLayout.findViewById(R.id.textToolbar);
        backButton = toolbarLayout.findViewById(R.id.buttonToolbar);
    }
}
