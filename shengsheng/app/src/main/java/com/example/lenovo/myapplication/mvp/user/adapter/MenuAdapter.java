package com.example.lenovo.myapplication.mvp.user.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.mvp.user.activity.MessageActivity;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.ServiceActivity;
import com.example.lenovo.myapplication.mvp.user.activity.SettingActivity;
import com.example.lenovo.myapplication.mvp.user.activity.CollectedActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/23.
 */

public class MenuAdapter extends BaseAdapter {
    private List<Map<String,Object>> data;
    private Activity context;
    private int item_layout_id;
    public MenuAdapter(Activity context, int item_layout_id, List<Map<String,Object>> data){
        this.context = context;
        this.item_layout_id = item_layout_id;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }
        ImageView imageView = convertView.findViewById(R.id.list_img);
        TextView name = convertView.findViewById(R.id.list_item);

        Map<String,Object> map= data.get(position);

        imageView.setImageResource((int)map.get("src"));
        name.setText(map.get("name").toString());





        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (position ==0){
                    intent = new Intent(context,CollectedActivity.class);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }else if(position==3) {
                    intent = new Intent(context, ServiceActivity.class);

                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }else if(position==2){
                    intent = new Intent(context,SettingActivity.class);

                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }else if(position==1){
                    intent = new Intent(context,MessageActivity.class);

                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }

            }
        });


        return convertView;

    }

}
