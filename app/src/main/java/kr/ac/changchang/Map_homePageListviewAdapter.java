package kr.ac.changchang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Map_homePageListviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map_homePageListview> itemList;

    // 생성자
    public Map_homePageListviewAdapter(Context context, List<Map_homePageListview> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size(); // 리스트의 아이템 개수 반환
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position); // 해당 위치의 아이템 반환
    }

    @Override
    public long getItemId(int position) {
        return position; // 위치를 ID로 사용
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 재사용 가능한 뷰가 없으면 새로 생성
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_view_homepage, parent, false);
        }

        // 현재 아이템 가져오기
        Map_homePageListview currentItem = itemList.get(position);

        // XML 레이아웃의 TextView에 데이터 바인딩
        TextView textView1 = convertView.findViewById(R.id.text1);
        TextView textView2 = convertView.findViewById(R.id.text2);

        textView1.setText(currentItem.getText1());
        textView2.setText(currentItem.getText2());

        return convertView;
    }
}
