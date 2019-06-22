package com.example.lenovo.myapplication.mvp.main.view.activity;




import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.user.adapter.MenuAdapter;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.ShopGoodsTypeActivity;
import com.example.lenovo.myapplication.mvp.user.activity.UpdateActivity;
import com.example.lenovo.myapplication.mvp.user.fragment.DiscoveryFragment;
import com.example.lenovo.myapplication.mvp.user.fragment.FollowFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends FragmentActivity {

    private FollowFragment f_a;
    private DiscoveryFragment f_b;
    private Fragment[] mFragments;
    private int mIndex;
    private RadioGroup radioGroup;
    TextView qianming;
    TextView qiandao;
    private boolean flag = true;
    private long exitTime = 0;
    private ImageView head_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDate();
        initView();
        initFragment();//方法一，默认第一fragment

    }
    private void initDate(){
        List<Map<String,Object>> list=new ArrayList<>();

        Map<String,Object> map1=new HashMap<>();
        map1.put("src",R.drawable.shoucang);
        map1.put("name","收藏");

        Map<String,Object> map4=new HashMap<>();
        map4.put("src",R.drawable.message_center);
        map4.put("name","消息中心");

        Map<String,Object> map6=new HashMap<>();
        map6.put("src",R.drawable.setting);
        map6.put("name","设置");



        list.add(map1);
        list.add(map4);
        list.add(map6);

        MenuAdapter adapter=new MenuAdapter(
                MainActivity.this,
                R.layout.list_item_layout,list
        );
        ListView listView= findViewById(R.id.lv);
        listView.setAdapter(adapter);



        // 点击头像修改个人信息
        head_image = findViewById(R.id.image_user);

        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);

                startActivityForResult(intent,0);
            }
        });


        qianming = findViewById(R.id.qianming_user);
        qiandao = findViewById(R.id.qiandao_date);
        TextView name =findViewById(R.id.name_user);
        //获取用户姓名和个人信息
        Glide.with(MainActivity.this)
                .load(Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage())
                .into(head_image);
        Log.e("urlImage",Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage());
        name.setText(UserManage.getInstance().getUserInfo(this).getUserName());

        qianming.setText(Data.user.getQianming());
        qiandao.setText(UserManage.getInstance().getUserInfo(MainActivity.this).getQiandao()+"天");
        Button qiandaoButton = findViewById(R.id.qiandao_user);
        qiandaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long day = (new Date().getTime()-Data.user.getQiandao_date().getTime())/(1000*60*60*24);

                if (day>1){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Data.url + "UserQianDao?user_id="+Data.user.getId())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"请明天再来！",Toast.LENGTH_SHORT).show();
                }
                qiandao.setText((Integer.parseInt(UserManage.getInstance().getUserInfo(MainActivity.this).getQiandao())+1)+"天");
            }
        });



    }

    private void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int arg1) {
                //遍历RadioGroup 里面所有的子控件。
                for (int index = 0; index < group.getChildCount(); index++) {
                    //获取到指定位置的RadioButton
                    RadioButton rb = (RadioButton)group.getChildAt(index);
                    //如果被选中
                    if (rb.isChecked()) {
                        setIndexSelected(index);
                        //setIndexSelectedTwo(index);  //方法二
                        break;
                    }
                }

            }
        });

    }
    //方法一，默认第一fragment
    private void initFragment() {
        f_a =new FollowFragment();
        f_b =new DiscoveryFragment();
        //f_c =new nearby_fragment();

        //添加到数组
        mFragments = new Fragment[]{f_a,f_b};
        //开启事务
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        //添加首页
        ft.add(R.id.content,f_a).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }
    //方法一，选中显示与隐藏
    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.content,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex=index;

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        qianming.setText(Data.user.getQianming());
    }


    Intent intent = new Intent();
    public void clickEvent(View view) {
                Button button1 = view.findViewById(R.id.yifu);
                Button button2 = view.findViewById(R.id.shuma);
                Button button3 = view.findViewById(R.id.meishi);
                Button button4 = view.findViewById(R.id.jiaju);
                Button button5 = view.findViewById(R.id.meizhuang);
            switch (view.getId()){
                case R.id.yifu:
                    intent.putExtra("name", button1.getText());
                    intent.setClass(MainActivity.this,ShopGoodsTypeActivity.class);
                    startActivityForResult(intent,0);
                    break;
                case R.id.shuma:
                    intent.putExtra("name", button2.getText());
                    intent.setClass(MainActivity.this,ShopGoodsTypeActivity.class);
                    startActivityForResult(intent,0);
                    break;
                case R.id.meishi:
                    intent.putExtra("name", button3.getText());
                    intent.setClass(MainActivity.this,ShopGoodsTypeActivity.class);
                    startActivityForResult(intent,0);
                    break;
                case R.id.jiaju:
                    intent.putExtra("name",button4.getText());
                    intent.setClass(MainActivity.this,ShopGoodsTypeActivity.class);
                    startActivityForResult(intent,0);
                    break;
                case R.id.meizhuang:
                    intent.putExtra("name", button5.getText());
                    intent.setClass(MainActivity.this,ShopGoodsTypeActivity.class);
                    startActivityForResult(intent,0);
                    break;
            }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort(R.string.one_more_exit);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(MainActivity.this)
                .load(Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage())
                .into(head_image);
        Log.e("head_image",Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage());
    }
}

