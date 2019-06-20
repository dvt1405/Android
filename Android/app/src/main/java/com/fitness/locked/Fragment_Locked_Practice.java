package com.fitness.locked;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fitness.R;

import java.util.List;
import java.util.zip.Inflater;

import model.PracticeGroup;
import model.PracticeGroupAdapter;
import sqlite.PracticeGroupDAO;

public class Fragment_Locked_Practice extends Fragment {
    private ListView listView;
    private List<PracticeGroup> listPracticeGroup;
    Button payMoMo;
    private final String passworkConnect ="d3ea5409901f250d2d658d6b94fdf7d8";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_locked, container, false);
        initView(view);
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listPracticeGroup.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_checkout,null);
                PracticeGroup practiceGroup = listPracticeGroup.get(position);
                ImageView imageView = convertView.findViewById(R.id.imageViewItemCheckout);
                TextView textView = convertView.findViewById(R.id.nameItemCheckout);
                textView.setText(practiceGroup.getName());
                imageView.setImageResource(practiceGroup.getAvatar());
                return convertView;
            }
        };
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Checkout.class);
                intent.putExtra("practice", listPracticeGroup.get(position));
                startActivity(intent);
            }
        });
        payMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Checkout.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void initView(View view) {
        payMoMo = view.findViewById(R.id.payMoMo);
        listView = view.findViewById(R.id.listPracticeCheckout);
        PracticeGroupDAO practiceGroupDAO = new PracticeGroupDAO(getContext());
        listPracticeGroup = practiceGroupDAO.getAllPracticeGroupLocked();

    }


}
