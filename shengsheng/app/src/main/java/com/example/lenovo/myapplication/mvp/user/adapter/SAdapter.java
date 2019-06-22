package com.example.lenovo.myapplication.mvp.user.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.user.activity.UserLoginActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/23.
 */

//设置界面的Adaper
public class SAdapter extends BaseAdapter {
    private List<Map<String,Object>> data2;
    private Activity context2;
    private int item_layout_id2;
    public SAdapter(Activity context2,int item_layout_id2,List<Map<String,Object>> data2){
        this.context2 = context2;
        this.item_layout_id2 = item_layout_id2;
        this.data2 = data2;
    }
    @Override
    public int getCount() {
        return data2.size();
    }

    @Override
    public Object getItem(int position) {
        return data2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context2);
            convertView = inflater.inflate(item_layout_id2,null);
        }
        ImageView imageView = convertView.findViewById(R.id.setting_list_img);
        TextView name = convertView.findViewById(R.id.setting_list_item);

        Map<String,Object> map= data2.get(position);

        imageView.setImageResource((int)map.get("src"));
        name.setText(map.get("name").toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 2){

                    AlertDialog.Builder AdBuilder =
                            new AlertDialog.Builder(context2);
                    AdBuilder.setTitle("是否确认要退出当前账户");
                    AdBuilder.setNegativeButton("取消",null);
                    AdBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            UserManage.getInstance().delUserInfo(context2);
                            Intent intent = new Intent();
                            intent.setClass(context2,UserLoginActivity.class);
                            context2.startActivity(intent);
                            context2.finish();
                        }
                    });
                    AdBuilder.show();


                }

            }
        });

        return convertView;

    }

}
