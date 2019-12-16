package com.shanchuang.ssf.mail.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderItemBean;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.view.ImageViewPlus;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/5/14
 */
public class ReleaseEvaAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    Activity activity;
    private EvaImageAdapter.onAddPicClickListener onAddPicClickListener = new EvaImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int code, List<LocalMedia> localMediaList) {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                    //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(3)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .selectionMedia(localMediaList)
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
//                    .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
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
                    .forResult(code);//结果回调onActivityResult code
        }
    };

    public ReleaseEvaAdapter(int layoutResId, @Nullable List<OrderItemBean> data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_title,item.getTitle());
        if (helper.getView(R.id.rec_eva_img).getTag() != null && helper.getView(R.id.rec_eva_img).getTag() instanceof TextWatcher) {
            ((EditText) helper.getView(R.id.rec_eva_img)).removeTextChangedListener((TextWatcher) helper.getView(R.id.rec_eva_img).getTag());
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        ((RecyclerView) helper.getView(R.id.rec_eva_img)).setLayoutManager(manager);
        EvaImageAdapter adapter = new EvaImageAdapter(mContext, onAddPicClickListener, helper.getAdapterPosition(), item.getLocalMedia());
        adapter.setList(item.getLocalMedia());
        adapter.setSelectMax(3);

        ((RecyclerView) helper.getView(R.id.rec_eva_img)).setAdapter(adapter);

        adapter.setOnItemClickListener(new EvaImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (item.getLocalMedia().size() > 0) {
                    LocalMedia media = item.getLocalMedia().get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(activity).themeStyle(R.style.picture_default_style).openExternalPreview(position, item.getLocalMedia());
                            break;
                    }
                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setContent(s.toString());
            }
        };

        ((EditText) helper.getView(R.id.et_option)).addTextChangedListener(textWatcher);
        helper.getView(R.id.et_option).setTag(textWatcher);
    }
}
