package com.example.lenovo.myapplication.mvp.user.fragment;

/**
 * Created by lenovo on 2018/5/24.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.base.view.activity.SSWebView;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.user.adapter.FollowFragmentAdapter;
import com.example.lenovo.myapplication.utils.GlideImageLoader;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.SearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingin on 2018/3/19.
 */

public class FollowFragment extends Fragment{

    private List <String> images = new ArrayList<String>();
    private View view;
    private String businessListStr;
    private RecyclerView recyclerView;
    private LinearLayout no_data_view;
    private List<Goods> goodsList = new ArrayList<>();
    private FollowFragmentAdapter followFragmentAdapter;
    private SmartRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_follow,container,false);
         initView();

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
             @Override
             public void onLoadMore(RefreshLayout refreshLayout) {
                 upPullRefresh(refreshLayout);
             }
         });
         StaggeredGridLayoutManager sgLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(sgLayoutManager);

        //轮播
        //初始化
        images.add(Data.url+"lunbotu/lunbotu1.jpg");
        images.add(Data.url+"lunbotu/lunbotu2.jpg");
        images.add(Data.url+"lunbotu/lunbotu3.jpg");

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        SSWebView.readGo(getContext(),"1","https://sale.jd.com/mall/NLTKmPrftebA1g6c.html");
                        break;
                    case 1:
                        SSWebView.readGo(getContext(),"2","https://baijiahao.baidu.com/s?id=1630952463255699521&wfr=spider&for=pc");

                        break;
                    case 2:
                        SSWebView.readGo(getContext(),"3","https://baijiahao.baidu.com/s?id=1631127831998783086&wfr=spider&for=pc");
                        break;
                    default:
                        break;

                }
            }
        });
        banner.start();


        ImageView ivsousuo = view.findViewById(R.id.ivsousuo);
        ivsousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),SearchActivity.class);
                startActivityForResult(intent,0);
            }
        });


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Data.url + "Guanzhu_index?user_id="+UserManage.getInstance().getUserInfo(getContext()).getId())
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = null;
            try {
                response = call.execute();
                businessListStr = response.body().string();
                Log.e("businessListStr",businessListStr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<List<Goods>>(){}.getType();
            goodsList = gson.fromJson(businessListStr, type);
            if (goodsList.size()>0){
                refreshLayout.setVisibility(View.VISIBLE);
                no_data_view.setVisibility(View.GONE);
                followFragmentAdapter = new FollowFragmentAdapter(getContext());
                followFragmentAdapter.setNewData(goodsList);
                recyclerView.setAdapter(followFragmentAdapter);
            }else {
                no_data_view.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
            }


        return view;

    }


    private void upPullRefresh(RefreshLayout refreshLayout){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "Guanzhu_index?user_id="+UserManage.getInstance().getUserInfo(getContext()).getId())
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            businessListStr = response.body().string();
            Log.e("businessListStr",businessListStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();
        goodsList = gson.fromJson(businessListStr, type);
        followFragmentAdapter.getData().clear();
        followFragmentAdapter.notifyDataSetChanged();
        followFragmentAdapter.addData(goodsList);
        refreshLayout.finishLoadMore(2000);

    }

    private void initView(){
        refreshLayout = view.findViewById(R.id.refrfeshFollowLayout);
        recyclerView = view.findViewById(R.id.frag_follow_recycler);
        no_data_view = view.findViewById(R.id.no_data_view);
    }
}
