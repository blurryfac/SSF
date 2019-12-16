package com.shanchuang.ssf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luck.picture.lib.permissions.RxPermissions;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.fragment.MainFragment;
import com.shanchuang.ssf.fragment.MessageFragment;
import com.shanchuang.ssf.fragment.PersonCenterFragment;
import com.shanchuang.ssf.fragment.ShopFragment;
import com.shanchuang.ssf.login.LoginAndRegActivity;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.XRadioGroup;
import com.vondear.rxtool.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements XRadioGroup.OnCheckedChangeListener {


    public static AlertDialog mLoginAlert;
    public static AlertDialog.Builder mLoginBuider;
    FragmentTransaction fTransaction;
    @BindView(R.id.ly_content)
     FrameLayout lyContent;
    @BindView(R.id.btn_main)
    RadioButton btnMain;
    @BindView(R.id.btn_video)
    RadioButton btnVideo;
    @BindView(R.id.btn_shop)
    RadioButton btnShop;
    @BindView(R.id.btn_me)
    RadioButton btnMe;
    @BindView(R.id.rg_tab_bar)
    XRadioGroup rgTabBar;
    private FragmentManager fManager;
    private MainFragment fg1;
    private MessageFragment fg2;
    private ShopFragment fg4;
    private PersonCenterFragment fg5;
    //退出时的时间
    private long mExitTime;

    /**
     * 未登录弹窗
     * @param activity activity
     */
    public static void showLoginDialog(Activity activity) {
        mLoginAlert = null;
        mLoginBuider = new AlertDialog.Builder(activity);
        mLoginAlert = mLoginBuider.setIcon(R.mipmap.logo)
                .setTitle(R.string.main_tip)
                .setMessage(R.string.main_no_login)
                .setNegativeButton(R.string.cancer, (dialog, which) -> mLoginAlert.dismiss())
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    mLoginAlert.dismiss();
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setClass(activity, LoginAndRegActivity.class);
                    activity.startActivityForResult(intent, 1);
                })
                .create();             //创建AlertDialog对象
        mLoginAlert.show();                    //显示对话框
    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出到后台
     */
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次进入后台", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
    }
    private int checkid;
    @Override
    public void onCheckedChanged(XRadioGroup group, @IdRes int checkedId) {
        if (SharedHelper.readId(this).isEmpty() && (checkedId == R.id.btn_me)) {
            group.check(checkid);
            showLoginDialog(this);
        } else {
            checkid = checkedId;
            fTransaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(fTransaction);

        switch (checkedId) {
            case R.id.btn_main:
                if (fg1 == null) {
                    fg1 = new MainFragment();

                    fTransaction.add(R.id.ly_content, fg1, "main");
                    fTransaction.hide(fg1);
                    fTransaction.show(fg1);
                } else {
                    fTransaction.show(fg1);
                }
                break;

            case R.id.btn_video:
                if (fg2 == null) {
                    fg2 = new MessageFragment();

                    fTransaction.add(R.id.ly_content, fg2, "video");
                    fTransaction.hide(fg2);
                    fTransaction.show(fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;


            case R.id.btn_shop:
                if (fg4 == null) {
                    fg4 = new ShopFragment();
                    fTransaction.add(R.id.ly_content, fg4, "shop");
                    fTransaction.hide(fg4);
                    fTransaction.show(fg4);
                } else {

                    fTransaction.show(fg4);
                }

                break;
            case R.id.btn_me:
                if (fg5 == null) {
                    fg5 = new PersonCenterFragment();
                    fTransaction.add(R.id.ly_content, fg5, "me");
                    fTransaction.hide(fg5);
                    fTransaction.show(fg5);
                } else {

                    fTransaction.show(fg5);
                }

                break;
        }
        fTransaction.commit();

        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg4 != null) fragmentTransaction.hide(fg4);
        if (fg5 != null) fragmentTransaction.hide(fg5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 当前应用是否处于前台
     *
     * @param context 上下文
     * @return 是否处于前台
     */
    private boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
            return false;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            fg1 = (MainFragment) fManager.findFragmentByTag("main");
            fg2 = (MessageFragment) fManager.findFragmentByTag("video");
            fg4 = (ShopFragment) fManager.findFragmentByTag("shop");
            fg5 = (PersonCenterFragment) fManager.findFragmentByTag("me");
        }
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取APP版本号
     *
     * @param ctx 上下文
     * @return 版本名称
     */
    public static Double getAPPVersionCode(Context ctx) {
        int currentVersionCode = 0;
        String appVersionName = "";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Double.parseDouble(appVersionName);
    }


    private AlertDialog mUpdateAlert;
    private AlertDialog.Builder mUpdateBuidler;

    /**
     * 更新应用弹窗
     *
     * @param activity 上下文
     * @param s 提示文字
     */
    public void showTipDialog(Activity activity, String s) {
        mUpdateAlert = null;
        mUpdateBuidler = new AlertDialog.Builder(activity);
        mUpdateAlert = mUpdateBuidler
                .setTitle(R.string.main_tip)
                .setMessage(R.string.jump_app_store)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, (dialog, which) -> openBrowser(activity, s))
                .create();             //创建AlertDialog对象
        mUpdateAlert.show();                    //显示对话框
    }

    /**
     * 打开浏览器
     *
     * @param context 上下文
     * @param url 链接
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.choice_browser)));
        } else {
            RxToast.normal("链接错误或无浏览器");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void initView() {
        fManager = getSupportFragmentManager();
        rgTabBar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        btnMain.setChecked(true);

        requestPermissions();

    }

    private static final String TAG = "MainActivity";

    /**
     * 请求权限
     */
    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(granted -> {
                    if (granted) {
                        Log.d(TAG, "requestPermissions: 权限请求通过");
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                        Log.d(TAG, "requestPermissions: 权限被拒绝");
                        RxToast.normal(getString(R.string.permisson_denied));
                    }
                });

    }


}
