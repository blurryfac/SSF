package com.shanchuang.ssf.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.FileBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.vondear.rxtool.RxFileTool;
import com.vondear.rxtool.view.RxToast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class AddTeamActivity extends BaseActivity {
    private static final String TAG = "AddTeamActivity";
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_add_file)
    TextView tvAddFile;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private String mHttpPath = "";//网络路径

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_team_layout;
    }

    @Override
    protected void initView() {
        title.setText("加入团队");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path = "";
            Uri uri = data.getData();
            path = RxFileTool.getPathFromUri(this, uri);
            httpUploadFile(path);
        }
    }

    /**
     * 上传文件到服务器
     *
     * @param path 本地路径
     */
    private void httpUploadFile(String path) {
        SubscriberOnNextListener<BaseBean<FileBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                Log.i("------------", baseBean.toString());
                mHttpPath = baseBean.getData().getUrl();
                tvAddFile.setText("文件已上传");
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, RequestBody> params = new HashMap<>();
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        params.put("jianli\"; filename=\"" + file.getName() + "\"", fileBody);
        HttpMethods.getInstance().jlfile(new ProgressSubscriber<>(onNextListener, this, true), params);
    }




    @OnClick({R.id.tv_add_file, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_save:
                if (isNull(etMobile)) {
                    RxToast.normal("请输入手机号");
                    return;
                }
                if (isNull(etName)) {
                    RxToast.normal("请输入姓名");
                    return;
                }
                if (isNull(mHttpPath)) {
                    RxToast.normal("请选择简历");
                    return;
                }
                httpAddTeam();
                break;
        }
    }

    private void httpAddTeam() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal(baseBean.getMsg());
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> params = new HashMap<>();
        params.put("name", getString(etName));
        params.put("mobile", getString(etMobile));
        params.put("jl_file", mHttpPath);
        HttpMethods.getInstance().jiaru(new ProgressSubscriber<>(onNextListener, this, true), params);
    }
}
