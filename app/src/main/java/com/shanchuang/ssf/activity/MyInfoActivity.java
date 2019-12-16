package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.ImgBean;
import com.shanchuang.ssf.net.entity.UserBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.PictureChoiceUtil;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2018/11/24
 */
public class MyInfoActivity extends BaseActivity {
    public static final int NICK_CODE = 2;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int INFO_CODE = 1;
    @BindView(R.id.img_tou)
    ImageViewPlus imgTou;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_nickname)
    EditText tvNickname;
    @BindView(R.id.rl_nickname)
    LinearLayout rlNickname;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.rl_mobile)
    LinearLayout rlMobile;

    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info_layout;
    }

    @Override
    protected void initView() {
        title.setText("我的资料");
    }

    @Override
    protected void initData() {
        setUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                if (!PictureSelector.obtainMultipleResult(data).isEmpty()) {
                    selectList = PictureSelector.obtainMultipleResult(data);
                    uploadImg();
                }
                break;
        }
        if (requestCode == INFO_CODE && resultCode == 3) {
            initData();
        }
    }

    private void uploadImg() {
        SubscriberOnNextListener<BaseBean<ImgBean>> onNextListener = new SubscriberOnNextListener<BaseBean<ImgBean>>() {
            @Override
            public void onNext(BaseBean<ImgBean> baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    Glide.with(MyInfoActivity.this).load(baseBean.getData().getImage()).into(imgTou);
                } else {
                    RxToast.normal(baseBean.getMsg());
                }
            }
        };
        Map<String, RequestBody> params = new HashMap<>();
        File file = new File(selectList.get(0).getCompressPath());
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        params.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
        Log.i("----------params1", file.getAbsolutePath());
        params.put("uid", RequestBody.create(null, SharedHelper.readId(this)));
        HttpMethods.getInstance().avatar(new ProgressSubscriber<>(onNextListener, this, true), params);
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        SubscriberOnNextListener<BaseBean<UserBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvNickname.setText(baseBean.getData().getUser().getNickname());
                tvMobile.setText(baseBean.getData().getTel());
                Glide.with(this).load(baseBean.getData().getUser().getAvatar()).into(imgTou);

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().user_index(new ProgressSubscriber<>(onNextListener, this, false), map);
    }

    @OnClick({R.id.img_tou, R.id.btn_save, R.id.rl_mobile})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_tou:
                PictureChoiceUtil.getInstance().choiceTouPic(this, selectList);
                break;
            case R.id.btn_save:
                httpSaveName();
                break;
            case R.id.rl_mobile:
                Bundle bundle = new Bundle();
                bundle.putInt("resultcode", 3);
                bundle.putString("value", getString(tvMobile));
                RxActivityTool.skipActivityForResult(this, ModityMobileActivity.class, bundle, INFO_CODE);
                break;

        }
    }


    private void httpSaveName() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal("修改成功");
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("nickname", tvNickname.getText().toString());
        HttpMethods.getInstance().info(new ProgressSubscriber<>(onNextListener, this, true), map);

    }
}
