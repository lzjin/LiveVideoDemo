package com.danqiu.online.live.demo.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Admin on 2017/2/9.
 */

public class ToastUtil {

    private static Toast toast;
    /**居中**/
    public static void showShort(Context context,String text){
        if(toast == null) toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        else toast.setText(text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void showShort(Context context,int textRes){
        if(toast == null) toast = Toast.makeText(context,context.getResources().getString(textRes),Toast.LENGTH_SHORT);
        else toast.setText(context.getResources().getString(textRes));
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void showLong(Context context,String text){
        if(toast == null) toast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        else toast.setText(text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**底部**/
    public static void showShortBot(Context context,String text){
        if(toast == null) toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        else toast.setText(text);
        toast.setGravity(Gravity.BOTTOM,0,SystemUtil.dip2px(context,60));
        toast.show();
    }
    public static void showShortBot(Context context,int textRes){
        if(toast == null) toast = Toast.makeText(context,context.getResources().getString(textRes),Toast.LENGTH_SHORT);
        else toast.setText(context.getResources().getString(textRes));
        toast.setGravity(Gravity.BOTTOM,0,SystemUtil.dip2px(context,60));
        toast.show();
    }
    public static void showLongot(Context context,String text){
        if(toast == null) toast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        else toast.setText(text);
        toast.setGravity(Gravity.BOTTOM,0,SystemUtil.dip2px(context,60));
        toast.show();
    }

}
