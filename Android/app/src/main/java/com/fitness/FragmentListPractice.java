package com.fitness;

import android.support.v7.app.ActionBar;
import android.drm.DrmStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import model.Practice;
import model.PracticeAdapter;
import model.PracticeGroup;

public class FragmentListPractice extends Fragment {
    private ListView listView;
    private PracticeGroup practiceGroup; // get from Fragment_Home_Screen
    private Toolbar toolbar;
    private ImageButton buttonBack;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listpractice_screen, container, false);
        initView(view);
        //---------Get Practicegroup from fragment_homescreen-----------
        Bundle getPracticeGroup = getArguments();
        if (getPracticeGroup != null) {
            practiceGroup = (PracticeGroup) getPracticeGroup.getSerializable("practicegroup");
            listView.setAdapter(new PracticeAdapter(practiceGroup.getListPractice(), container.getContext(), R.layout.item_listview_homescreen));
            listView.setOnItemClickListener(onItemClicked());
        }

        return view;
    }

    public void initView(View view) {
        listView = view.findViewById(R.id.listViewPracticeScreen);
        toolbar = getActivity().findViewById(R.id.toolbarListPractice);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        textView = activity.findViewById(R.id.textToolbar);
        textView.setText(R.string.app_name);
    }


    private AdapterView.OnItemClickListener onItemClicked() {
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentGuidePractice fragmentGuidePractice = new FragmentGuidePractice();
                Bundle sendPractice = new Bundle();
                sendPractice.putSerializable("practice", practiceGroup.getListPractice().get(i));
                fragmentGuidePractice.setArguments(sendPractice);
                fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragmentGuidePractice);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }

    private View.OnClickListener buttonBackClicked() {
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.frameLayoutMainActivity, new Fragment_HomeScreen());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }
}
