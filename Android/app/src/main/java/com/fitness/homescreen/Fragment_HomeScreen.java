package com.fitness.homescreen;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fitness.R;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.Guide_Practice;
import model.Practice;
import model.PracticeGroup;
import model.PracticeGroupAdapter;
import model.Practice_PrGroup;
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
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        PracticeGroupAdapter adapter = new PracticeGroupAdapter(listPracticeGroup, container.getContext(), R.layout.item_listview_homescreen);
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
                fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragmentListPractice)
                        .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                        .addToBackStack("backStackHomeScreen")
                        .commit();
            }
        };
    }

    public void initView(View view) {
        this.listView = view.findViewById(R.id.listViewTab1);
        this.linearLayout = view.findViewById(R.id.linerLayoutFragmentHomesSreen);
        PracticeGroupDAO dbManager = new PracticeGroupDAO(getActivity().getBaseContext());
        this.listPracticeGroup = dbManager.getAllPracticeGroup();

        //Data test
        List<Practice> practiceList = new PracticeDAO(getActivity().getBaseContext()).getAllPractice();
        List<Guide> guideList = new GuideDAO(getActivity().getBaseContext()).getAllGuideDataBase();
        Log.e("Size guide: ", guideList.size() + "");
        Log.e("Size practiceList: ", practiceList.size() + "");
        Practice_PrGroupDAO practice_prGroupDAO = new Practice_PrGroupDAO(getActivity().getBaseContext());
        if (practice_prGroupDAO.getGroup_PracticeDatabaseCount() == 0) {
            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(0), listPracticeGroup.get(0)));
            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(1), listPracticeGroup.get(1)));
            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(2), listPracticeGroup.get(1)));
            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(4), listPracticeGroup.get(1)));
            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(1), listPracticeGroup.get(2)));
//            practice_prGroupDAO.addPractice_PrGroup(new Practice_PrGroup(practiceList.get(3), listPracticeGroup.get(3)));
        }
        Guide_PracticeDAO guide_practiceDAO = new Guide_PracticeDAO(getActivity().getBaseContext());
        if (guide_practiceDAO.getGuide_PracticeDatabaseCount() == 0) {
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(0), guideList.get(0)));
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(0), guideList.get(1)));
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(1), guideList.get(2)));
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(2), guideList.get(3)));
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(3), guideList.get(4)));
            guide_practiceDAO.addGuide_Practice(new Guide_Practice(practiceList.get(4), guideList.get(5)));
        }
    }

    public static Fragment_HomeScreen getInstance(int number) {
        Fragment_HomeScreen fragment_homeScreen = new Fragment_HomeScreen();
        Bundle args = new Bundle();
        args.putInt("Number", number);
        fragment_homeScreen.setArguments(args);
        return fragment_homeScreen;
    }



    private class Async extends AsyncTask<Void,PracticeGroup,List<PracticeGroup>>{
        private List<Practice_PrGroup> listGroup;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listGroup = new ArrayList<>();
        }

        @Override
        protected List<PracticeGroup> doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onProgressUpdate(PracticeGroup... values) {
            super.onProgressUpdate(values);
        }
    }

}
