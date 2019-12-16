package com.shanchuang.ssf.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogLoading;
import com.vondear.rxui.view.dialog.RxDialogSure;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 腾讯WEB
 * Created by JY on 2018/3/22.
 */

public class TcWebActivity extends BaseActivity {

    /**
     * 调用支付宝
     */
    public static final int ALI_MSG = 0x10;
    RxDialogLoading rxDialogLoading;
    @BindView(R.id.web)
    WebView web;
    /**
     * 打开文件
     *
     * @param id 系统监听下载id
     */
    RxDialogSure rxDialogSure;
    //    实例化一个MyHandler对象
    MyHandler mHandler = new MyHandler(this);
    //图片返回列表
    List<LocalMedia> selectList = new ArrayList<>();
    private String url = "";
    private String loc[] = new String[3];
    private String pay_id = "";
    private Dialog tcDialog;
    /**
     * 广播监听下载进度
     *
     * @param Id 系统监听下载id
     */
    private BroadcastReceiver broadcastReceiver;
    private int status;
    private String path;
    private int payType;


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_layout;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        rxDialogLoading = new RxDialogLoading(this);
        rxDialogLoading.show();
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        initWeb(intent.getStringExtra("url"));
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                //当进度走到100的时候做自己的操作，我这边是弹出dialog
                Log.i("---------", progress + "");
                if (progress > 70) {
                    rxDialogLoading.dismiss();
                    if (web != null) {
                        web.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    void initWeb(String url) {
        //设置编码　　
        web.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        IX5WebViewExtension ix5 = web.getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
        web.setWebViewClient(new WebViewClient() {
            //设置在webView点x击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }
        });
        web.loadUrl(url);
        //设置本地调用对象及其接口
        //第一个参数为实例化自定义的接口对象 第二个参数为提供给JS端调用使用的对象名
        web.addJavascriptInterface(new Contact() {
            @JavascriptInterface
            @Override
            public void callAndroid(String YYWh5, String file) {
                Log.i("-----------", YYWh5 + "==========" + file);
                Intent intent = new Intent();
                switch (YYWh5) {
                    case "invitenum"://返回
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(file);
                        RxToast.normal("复制成功");
                        break;
                    case "back"://返回
                        showShareDialog(R.mipmap.logo, file);
                        break;
                    case "pay":
                        pay_id = file;
                        showPayDialog();
                        break;
                    case "tel"://打电话
                        call(YYWh5);
                        break;
                    case "share":

                        break;
                    case "download":
                        break;
                    case "detail":

                        break;
                    case "gaode1"://调起高德地图
                        loc = YYWh5.split(",");
                        boolean b = isPackageInstalled(TcWebActivity.this, "com.autonavi.minimap");
                        if (b) {
                            Intent naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat=&dlon=&dname=" + loc[2] + "&dev=0&t=2"));
                            TcWebActivity.this.startActivity(naviIntent);
                        } else {
                            RxToast.normal("未安装应用");
                        }
                        break;
                    case "gorder":
                        setResult(1);
                        finish();
                        break;
                    case "video":

                        break;
                    case "form2":

                        break;
                    case "form0":

                        break;

                }
            }


        }, "myObj");
        //载入js
    }

    /**
     * 分享弹框以及点击事件
     *
     * @param thumb_img 分享封面图
     */
    private void showShareDialog(final int thumb_img, String file) {
        tcDialog = DialogUtil.getInstance().showShareDialog(TcWebActivity.this);
        DialogUtil.getInstance().setOnShareClickListener(new DialogUtil.OnShareClickListener() {
            @Override
            public void qqOnClick(View v) {
                DialogUtil.ShareWeb(thumb_img, SHARE_MEDIA.QQ, TcWebActivity.this, HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.SHARE_URL + file);
            }

            @Override
            public void circleOnClick(View v) {
                DialogUtil.ShareWeb(thumb_img, SHARE_MEDIA.WEIXIN_CIRCLE, TcWebActivity.this, HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.SHARE_URL + file);
            }

            @Override
            public void qZoneOnClick(View v) {
                DialogUtil.ShareWeb(thumb_img, SHARE_MEDIA.QZONE, TcWebActivity.this, HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.SHARE_URL + file);
            }

            @Override
            public void weixinOnClick(View v) {
                DialogUtil.ShareWeb(thumb_img, SHARE_MEDIA.WEIXIN, TcWebActivity.this, HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.SHARE_URL + file);
            }
        });
        tcDialog.show();
    }

    /**
     * 打开支付弹窗
     */
    private void showPayDialog() {
        tcDialog = DialogUtil.getInstance().showPayDialog(TcWebActivity.this);
        DialogUtil.getInstance().setOnPayClickListener(new DialogUtil.OnPayClickListener() {
            @Override
            public void onYuE(View v) {
                payType = 1;
                tcDialog.dismiss();
            }

            @Override
            public void onAliPay(View v) {
                payType = 0;
                tcDialog.dismiss();

            }

            @Override
            public void onWeiXinPay(View v) {
                tcDialog.dismiss();
            }
        });
        tcDialog.show();

    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static boolean isPackageInstalled(Context mContext, String packagename) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        } finally {
            return packageInfo == null ? false : true;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                if (!PictureSelector.obtainMultipleResult(data).isEmpty()) {
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (status == 3) {
                        comPressVideo();
                    } else {
                        uploadImg();
                    }

                }


                break;
        }
    }

    /**
     * 压缩视频
     */
    private void comPressVideo() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    mHandler.sendEmptyMessage(0x10);
        /**
         * 视频压缩
         * 第一个参数:视频源文件路径
         * 第二个参数:压缩后视频保存的路径
         */
//                    path = SiliCompressor.with(TcWebActivity.this).compressVideo(selectList.get(0).getPath(), Environment.getExternalStorageDirectory().getPath());
//                    uploadVideo();
//                    Log.i("-------------", path);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
    }
//        }.start();
//    }

    /**
     * 上传图片
     */
    private void uploadImg() {
        SubscriberOnNextListener<BaseBean<List<String>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<String>>>() {
            @Override
            public void onNext(BaseBean<List<String>> baseBean) {
                if (200 == baseBean.getCode()) {
                    Log.i("------------", baseBean.toString());

                    for (int i = 0; i < baseBean.getData().size(); i++) {
                        switch (status) {
                            case 0:
                                web.loadUrl("javascript:setImageWithPathA('" + baseBean.getData().get(i) + ",form0')");
                                break;
                            case 1:
                                web.loadUrl("javascript:setImageWithPathA('" + baseBean.getData().get(i) + ",form2')");
                                break;
                            case 2:
                                web.loadUrl("javascript:setImageWithPathA('" + baseBean.getData().get(i) + ",grrenzheng2')");
                                break;
                            case 3:
                                web.loadUrl("javascript:setImageWithPathA('" + baseBean.getData().get(i) + ",gsrenzheng')");
                                break;
                        }
                        if (i == 0) {
                            path = baseBean.getData().get(0);

                        } else {
                            path = path + "@#@" + baseBean.getData().get(i);
                        }
                    }
                }
            }
        };
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getCompressPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            if (i == 0) {
                params.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
            } else {
                params.put("file" + i + "\"; filename=\"" + file.getName() + "\"", fileBody);
            }

            Log.i("----------params1", file.getAbsolutePath());
        }
        Log.i("----------params", params.toString());
//        HttpMethods.getInstance().uploadFile(new ProgressSubscriber<>(onNextListener, this, true), params);
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();

        }
    }

    private void uploadVideo() {
        SubscriberOnNextListener<BaseBean<List<String>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<String>>>() {
            @Override
            public void onNext(BaseBean<List<String>> baseBean) {
                if (200 == baseBean.getCode()) {
                    Log.i("------------", baseBean.toString());
                    path = baseBean.getData().get(0);
                    if (rxDialogLoading.isShowing()) {
                        rxDialogLoading.show();
                    }
                    web.loadUrl("javascript:setImageWithPathA('" + path + ",video')");

                }
            }
        };
        Map<String, RequestBody> params = new HashMap<>();
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("video/mp4"), file);
        String s = "{\"type\":\"" + 1 + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), s);
        params.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
        params.put("type", body);
        Log.i("----------file", file.getAbsolutePath());
        Log.i("----------params", params.toString());
//        HttpMethods.getInstance().uploadFile(new ProgressSubscriber<>(onNextListener, this, true), params);
    }

    //定义接口，提供给JS调用
    interface Contact {
        @JavascriptInterface
        void callAndroid(String YYWh5, String file);
    }


    static class MyHandler extends Handler {
        //注意下面的“PopupActivity”类是MyHandler类所在的外部类，即所在的activity
        WeakReference<TcWebActivity> mActivity;

        MyHandler(TcWebActivity activity) {
            mActivity = new WeakReference<TcWebActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TcWebActivity theActivity = mActivity.get();
            switch (msg.what) {
                case 0x10:
                    theActivity.rxDialogLoading.show();
                    break;
            }
        }
    }
}
