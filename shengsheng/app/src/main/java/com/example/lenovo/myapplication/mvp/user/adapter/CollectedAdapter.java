package com.example.lenovo.myapplication.mvp.user.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.user.activity.GoodsdetialActivity;

import java.util.List;


/**
 * Created by dingin on 2018/5/27.
 */

public class CollectedAdapter extends BaseAdapter {
    private List<Goods> data;
    private Activity context;

    public CollectedAdapter(Activity context, List<Goods> data) {
        this.context = context;
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_collected_item, null);
        }
        ImageView imageView = convertView.findViewById(R.id.col_img);
        TextView name = convertView.findViewById(R.id.col_name);
        TextView info = convertView.findViewById(R.id.col_info);
        TextView index = convertView.findViewById(R.id.text_item_order_detail_index);

        Glide.with(context)
                .load(Data.urlImage + data.get(position).getImage())
                .into(imageView);
        name.setText(data.get(position).getName());
        info.setText(data.get(position).getInfo());
        index.setText(String.valueOf(position+1));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsdetialActivity.readGo(context,data.get(position));

            }
        });
        return convertView;
    }
}

