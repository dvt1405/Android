package model;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.fitness.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class GuideAdapter extends BaseAdapter  {
    private List<Guide> listGuide;
    private Context context;
    private int layout;

    public GuideAdapter(List<Guide> listGuide, Context context, int layout) {
        this.listGuide = listGuide;
        this.context = context;
        this.layout = layout;
    }

    public List<Guide> getListGuide() {
        return listGuide;
    }

    public void setListGuide(List<Guide> listGuide) {
        this.listGuide = listGuide;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listGuide.size();
    }

    @Override
    public Object getItem(int i) {
        return listGuide.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listGuide.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewNameItem = view.findViewById(R.id.nameItemListView);
        Guide  guide = listGuide.get(i);
        textViewNameItem.setText(guide.getName());
        return view;
    }
}
