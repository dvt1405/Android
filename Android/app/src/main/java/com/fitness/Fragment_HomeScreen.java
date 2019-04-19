package com.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.GuideAdapter;
import model.Practice;
import model.PracticeGroup;
import model.PracticeGroupAdapter;

public class Fragment_HomeScreen extends Fragment {
    private List<PracticeGroup> listPracticeGroup;
    private ListView listView;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesreen, container, false);
        initView(view);
        //------------Set adapter-----------
        PracticeGroupAdapter adapter = new PracticeGroupAdapter(listPracticeGroup, container.getContext(), R.layout.item_listview_homescreen);
        listView.setAdapter(adapter);

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //-------------Click an item------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentListPractice fragmentListPractice = new FragmentListPractice();
                Bundle bundle = new Bundle();
                bundle.putSerializable("practicegroup",listPracticeGroup.get(i));
                bundle.putString("bundle", "bundle");
                fragmentListPractice.setArguments(bundle);
                fragmentTransaction.replace(R.id.root_frame,fragmentListPractice);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public void initView(View view) {
        this.listView = view.findViewById(R.id.listViewTab1);
        this.listPracticeGroup = new ArrayList<>();
        //-----Data-Test------
        Guide guid1 = new Guide("Guide 1", 2, "");
        Guide guid2 = new Guide("Guide 2", 2, "");
        List<Guide> listGuid1 = new ArrayList<>();
        List<Guide> listGuid2 = new ArrayList<>();
        List<Guide> listGuid3 = new ArrayList<>();

        listGuid1.add(guid1);
        listGuid1.add(guid2);
        listGuid2.add(guid2);
        listGuid2.add(guid2);
        listGuid3.add(guid2);
        listGuid3.add(guid1);
        listGuid3.add(guid2);

        Practice practice1 = new Practice("Practice 1",listGuid1,2,"");
        Practice practice2 = new Practice("Practice 2",listGuid2,2,"");
        Practice practice3 = new Practice("Practice 3",listGuid3,2,"");
        List<Practice> listPracetice1 = new ArrayList<>();
        List<Practice> listPracetice2 = new ArrayList<>();
        List<Practice> listPracetice3 = new ArrayList<>();
        listPracetice1.add(practice1);
        listPracetice1.add(practice3);
        listPracetice2.add(practice1);
        listPracetice2.add(practice2);
        listPracetice2.add(practice3);
        listPracetice3.add(practice3);

        PracticeGroup practiceGroup1 = new PracticeGroup("Group 1",1,listPracetice1,"" );
        PracticeGroup practiceGroup2 = new PracticeGroup("Group 2",1,listPracetice2,"" );
        PracticeGroup practiceGroup3 = new PracticeGroup("Group 3",1,listPracetice3,"" );
        this.listPracticeGroup.add(practiceGroup1);
        this.listPracticeGroup.add(practiceGroup2);
        this.listPracticeGroup.add(practiceGroup3);
        this.listPracticeGroup.add(practiceGroup1);
    }

}
