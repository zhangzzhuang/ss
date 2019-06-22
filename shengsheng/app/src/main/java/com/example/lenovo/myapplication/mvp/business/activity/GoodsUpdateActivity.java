package com.example.lenovo.myapplication.mvp.business.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.utils.DialogUtils;
import com.example.lenovo.myapplication.utils.StreamTools;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @User basi
 * @Date 2019/4/11
 * @Time 8:23 PM
 * @Description ${DESCRIPTION}
 */
public class GoodsUpdateActivity extends Activity {
    private static final int UPDSUC = 1;
    private String price;
    private String info;
    private String starteTime;
    private String endTime;
    private Date start;
    private Date end;
    private Goods goods;



    @BindView(R.id.up_Goods_content_image)
    ImageView up_Goods_content_image;
    @BindView(R.id.upPrice)
    EditText upPrice;
    @BindView(R.id.upInfo)
    EditText upInfo;
    @BindView(R.id.up_Goods_starTime)
    TextView up_Goods_starTime;
    @BindView(R.id.up_Goods_endTime)
    TextView up_Goods_endTime;
    @BindView(R.id.upStu)
    Button upStu;


    @OnTextChanged(value = R.id.up_Goods_endTime,callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onPwdEtChanged(CharSequence c, int i, int i1, int i2) {
        String s = up_Goods_starTime.getText().toString();
        if (!TextUtils.isEmpty(s)){
            if (c.length()>0){
                upStu.setBackgroundResource(R.drawable.shape);
                upStu.setClickable(true);
            }else {
                upStu.setBackgroundResource(R.drawable.login_btn_gray);
                upStu.setClickable(false);
            }
        }else {
            upStu.setClickable(false);
            upStu.setBackgroundResource(R.drawable.login_btn_gray);
        }
    }


    @OnClick(R.id.return_good_content)
    public void retContent(){
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_update_layout);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        goods = new Goods();

        goods = (Goods) intent.getSerializableExtra("goods");

        initView();


        upStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGood(String.valueOf(goods.getId()),String.valueOf(goods.getBusiness_id()));
            }
        });
        upStu.setClickable(false);

        //设置开始时间
        up_Goods_starTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置函数以便选择时间
                showDialogPick((TextView) v);
            }
        });
        //设置结束时间
        up_Goods_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置函数以便选择时间
                showDialogPick((TextView) v);
            }
        });

    }

//    http://192.168.43.221:8080/sx/UpdateGood?goods_id=21&business_id=1&price=12&info=1233&starttime=2019-04-11%2022:15:00.000000&endtime=2019-04-18%2022:15:00.000000
    private void updateGood( String good_id,String business_id){
        this.price = upPrice.getText().toString();
        this.info = upInfo.getText().toString();

        this.starteTime = up_Goods_starTime.getText().toString();
        this.endTime = up_Goods_endTime.getText().toString();


        long days = StreamTools.getDatePoor(starteTime,endTime,start,end);
        if (days<1){
            Toast.makeText(GoodsUpdateActivity.this,"商品活动日期小于一天，请重新输入",Toast.LENGTH_SHORT)
                    .show();
            up_Goods_starTime.setText("");
            up_Goods_endTime.setText("");
        }else {
            goods = new Goods();
            goods.setPrice(Double.valueOf(price));
            goods.setInfo(info);
            goods.setStarttime(StreamTools.StrToDate(starteTime));
            goods.setEndtime(StreamTools.StrToDate(endTime));
            Gson gson=new Gson();
            String goodsStr=gson.toJson(goods);

            RequestBody requestBody=RequestBody.create(MediaType.parse("goods"),goodsStr);

        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Data.url+"UpdateGood?goods_id="+good_id +"&business_id="+business_id+"&price="+price+"&info="+info+"&starttime="+starteTime+"&endtime="+endTime)
                .post(requestBody)
                .build();
        Log.e("ffff",price+"||"+info+"||"+starteTime+"||"+endTime);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Response response = null;
                try {
                    //回调
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Message message = new Message();
                        message.what = UPDSUC;
                        mhandler.sendMessage(message);
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        }
    }

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDSUC:
                    final DialogUtils dialogUtils = new DialogUtils(GoodsUpdateActivity.this,R.string.success_upd,true);
                    dialogUtils.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogUtils.dismiss();
                            finish();
                        }
                    },3000);
                    break;
                default:
                    break;
            }
        }
    };

   private void initView(){
       upPrice.setText(goods.getPrice().toString());
       upInfo.setText(goods.getInfo());

       Glide.with(GoodsUpdateActivity.this)
               .load(Data.urlImageBusiness+goods.getImage())
               .into(up_Goods_content_image);
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
        final TimePickerDialog timePickerDialog = new TimePickerDialog(GoodsUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(" "  + hourOfDay + ":" + minute);

                //设置TextView显示最终选择的时间
                timeText.setText(time);
            }
        }, hour, minute, true);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(GoodsUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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


}
