package com.fitness.customwork;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fitness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import model.Practice;
import model.PracticeGroup;
import model.TestPlayMusicThread;
import sqlite.PracticeDAO;
import sqlite.PracticeGroupDAO;
import sqlite.Practice_PrGroupDAO;

public class Fragment_CustomWorkScreen extends Fragment {
    private Button buttonAdd;
    private List<PracticeGroup> listCustom;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customwork,container,false);
        initView(view);
        new Async().execute();
        buttonAdd.setOnClickListener(onButtonAddClick);
        return view;
    }
    public void initView(View view) {
        buttonAdd = view.findViewById(R.id.buttonAddCustomWork);
        listView = view.findViewById(R.id.listViewCustomWork);
        listCustom = new ArrayList<>();
    }
    private View.OnClickListener onButtonAddClick
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutMainActivity,new Fragment_AddCustomPractice())
                    .addToBackStack("backStackCustomWork")
                    .commit();

        }
    };

    private class Async extends AsyncTask<Void,PracticeGroup,List<PracticeGroup>> {
        private List<PracticeGroup> listPracticeAsync ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listPracticeAsync = new ArrayList<>();
        }

        @Override
        protected List<PracticeGroup> doInBackground(Void... practices) {
            final List<PracticeGroup> list = new PracticeGroupDAO(getActivity().getBaseContext()).getAllCustom();
            for(int i=0; i<list.size();i++) {
                list.get(i).setListPractice(new Practice_PrGroupDAO(getActivity().getBaseContext()).getListPracticeByIdGroup(list.get(i).getId()));
                if(list.get(i).getListPractice().size()==0) {
                    Log.e("Number of Practice: ", list.get(i).getListPractice().size()+"");
                    new PracticeGroupDAO(getActivity().getBaseContext()).deleteById(list.get(i));
                }
                publishProgress(list.get(i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<PracticeGroup> practiceGroups) {
            super.onPostExecute(practiceGroups);
            listPracticeAsync = practiceGroups;
        }

        @Override
        protected void onProgressUpdate(PracticeGroup... values) {
            super.onProgressUpdate(values);
            listPracticeAsync.add(values[0]);
            Log.e("ID: ", listPracticeAsync.size()+"");
            listCustom.add(values[0]);
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return listPracticeAsync.size();
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.item_customwork_container,null);
                    TextView textView = view.findViewById(R.id.itemCustomName);
                    textView.setText(listPracticeAsync.get(i).getName());

                    return view;
                }
            };
            listView.setAdapter(adapter);
        }
    }

}
