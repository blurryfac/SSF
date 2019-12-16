package com.shanchuang.ssf.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.utils.DividerItemDecoration;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.shanchuang.ssf.R;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JY on 2018/4/11.
 */

public class DialogUtil {


    private Dialog dialog;
    private RecyclerView rec;
    private DialogAdapter mAdapter;
    private int pos;
    private OnItemClickListener mOnItemClickListener;
    private OnPayClickListener mOnPayClickListener;//支付
    private OnShareClickListener mOnShareClickListener;//分享
    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.mOnShareClickListener = onShareClickListener;
    }
    public interface OnShareClickListener {
        void qqOnClick(View v);

        void circleOnClick(View v);

        void qZoneOnClick(View v);

        void weixinOnClick(View v);

    }

    //获取单例
    public static DialogUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void showDialog(Activity activity, List<String> list, TextView tv) {
        dialog = new Dialog(activity, R.style.Theme_Light_Dialog_1);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_list, null);
        rec = dialogView.findViewById(R.id.rec_text);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        rec.setLayoutManager(linearLayoutManager);
        rec.setNestedScrollingEnabled(false);
        rec.addItemDecoration(new DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new DialogAdapter(list, activity);
        mAdapter.setOnItemClickListener(new DialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                dialog.dismiss();
                mOnItemClickListener.onClick(v, position);

            }


        });
        rec.setAdapter(mAdapter);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();


    }
    public void setBottomDialog(Activity activity, Dialog fullDialog, View view) {

        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
//        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(view);
    }

    public Dialog showShareDialog(Activity activity) {
        Dialog shareDialog = new Dialog(activity, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.share_dialog, null);
        TextView tv_weixin_share = (TextView) dialogView.findViewById(R.id.tv_share_weixin);
        TextView tv_circle_share = (TextView) dialogView.findViewById(R.id.tv_share_wxcircle);
        TextView tv_qq_share = (TextView) dialogView.findViewById(R.id.tv_share_qq);
        TextView tv_qzone_share = (TextView) dialogView.findViewById(R.id.tv_share_qq_zone);
        ImageView iv_back = (ImageView) dialogView.findViewById(R.id.iv_share_close);
        //获得dialog的window窗口
        setWindow1(shareDialog, dialogView, activity);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        tv_weixin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信分享
                mOnShareClickListener.weixinOnClick(v);
            }
        });
        tv_qq_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qq分享
                mOnShareClickListener.qqOnClick(v);

            }
        });
        tv_circle_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //朋友圈分享
                mOnShareClickListener.circleOnClick(v);
            }
        });
        tv_qzone_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QQ空间分享
                mOnShareClickListener.qZoneOnClick(v);
            }
        });
        return shareDialog;
    }
    public static void ShareWeb(int thumb_img, SHARE_MEDIA o, final Activity activity, String title, String desc, String url) {
        UMImage thumb = new UMImage(activity, thumb_img);
        UMWeb umWeb = new UMWeb(url);
        umWeb.setThumb(thumb);
        umWeb.setTitle(title);
        umWeb.setDescription(desc);
        new ShareAction(activity).withMedia(umWeb).setPlatform(o).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "分享取消", Toast.LENGTH_SHORT).show();
            }
        }).share();
    }
    public View showFullDialog(Activity activity, Dialog fullDialog, int layout) {

        View dialogView = LayoutInflater.from(activity).inflate(layout, null);
        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        //window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(dialogView);

        return dialogView;
    }
    public View ChoiceImgDialog(Activity activity, Dialog dialog, int layout) {

        View dialogView = LayoutInflater.from(activity).inflate(layout, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(10, 0, 10, 10);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        return dialogView;
    }
    public void setDialog(Activity activity, AlertDialog fullDialog, View view, int gravity, int padding) {

        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
//        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(gravity);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(padding, 0, padding, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(view);
    }
    public void setDialog1(Activity activity, AlertDialog fullDialog, View view, int gravity, int padding) {

        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(gravity);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(padding, 0, padding, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(view);
    }
    public void setDialog2(Activity activity, Dialog fullDialog, View view, int gravity, int padding) {

        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(gravity);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(padding, 0, padding, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(view);
    }
    public void setDialog3(Activity activity, Dialog fullDialog, View view, int gravity, int padding) {

        //获得dialog的window窗口
        Window window = fullDialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(gravity);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(padding, 0, padding, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        fullDialog.setContentView(view);
    }
    public void showDatePicker(Activity activity, final TextView tv) {
        Calendar cale1 = Calendar.getInstance();
        new DatePickerDialog(activity, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //这里获取到的月份需要加上1哦~
                tv.setText(+year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        }
                , cale1.get(Calendar.YEAR)
                , cale1.get(Calendar.MONTH)
                , cale1.get(Calendar.DAY_OF_MONTH)).show();
    }

    public Dialog showPayDialog(Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.Theme_Light_Dialog_1);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.pay_dialog, null);
        RadioButton tv_weixin_share = (RadioButton) dialogView.findViewById(R.id.tv_share_weixin);
        RadioButton tv_qq_share = (RadioButton) dialogView.findViewById(R.id.tv_share_ali);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) dialogView.findViewById(R.id.tv_confirm);
        setWindow(dialog, dialogView, activity);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_weixin_share.isChecked()){
                    mOnPayClickListener.onWeiXinPay(v);
                }else if(tv_qq_share.isChecked()){
                    mOnPayClickListener.onAliPay(v);
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public void setWindow(Dialog dialog, View view, Activity activity) {
        Window window = dialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(view);
    }
    public void setWindow1(Dialog dialog, View view, Activity activity) {
        Window window = dialog.getWindow();
        WindowManager wm = activity.getWindowManager();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(50, 0, 50, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(view);
    }
    public void setOnPayClickListener(OnPayClickListener onPayClickListener) {
        this.mOnPayClickListener = onPayClickListener;
    }

    public interface OnPayClickListener {
        void onYuE(View v);//支付宝支付
        void onAliPay(View v);//支付宝支付

        void onWeiXinPay(View v);//微信支付
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);


    }

    private static class SingletonHolder {
        private static final DialogUtil INSTANCE = new DialogUtil();
    }

}
