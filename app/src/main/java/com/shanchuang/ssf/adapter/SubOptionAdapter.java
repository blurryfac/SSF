package com.shanchuang.ssf.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.SubOptionBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/17
 */
public class SubOptionAdapter extends BaseQuickAdapter<SubOptionBean, BaseViewHolder> {
    public SubOptionAdapter(int layoutResId, @Nullable List<SubOptionBean> data) {
        super(layoutResId, data);
    }
    public OnCheckClickListener mOnCheckClickListener;
    public interface OnCheckClickListener {
        void onItemCheck(CompoundButton buttonView, boolean isChecked,int postion);
    }
    public void setOnCheckClickListener( OnCheckClickListener listener) {
        mOnCheckClickListener = listener;
    }
    private String option[]={"A. ","B. ","C. ","D. "};//选项

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final SubOptionBean appInfoBean = mData.get(position);
            if (appInfoBean != null) {

                RadioButton cb_option=holder.getView(R.id.cb_option);
                cb_option.setChecked(mData.get(position).isCheck());
            }
        }
    }
    /**
     * 暴露用于修改进度值的方法
     *
     * @param position
     */
    public void setChecbox(boolean check, int position) {
        mData.get(position).setCheck(check);

        notifyItemChanged(position, 1);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubOptionBean item) {

        RadioButton cb_option=helper.getView(R.id.cb_option);
        cb_option.setChecked(item.isCheck());
        helper.setText(R.id.tv_option_text,option[helper.getAdapterPosition()]);
        if(item.getType()==1){
            helper.setText(R.id.tv_option_item,item.getName());
            helper.getView(R.id.iv_option_item).setVisibility(View.GONE);
            helper.getView(R.id.tv_option_item).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_option_item).setVisibility(View.GONE);
            helper.getView(R.id.iv_option_item).setVisibility(View.VISIBLE);
            Glide.with(mContext).asBitmap().load(item.getName())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            int width = bitmap.getWidth()*2;
                            int height = bitmap.getHeight();
                            voidloadIntoUseFitWidth(width, mContext, item.getName(), (ImageView) helper.getView(R.id.iv_option_item));
//
                        }
                    });
        }
        cb_option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                mOnCheckClickListener.onItemCheck(buttonView,isChecked,helper.getAdapterPosition());
            }
        });
    }
    @SuppressLint("CheckResult")
    private void voidloadIntoUseFitWidth(int width, Context context, final String imageUrl, final ImageView imageView) {
        //我这里是先获取屏幕的宽高，然后把屏幕的宽设为imageView的宽。

        WindowManager wm = (WindowManager) context

                .getSystemService(Context.WINDOW_SERVICE);

        int pwidth = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if(width>=pwidth){
            params.width = pwidth-280;
        }else {
            params.width = width;
        }
        imageView.setLayoutParams(params);
        //glide是在listener()方法中传入一个RequestListener来设置当图片资源准备好了以后自定义的操作的。
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(imageUrl).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView == null) {
                    return false;
                }
                //首先设置imageView的ScaleType属性为ScaleType.FIT_XY，让图片不按比例缩放，把图片塞满整个View。
                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
//                  得到当前imageView的宽度（我设置的是屏幕宽度），获取到imageView与图片宽的比例，然后通过这个比例去设置imageView的高
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                params.height = vh ;
                imageView.setLayoutParams(params);
                return false;
            }
        }).into(imageView);
    }
}
