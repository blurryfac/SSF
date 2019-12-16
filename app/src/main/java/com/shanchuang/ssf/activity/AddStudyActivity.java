package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.GridImageAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.UploadBean;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
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
 * @time 2019/10/24
 */
public class AddStudyActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_option)
    EditText etOption;
    @BindView(R.id.rec_imgs)
    RecyclerView recImgs;
    @BindView(R.id.tv_release)
    TextView tvRelease;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_study_layout;
    }

    @Override
    protected void initView() {
        title.setText("发布记录");
        initRec();
    }

    @Override
    protected void initData() {

    }
    List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(AddStudyActivity.this)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                    //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(3)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .selectionMedia(selectList)
                    //.previewVideo()// 是否可预览视频 true or false
                    // .enablePreviewAudio() // 是否可播放音频 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .enableCrop(false)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    // .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                    //  .isGif()// 是否显示gif图片 true or false
                    // .compressSavePath(getPath())//压缩图片保存地址
                    //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    //.cropCompressQuality()// 裁剪压缩质量 默认90 int
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                    //  .rotateEnabled() // 裁剪是否可旋转图片 true or false
                    //  .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                    // .videoQuality()// 视频录制质量 0 or 1 int
                    //  .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                    //   .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                    // .recordVideoSecond()//视频秒数录制 默认60s int
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };
    private GridImageAdapter adapter;
    private void initRec() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recImgs.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(3);
        recImgs.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddStudyActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);

                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddStudyActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddStudyActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                selectList.clear();
                selectList.addAll(PictureSelector.obtainMultipleResult(data));
                if (!selectList.isEmpty()) {
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    uploadImg();//上传图片
                }

                break;

        }

    }
    private String path="";
    private void uploadImg() {
        SubscriberOnNextListener<BaseBean<UploadBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                for(int i=0;i<baseBean.getData().getUrl().size();i++){
                    if(i==0){
                        path=baseBean.getData().getUrl().get(i);
                    }else {
                        path=path+","+baseBean.getData().getUrl().get(i);
                    }
                }
            } else {
                RxToast.normal(baseBean.getMsg());
            }
        };
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getCompressPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                params.put("file[" + i + "]\"; filename=\"" + file.getName() + "\"", fileBody);
            Log.i("----------params1", file.getAbsolutePath());

        }
        params.put("uid", RequestBody.create(null, SharedHelper.readId(this)));
        HttpMethods.getInstance().upload(new ProgressSubscriber<>(onNextListener, this, true), params);
    }

    @OnClick(R.id.tv_release)
    public void onViewClicked() {
        if(isNull(etOption)){
            RxToast.normal("请输入记录说明");
            return;
        }
        httpReleaseRecoard();
    }

    private void httpReleaseRecoard() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                setResult(0x12);
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("content", getString(etOption));
        map.put("images", path);
        HttpMethods.getInstance().add_chengzhang(new ProgressSubscriber<>(onNextListener, this, true), map);
    }
}
