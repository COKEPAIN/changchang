package kr.ac.changchang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class Todo_checkListAdapter extends BaseAdapter {
    private Context context;
    private List<Todo_checkList> items;

    public Todo_checkListAdapter(Context context, List<Todo_checkList> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_checkout, parent, false);
        }

        Todo_checkList item = items.get(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        TextView textView = convertView.findViewById(R.id.text);

        // 데이터 설정
        textView.setText(item.getText());
        checkBox.setChecked(item.isChecked());

        // 체크박스 이벤트 핸들러
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            // 체크박스 선택 시 이벤트 처리
            if (isChecked) {
                // 예: 체크박스가 선택된 경우
                System.out.println(item.getText() + " is selected.");
            } else {
                // 예: 체크박스 선택이 해제된 경우
                System.out.println(item.getText() + " is deselected.");
            }
        });

        return convertView;
    }
}

