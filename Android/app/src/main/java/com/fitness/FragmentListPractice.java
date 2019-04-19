package com.fitness;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import model.PracticeAdapter;
import model.PracticeGroup;

public class FragmentListPractice extends Fragment {
    private ListView listView;
    ImageButton imageButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listpractice_screen,container,false);
        listView = view.findViewById(R.id.listViewPracticeScreen);
        imageButton = view.findViewById(R.id.buttonBackPracticeScreen);

        //---------Get Practicegroup from fragment homescreen-----------
        Bundle bundle = getArguments();
        Log.e("Get bundle success","vvvv");
        if(bundle!=null) {
            Log.e("Get bundle success",bundle.getString("bundle"));
            PracticeGroup practiceGroup = (PracticeGroup) bundle.getSerializable("practicegroup");
            Log.e("Get bundle success", practiceGroup.getName());
            listView.setAdapter(new PracticeAdapter(practiceGroup.getListPractice(),container.getContext(),R.layout.item_listview_homescreen));
        }
        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.root_frame, new Fragment_HomeScreen());
                fragmentTransaction.commit();
            }
        });

        return view;
        //----------BackButton CLick------------
    }
}
