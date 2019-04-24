package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.R;

import java.util.List;

public class PracticeAdapter extends BaseAdapter {
    private List<Practice> listPractice;
    private Context context;
    private int layout;

    public PracticeAdapter(List<Practice> listPractice, Context context, int layout) {
        this.listPractice = listPractice;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listPractice.size();
    }

    @Override
    public Object getItem(int i) {
        return listPractice.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listPractice.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewNameItem = view.findViewById(R.id.nameItemListView);
        ImageView imageView = view.findViewById(R.id.imageViewItem);
        Practice  practice = listPractice.get(i);
        textViewNameItem.setText(practice.getName());
        imageView.setImageResource(practice.getAvatar());
        return view;
    }
}
