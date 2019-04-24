package com.fitness.homescreen;

import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fitness.R;

import java.util.ArrayList;
import java.util.List;

import model.Practice;
import model.PracticeAdapter;
import model.PracticeGroup;
import sqlite.Practice_PrGroupDAO;

public class FragmentListPractice extends Fragment {
    private ListView listView;
    private PracticeGroup practiceGroup; // get from Fragment_Home_Screen
    private ImageButton buttonBack;
    private TextView textView;
    private CollapsingToolbarLayout toolbarLayout;
    private Practice_PrGroupDAO practice_prGroupDAO;
    private List<Practice> listPractice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listpractice_screen, container, false);
        initView(view);
        //---------Get Practicegroup from fragment_homescreen-----------
        Bundle getPracticeGroup = getArguments();
        if (getPracticeGroup != null) {
            practiceGroup = (PracticeGroup) getPracticeGroup.getSerializable("practicegroup");
            textView.setText(practiceGroup.getName());
            listPractice = practice_prGroupDAO.getListPracticeByIdGroup(practiceGroup.getId());
            listView.setAdapter(new PracticeAdapter(listPractice, container.getContext(), R.layout.item_listview_homescreen));
            listView.setOnItemClickListener(onItemClicked());
        }

        buttonBack.setOnClickListener(buttonBackClicked());
        return view;
    }

    public void initView(View view) {
        listView = view.findViewById(R.id.listViewPracticeScreen);
        toolbarLayout = view.findViewById(R.id.toolbarListPractice);
        buttonBack = toolbarLayout.findViewById(R.id.buttonToolbar);
        textView = toolbarLayout.findViewById(R.id.textToolbar);
        listPractice = new ArrayList<>();
        practice_prGroupDAO = new Practice_PrGroupDAO(getActivity().getBaseContext());
    }

    private AdapterView.OnItemClickListener onItemClicked() {
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentGuidePractice fragmentGuidePractice = new FragmentGuidePractice();
                Bundle sendPractice = new Bundle();
                sendPractice.putSerializable("practice", listPractice.get(i));
                fragmentGuidePractice.setArguments(sendPractice);
                fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragmentGuidePractice);
                fragmentTransaction.addToBackStack("backStackListPractice");
                fragmentTransaction.commit();
            }
        };
    }

    private View.OnClickListener buttonBackClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack("backStackHomeScreen",1);
                getFragmentManager().popBackStack("backStackCustomwork",1);
            }
        };
    }

}
