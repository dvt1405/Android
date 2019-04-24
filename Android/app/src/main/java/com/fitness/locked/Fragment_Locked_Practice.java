package com.fitness.locked;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fitness.R;

import java.util.List;

import model.PracticeGroup;
import model.PracticeGroupAdapter;

public class Fragment_Locked_Practice extends Fragment {
    private ListView listView;
    private List<PracticeGroup> listPracticeGroup;
    private final String passworkConnect ="d3ea5409901f250d2d658d6b94fdf7d8";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesreen, container, false);
        initView(view);



        return view;
    }

    private void initView(View view) {

    }


}
