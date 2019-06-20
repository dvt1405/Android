package com.fitness.customwork;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fitness.MainActivity;
import com.fitness.R;
import com.fitness.homescreen.FragmentListPractice;
import com.fitness.locked.Checkout;

import java.io.Serializable;
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
        View view = inflater.inflate(R.layout.fragment_customwork, container, false);
        initView(view);
        new Async().execute();
        buttonAdd.setOnClickListener(onButtonAddClick);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
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
                    .replace(R.id.frameLayoutMainActivity, new Fragment_AddCustomPractice())
                    .addToBackStack("backStackCustomWork")
                    .commit();
//            Intent intent = new Intent(getActivity(),Checkout.class);
//            startActivity(intent);

        }
    };
    // Play Custom
    private AdapterView.OnItemClickListener onItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            FragmentListPractice fragmentListPractice = new FragmentListPractice();
            Bundle bundle = new Bundle();
            bundle.putSerializable("practicegroup", listCustom.get(i));
            fragmentListPractice.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutMainActivity, fragmentListPractice)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack("backStackCustomwork")
                    .commit();
        }
    };
    private AdapterView.OnItemLongClickListener onItemLongClickListener
            = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            PracticeGroup practiceGroup = listCustom.get(i);
            Bundle bundle = new Bundle();
            Fragment_AddCustomPractice fragment_addCustomPractice = new Fragment_AddCustomPractice();
            bundle.putSerializable("listPractice", (Serializable) practiceGroup);
            fragment_addCustomPractice.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutMainActivity,fragment_addCustomPractice)
                    .addToBackStack("backStackCustomWork")
                    .commit();
            Log.e("Chinh sua,",""+i);
            return false;
        }
    };

    private class Async extends AsyncTask<Void, PracticeGroup, List<PracticeGroup>> {
        private List<PracticeGroup> listPracticeAsync;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listPracticeAsync = new ArrayList<>();
        }

        @Override
        protected List<PracticeGroup> doInBackground(Void... practices) {
            final List<PracticeGroup> list = new PracticeGroupDAO(getActivity().getBaseContext()).getAllCustom();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setListPractice(new Practice_PrGroupDAO(getActivity().getBaseContext()).getListPracticeByIdGroup(list.get(i).getId()));
                if (list.get(i).getListPractice().size() == 0) {
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
            Log.e("ID: ", listPracticeAsync.size() + "");
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
                    view = inflater.inflate(R.layout.item_customwork_container, null);
                    TextView textView = view.findViewById(R.id.itemCustomName);
                    textView.setText(listPracticeAsync.get(i).getName());
                    return view;
                }
            };
            listView.setAdapter(adapter);
        }
    }

}
