package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.fitness.R;

import java.util.List;
import java.util.zip.Inflater;

public class CustomWorkAdater  extends BaseAdapter {
    private List<CustomWork> listCustomwork;
    private Context context;
    private int layout;

    public CustomWorkAdater(List<CustomWork> listCustomwork, Context context, int layout) {
        this.listCustomwork = listCustomwork;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listCustomwork.size();
    }

    @Override
    public Object getItem(int i) {
        return listCustomwork.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView textViewName = view.findViewById(R.id.itemCustomName);
        TextView textViewDate = view.findViewById(R.id.itemCustomDate);
        CustomWork customWork = listCustomwork.get(i);
        textViewName.setText(customWork.getName());
        textViewDate.setText(customWork.getDate());
        return view;
    }
}
