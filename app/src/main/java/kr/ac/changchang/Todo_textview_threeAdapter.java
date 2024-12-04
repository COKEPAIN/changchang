package kr.ac.changchang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Todo_textview_threeAdapter extends BaseAdapter {
    private Context context;
    private List<Todo_textview_three> items;

    public Todo_textview_threeAdapter(Context context, List<Todo_textview_three> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_textview_three, parent, false);
        }

        Todo_textview_three item = items.get(position);

        TextView text1 = convertView.findViewById(R.id.text1);
        TextView text2 = convertView.findViewById(R.id.text2);
        TextView text3 = convertView.findViewById(R.id.text3);

        text1.setText(item.getText1());
        text2.setText(item.getText2());
        text3.setText(item.getText3());

        return convertView;
    }
}
