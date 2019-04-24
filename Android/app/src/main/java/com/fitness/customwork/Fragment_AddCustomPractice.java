package com.fitness.customwork;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import model.Practice;
import model.PracticeAdapter;
import model.PracticeGroup;
import model.Practice_PrGroup;
import sqlite.PracticeDAO;
import sqlite.PracticeGroupDAO;
import sqlite.Practice_PrGroupDAO;

public class Fragment_AddCustomPractice extends Fragment {
    private Button buttonToolBar;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView textView;
    private ImageButton buttonBack;
    private ListView listView;
    private List<Pair<Practice,Boolean>> listPactice = new ArrayList<>();
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private String nameCustom ="";
    private List<Pair<Practice,Integer>> listCustomPractice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcustompractice,container,false);
        initView(view);

        buttonBack.setOnClickListener(onButtonBackToolbarClicked);
        buttonToolBar.setOnClickListener(onbuttonToolBarClicked);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkBox = view.findViewById(R.id.checkBoxItemAddPractice);
                checkBox.setChecked(!checkBox.isChecked());
                if(checkBox.isChecked()) {
                    Pair<Practice,Integer> pair = new Pair<>(listPactice.get(i).first,Integer.valueOf(i));
                    listCustomPractice.add(pair);
                }else {
                    for(int j =0; j< listCustomPractice.size();j++) {
                        int k = listCustomPractice.get(j).second;
                        if(k==i) listCustomPractice.remove(i);
                    }
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        listPactice = new ArrayList<>();
        listView = view.findViewById(R.id.listViewAddPractice);
        toolbarLayout = view.findViewById(R.id.toolbarAddCustomPractice);
        buttonToolBar = toolbarLayout.findViewById(R.id.buttonEndToolbar);
        textView = toolbarLayout.findViewById(R.id.textToolbar);
        textView.setText("Create YourWork");
        buttonBack = toolbarLayout.findViewById(R.id.buttonToolbar);
        ViewGroup.LayoutParams btnParam =  buttonToolBar.getLayoutParams();
        btnParam.width = 125;
        buttonToolBar.setBackgroundResource(R.drawable.ic_check);
        textInputEditText = view.findViewById(R.id.editTextFragmentAddPractice);
        textInputLayout = view.findViewById(R.id.textInput);
        listCustomPractice = new ArrayList<>();
        new Async().execute();

    }
    private View.OnClickListener onbuttonToolBarClicked
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PracticeGroup practiceGroup = new PracticeGroup();
            practiceGroup.setDescription("custom");
            Practice_PrGroupDAO practice_prGroupDAO = new Practice_PrGroupDAO(getContext());
            PracticeGroupDAO practiceGroupDAO = new PracticeGroupDAO(getContext());
            if(checkInputText()){
                practiceGroup.setName(nameCustom);
                practiceGroupDAO.addPracticeGroup(practiceGroup);
                practiceGroup.setId(practiceGroupDAO.getPracticeGroupByName(nameCustom).getId());
                Log.e("ID UPDATE: ", practiceGroup.getId()+"");
                for(int i =0; i<listCustomPractice.size();i++) {
                    Practice_PrGroup practice_prGroup = new Practice_PrGroup(listCustomPractice.get(i).first,practiceGroup);
                    practice_prGroupDAO.addPractice_PrGroup(practice_prGroup);

                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutMainActivity, new Fragment_CustomWorkScreen())
                        .commit();
            }

        }
    };
    private View.OnClickListener onButtonBackToolbarClicked
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().popBackStack("backStackCustomWork",1);
        }
    };

    public void closeKeyboard() {

    }
    public Boolean checkInputText() {
        nameCustom = "";
        String s = textInputLayout.getEditText().getText().toString().trim();
        if (s.equalsIgnoreCase("")) {
            textInputLayout.setError("Name can't be null");
        }
        else {
            nameCustom+=s;
            textInputLayout.setError(null);
            return true;
        }
        return false;
    }

    private class Async extends AsyncTask<Practice,Practice,List<Practice>> {
        private List<Practice> listPracticeAsync ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listPracticeAsync = new ArrayList<>();
        }

        @Override
        protected List<Practice> doInBackground(Practice... practices) {
            final List<Practice> list = new PracticeDAO(getActivity().getBaseContext()).getAllPractice();
            Log.e("Async","");
            for(int i=0; i<list.size();i++) {
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
        protected void onPostExecute(List<Practice> practices) {
            super.onPostExecute(practices);
            listPracticeAsync = practices;
        }

        @Override
        protected void onProgressUpdate(Practice... values) {
            super.onProgressUpdate(values);
            listPracticeAsync.add(values[0]);
            listPactice.add(new Pair<Practice, Boolean>(values[0],false));
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
                    view = inflater.inflate(R.layout.item_listview_addpractice,null);
                    TextView textView = view.findViewById(R.id.textViewItemAddPractice);
                    textView.setText(listPracticeAsync.get(i).getName());
                    CheckBox checkBox = view.findViewById(R.id.checkBoxItemAddPractice);
                    return view;
                }
            };
            listView.setAdapter(adapter);
        }
    }

}
