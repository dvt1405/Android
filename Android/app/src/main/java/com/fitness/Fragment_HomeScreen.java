package com.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.GuideAdapter;
import model.Guide_Practice;
import model.Practice;
import model.PracticeAdapter;
import model.PracticeGroup;
import model.PracticeGroupAdapter;
import model.Practice_PrGroup;
import sqlite.DBManager;
import sqlite.GuideDAO;
import sqlite.Guide_PracticeDAO;
import sqlite.PracticeDAO;
import sqlite.PracticeGroupDAO;
import sqlite.Practice_PrGroupDAO;

public class Fragment_HomeScreen extends Fragment {
    private List<PracticeGroup> listPracticeGroup;
    private ListView listView;
    private LinearLayout linearLayout;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesreen, container, false);
        initView(view);
        //------------Set adapter-----------
        PracticeGroupAdapter adapter = new PracticeGroupAdapter(listPracticeGroup, container.getContext(), R.layout.item_listview_homescreen);
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        listView.setAdapter(adapter);
        //-------------Click an item------------------
        listView.setOnItemClickListener(onItemClicked(fragmentTransaction));
        return view;
    }

    public View onReselected(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homesreen, container, false);
    }

    private AdapterView.OnItemClickListener onItemClicked(final FragmentTransaction fragmentTransaction) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentListPractice fragmentListPractice = new FragmentListPractice();
                Bundle bundle = new Bundle();
                bundle.putSerializable("practicegroup", listPracticeGroup.get(i));
                bundle.putString("bundle", "bundle");
                fragmentListPractice.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragmentListPractice);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
                fragmentTransaction.addToBackStack("backStackHomeScreen");
                fragmentTransaction.commit();
            }
        };
    }

    public void initView(View view) {
        this.listView = view.findViewById(R.id.listViewTab1);
        this.listPracticeGroup = new ArrayList<>();
        this.linearLayout = view.findViewById(R.id.linerLayoutFragmentHomesSreen);
        PracticeGroupDAO dbManager = new PracticeGroupDAO(getActivity().getBaseContext());
        listPracticeGroup = dbManager.getAllPracticeGroup();


//        List<Practice> practiceList = new PracticeDAO(getActivity().getBaseContext()).getAllPractice();
//        List<Guide> guideList = new GuideDAO(getActivity().getBaseContext()).getAllGuideDataBase();
//        new Practice_PrGroupDAO(getActivity().getBaseContext()).addPractice_PrGroup(new Practice_PrGroup(practiceList.get(1),listPracticeGroup.get(0)));
//        new Practice_PrGroupDAO(getActivity().getBaseContext()).addPractice_PrGroup(new Practice_PrGroup(practiceList.get(2),listPracticeGroup.get(0)));
//        new Practice_PrGroupDAO(getActivity().getBaseContext()).addPractice_PrGroup(new Practice_PrGroup(practiceList.get(3),listPracticeGroup.get(1)));
//        new Practice_PrGroupDAO(getActivity().getBaseContext()).addPractice_PrGroup(new Practice_PrGroup(practiceList.get(4),listPracticeGroup.get(3)));
//        Guide_PracticeDAO guide_practiceDAO = new Guide_PracticeDAO(getActivity().getBaseContext());
//        guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(1),guideList.get(3)));
//        guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(2),guideList.get(2)));
//        guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(3),guideList.get(1)));
//        guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(4),guideList.get(6)));
    }


}
