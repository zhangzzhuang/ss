package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.map.UserNotice;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/29.
 */

public class MessageActivity extends Activity {
        public String date;
        public ListView lv;
        public ArrayList<UserNotice> listuser;
        public ImageView msg_back;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_message);

            msg_back =(ImageView)findViewById(R.id.msg_back);
            msg_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            lv = (ListView) findViewById(R.id.lv_message);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Data.url+"Notice_user?user_id="+Data.user.getId()).build();

            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                date=response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<UserNotice>>(){}.getType();
            listuser= gson.fromJson(date,type);

                    useradapter useradapter=new useradapter(MessageActivity.this,listuser);
                    lv.setAdapter(useradapter);
        }
        public class useradapter extends BaseAdapter {
            public Context con;
            public List<UserNotice> listUser = new ArrayList<>();


            public useradapter(Context context, List<UserNotice> listUser) {
                this.con=context;
                this.listUser = listUser;
            }

            @Override
            public int getCount() {
                return listUser.size();
            }

            @Override
            public Object getItem(int position) {
                return listUser.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater mInflater=LayoutInflater.from(con);
                    convertView = mInflater.inflate(R.layout.message_list, null);
                }
                TextView info =  convertView.findViewById(R.id.info_message);
                TextView time =  convertView.findViewById(R.id.time_message);
                ImageView img = convertView.findViewById(R.id.notice_user);
                Glide.with(convertView)
                        .load(Data.urlImage + listUser.get(position).getImage())
                        .into(img);
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                info.setText("消息:    "+listUser.get(position).getInfo());
                time.setText("时间:      "+sdformat.format(listUser.get(position).getTime()));
                return convertView;
            }
        }


    }


