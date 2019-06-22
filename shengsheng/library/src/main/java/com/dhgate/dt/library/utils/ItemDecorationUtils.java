package com.dhgate.dt.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.dhgate.dt.library.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;


/**
 * ClassName ItemDecorationUtils
 * Description  RecyclerView divider工具类
 * 参考: https://github.com/yqritc/RecyclerView-FlexibleDivider
 * date createTime：2015/9/11 15:29
 * version
 */
public class ItemDecorationUtils {
    public static int color_comm_divider = 0xFFededed;//  R.color.tmc_divider_color

    public static int getStyleDividerColor(Context context) {
        /*if (!(context instanceof BaseActivity)) return color_comm_divider;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.divide_1, typedValue, true);
        try {
            color_comm_divider = Color.parseColor("" + typedValue.coerceToString());
        } catch (Exception e) {
        }*/
        return color_comm_divider;
    }


    /**
     * 宽度与RecyclerView一致
     */
    public static RecyclerView.ItemDecoration getCommFullDivider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度与RecyclerView一致
     * 垂直方向
     *
     * @param context
     * @param isShowLastDivider
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFullDividerVertical(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        VerticalDividerItemDecoration.Builder builder = new VerticalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 5 dp 分割
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFull5Divider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 10 dp 分割
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFull10Divider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp10);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 0.5 dp 分割
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFull05Divider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    public static RecyclerView.ItemDecoration getCommFull05Divider(Context context, boolean isShowLastDivider, int color) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(ContextCompat.getColor(context,color)).sizeResId(R.dimen.dp0_5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 5 dp 的透明
     */
    public static RecyclerView.ItemDecoration getCommTrans5Divider(Context context, boolean isShowLastDivider) {
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.dp5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    /**
     * 高度 10 dp 的透明
     */
    public static RecyclerView.ItemDecoration getCommTrans10Divider(Context context, boolean isShowLastDivider) {
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.dp10);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 10 dp 的透明+外边距
     */
    public static RecyclerView.ItemDecoration getCommTransMagin10Divider(Context context, boolean isShowLastDivider) {
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.dp10).marginResId(R.dimen.dp10, R.dimen.dp10);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    public static RecyclerView.ItemDecoration getCommTransMaginDivider(Context context, boolean isShowLastDivider, int color, int heightResId, int leftResId, int rightResId) {
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(ContextCompat.getColor(context,color)).sizeResId(heightResId).marginResId(leftResId, rightResId);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 外边距10 dp
     */
    public static RecyclerView.ItemDecoration getCommMagin10Divider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5).marginResId(R.dimen.dp10, R.dimen.dp10);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    /**
     * 外边距5 dp
     */
    public static RecyclerView.ItemDecoration getCommMagin5Divider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5).marginResId(R.dimen.dp5, R.dimen.dp5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    /**
     * 高度 10 dp 分割(横向) 透明
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommTrans10VerticalDivider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        VerticalDividerItemDecoration.Builder builder = new VerticalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.dp10);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

    /**
     * 高度 5 dp 分割(横向)
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFull5VerticalDivider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        VerticalDividerItemDecoration.Builder builder = new VerticalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.dp5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    /**
     * 高度 0.5 dp 分割(横向)
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getCommFull0p5VerticalDivider(Context context, boolean isShowLastDivider) {
        getStyleDividerColor(context);
        VerticalDividerItemDecoration.Builder builder = new VerticalDividerItemDecoration.Builder(context).color(color_comm_divider).sizeResId(R.dimen.dp0_5);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }


    public static RecyclerView.ItemDecoration getVerticalCommTransMaginDivider(Context context, boolean isShowLastDivider, int color, int heightResId, int leftResId, int rightResId) {
        VerticalDividerItemDecoration.Builder builder = new VerticalDividerItemDecoration.Builder(context).color(ContextCompat.getColor(context,color)).sizeResId(heightResId).marginResId(leftResId, rightResId);
        if (isShowLastDivider) builder.showLastDivider();
        return builder.build();
    }

}

