package kr.ac.changchang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Todo_checkListAdapter extends BaseAdapter {
    private Context context;
    private List<Todo_checkList> items;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
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

        Todo_checkList item = items.get(position); // 현재 항목 가져오기

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        TextView textView = convertView.findViewById(R.id.text);

        // 데이터 설정
        textView.setText(item.getText());
        checkBox.setChecked(item.isChecked());

        // 체크박스 클릭 이벤트
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deleteTodolist(item.getId(), position); // item.getId() 사용
            }
        });

        return convertView;
    }
    private void deleteTodolist(int todoId, int position) {
        Call<Void> call = apiService.deleteTodo(todoId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    // 리스트에서 해당 항목 제거
                    items.remove(position);


                    // 어댑터에 변경사항 알림
                    notifyDataSetChanged();

                    // ListView의 높이를 동적으로 조정
                    ViewGroup.LayoutParams params = ((Todo) context).findViewById(R.id.listView1).getLayoutParams();
                    params.height = (int) (60 * items.size() * context.getResources().getDisplayMetrics().density);
                    ((Todo) context).findViewById(R.id.listView1).setLayoutParams(params);


                } else {
                    Toast.makeText(context, "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

