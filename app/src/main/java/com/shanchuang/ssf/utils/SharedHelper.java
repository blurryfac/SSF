package com.shanchuang.ssf.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SharedHelper {
    private static final String UID = "uid";

    /**
     * 保存uid
     */
    public static void saveId(Context mContext, String uid) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(UID, uid);
        editor.apply();
    }




    /**
     * 保存uid
     */
    public static void saveSchoolId(Context mContext, String score) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("school_id", score);
        editor.apply();
    }
    /**
     * 保存uid
     */
    public static void saveOtherId(Context mContext, String score) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("other_id", score);
        editor.apply();
    }
    /**
     * 保存头像
     * @param mContext
     * @param avatar 头像
     */
    public static void saveAvatar(Context mContext, String avatar) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("avatar", avatar);
        editor.apply();
    }
    /**
     * 保存头像
     * @param mContext
     * @param mobile 手机
     */
    public static void saveMobile(Context mContext, String mobile) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }
    /**
     * 保存昵称
     * @param mContext
     * @param nick 昵称
     */
    public static void saveNick(Context mContext, String nick) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nick", nick);
        editor.apply();
    }
    /**
     * 读取头像
     * @param mContext
     * @return
     */
    public static String readAvatar(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString("avatar","");
    }
    /**
     * 读取积分
     * @param mContext
     * @return
     */
    public static String readSchoolId(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString("school_id","0");
    }
    /**
     * 读取积分
     * @param mContext
     * @return
     */
    public static String readOtherId(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString("other_id","0");
    }
    /**
     * 读取手机号
     * @param mContext
     * @return
     */
    public static String readMobile(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString("mobile","");
    }
    /**
     * 读取昵称
     * @param mContext
     * @return
     */
    public static String readNick(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString("nick","");
    }
    public static String readId(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        return sp.getString(UID, "");
    }
    /**
     * 清除SharedPreference
     */
    public static void clear(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(Context mContext, String tag, List<T> datalist) {
        SharedPreferences sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        if (null == datalist || datalist.size() <= 0)
//            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
//        editor.clear();
        editor.putString(tag, strJson);
        editor.apply();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static<T> List<T> getDataList(Context mContext, String tag) {
        SharedPreferences sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        List<T> datalist = new ArrayList<T>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }


}
