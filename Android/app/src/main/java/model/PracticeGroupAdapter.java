package model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.R;

import java.util.List;

public class PracticeGroupAdapter extends BaseAdapter {
    private List<PracticeGroup> listPracticeGroup;
    private Context context;
    private int layout;

    public PracticeGroupAdapter(List<PracticeGroup> listPracticeGroup, Context context, int layout) {
        this.listPracticeGroup = listPracticeGroup;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listPracticeGroup.size();
    }

    @Override
    public Object getItem(int i) {
        return listPracticeGroup.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listPracticeGroup.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewNameItem = view.findViewById(R.id.nameItemListView);
        ImageView imageView = view.findViewById(R.id.imageViewItem);
        PracticeGroup  practiceGroup = listPracticeGroup.get(i);
        textViewNameItem.setText(practiceGroup.getName());
        imageView.setImageResource(practiceGroup.getAvatar());
        return view;
    }
}
