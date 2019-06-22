package com.example.lenovo.myapplication.mvp.business.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.manager.BusinessManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.utils.StreamTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.typeList;

/**
 * Created by lenovo on 2018/5/26.
 */

public class AddGoodsActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_PERMISSION = 2;
    private static String TAG="appGoods";
    private Goods goods;
    private OkHttpClient okHttpClient  = new OkHttpClient();
    private String Goods_typeStr;
    private String responseStr;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText Add_Goods_name;
    private EditText Add_Goods_info;
    private TextView Add_Goods_starTime;
    private TextView Add_Goods_endTime;
    private ImageView Add_Goods_image;
    private TextView Add_Goods_back;
    private Button Add_Goods_Button;
    private Date StarTime;
    private Date EndTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        goods=new Goods();
        spinner=findViewById(R.id.Goods_Add_type);
        Add_Goods_name=findViewById(R.id.Add_Goods_name);
        Add_Goods_info=findViewById(R.id.Add_Goods_info);
        Add_Goods_starTime=findViewById(R.id.Add_Goods_starTime);
        Add_Goods_endTime=findViewById(R.id.Add_Goods_endTime);
        Add_Goods_image=findViewById(R.id.Add_Goods_image);
        Add_Goods_back=findViewById(R.id.Goods_add_back);
        Add_Goods_Button=findViewById(R.id.Add_Goods);
        //实现下拉列表选择类型
        Request request=new Request.Builder().url(Data.url+"GetGoodsType").build();
        final Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            Goods_typeStr=response.body().string();
            Log.e("Goods_type",Goods_typeStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        Type type=new TypeToken<List<String>>(){}.getType();
        typeList=gson.fromJson(Goods_typeStr,type);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        //实现返回按钮
        Add_Goods_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGoodsActivity.this.finish();
            }
        });
       //上传头像
        Add_Goods_image.setFocusable(true);
        Add_Goods_image.setFocusableInTouchMode(true);
        Add_Goods_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开手机相册，动态申请权限；
                ActivityCompat.requestPermissions(AddGoodsActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);
                Log.e("**************","*****************");
            }
        });
        //设置开始时间
        Add_Goods_starTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置函数以便选择时间
                showDialogPick((TextView) v);
            }
        });
        //设置结束时间
        Add_Goods_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置函数以便选择时间
                showDialogPick((TextView) v);
            }
        });
        //添加商品
        Add_Goods_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Goods_name=Add_Goods_name.getText().toString();
                String Goods_image="G"+Goods_name+Data.business.getId()+".jpg";
                String Goods_type=spinner.getSelectedItem().toString();
                String Goods_info=Add_Goods_info.getText().toString();
                String StarTimeStr=Add_Goods_starTime.getText().toString();
                String EndTimeStr=Add_Goods_endTime.getText().toString();
                long times = StreamTools.getDatePoor(StarTimeStr,EndTimeStr,StarTime,EndTime);
                if (times<1){
                    Toast.makeText(AddGoodsActivity.this,"商品活动日期小于一天，请重新输入",Toast.LENGTH_SHORT)
                            .show();
                    Add_Goods_endTime.setText("");
                    Add_Goods_starTime.setText("");
                }else {
                    goods.setBusiness_id(Data.business.getId());
                    goods.setName(Goods_name);
                    goods.setPrice(0.00);
                    goods.setImage(Goods_image);
                    goods.setType(Goods_type);
                    goods.setInfo(Goods_info);
                    Gson gson1=new Gson();
                    String goodsStr=gson1.toJson(goods);
                    RequestBody requestBody=RequestBody.create(MediaType.parse("goods"),goodsStr);
                    Request request1=new Request
                            .Builder()
                            .url(Data.url+"TianJiaGoods?starttime="+StarTimeStr+"&endtime="+EndTimeStr)
                            .post(requestBody)
                            .build();
                    Call call1=okHttpClient.newCall(request1);
                    try {
                        Response response=call1.execute();
                        responseStr=response.body().string();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (responseStr!=null){
                        Toast.makeText(AddGoodsActivity.this,responseStr,Toast.LENGTH_SHORT)
                                .show();
                        Intent intent=new Intent(AddGoodsActivity.this,BuinessActivity.class);

                        startActivity(intent);
                        finish();
                    }
                }


            }
        });
    }
    //将两个选择时间的dialog放在该函数中
    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化TimePickerDialog对象
        final TimePickerDialog timePickerDialog = new TimePickerDialog(AddGoodsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(" "  + hourOfDay + ":" + minute);
                //设置TextView显示最终选择的时间
                timeText.setText(time);
            }
        }, hour, minute, true);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddGoodsActivity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time.append(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                //选择完日期后弹出选择时间对话框
                timePickerDialog.show();
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }


    /*
    上传方法
     */
    private void doUploadFile(File file)  {

        OkHttpClient mOkHttpClient = new OkHttpClient();
        String imgname=null;
        try {
            imgname=URLDecoder.decode("G"+Add_Goods_name.getText().toString()+BusinessManage.getInstance().getBusinessInfo(this).getId(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*; charset=utf-8"),file);
        Request request = new Request.Builder().url(Data.url+"UploadBusinessFile?name="+imgname)
                .post(requestBody).build();
        Call call=mOkHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,response.body().string());
                Log.e("aaaaa","上传成功");
            }
        });

    }
    //相册界面返回之后回调方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取照片
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //获取照片
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            String column = MediaStore.Images.Media.DATA;
            int columIndex = cursor.getColumnIndex(column);
            String path = cursor.getString(columIndex);
            File file = new File(path);
            Glide.with(this).load(file).into(Add_Goods_image);
            doUploadFile(file);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //打开手机相册
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE);
    }
}
