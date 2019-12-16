package com.shanchuang.ssf.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/27
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.web)
    WebView web;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us_layout;
    }
    private Dialog mShareDialog;
    @Override
    protected void initView() {
        web.loadUrl(HttpMethods.BASE_URL+"k12/article/about");
        initShareDialog();
    }
    private void initShareDialog() {
            mShareDialog = DialogUtil.getInstance().showShareDialog(this);
            DialogUtil.getInstance().setOnShareClickListener(new DialogUtil.OnShareClickListener() {
                @Override
                public void qqOnClick(View v) {
                    mShareDialog.dismiss();
                    DialogUtil.ShareWeb(R.mipmap.logo, SHARE_MEDIA.QQ, AboutUsActivity.this,
                            HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.BASE_URL + "k12/article/fenxiang");
                }

                @Override
                public void circleOnClick(View v) {

                }

                @Override
                public void qZoneOnClick(View v) {

                }

                @Override
                public void weixinOnClick(View v) {
                    mShareDialog.dismiss();
                    DialogUtil.ShareWeb(R.mipmap.logo, SHARE_MEDIA.WEIXIN, AboutUsActivity.this,
                            HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.BASE_URL + "k12/article/fenxiang");
                }
            });
    }
    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_return, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_share:
                mShareDialog.show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
