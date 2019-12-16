package com.shanchuang.ssf.mail.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.JYShopCarBean;
import com.shanchuang.ssf.bean.JYShopItemBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.shanchuang.ssf.view.adderView;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JYShoppingItemRecyclerViewAdapter extends BaseQuickAdapter<JYShopItemBean, BaseViewHolder> {

    private Context context;

    public JYShoppingItemRecyclerViewAdapter(Context context, @Nullable List<JYShopItemBean> data) {
        super(R.layout.recyclerview_shopping_item_item, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final JYShopItemBean item) {
        helper.setText(R.id.tv_shopping_item_title_item_item, item.getTitle());
        helper.setText(R.id.tv_shopping_item_price_item_item, "¥ " + String.format("%.2f", item.getPrice()));
        helper.setText(R.id.iv_shopping_item_add_item_item, item.getJianjie());
//        helper.setText(R.id.tv_shopping_item_count_item_item, "x "+item.getAmount());
        Glide.with(context)
                .load(item.getImage())
                .into((ImageViewPlus) helper.getView(R.id.iv_shopping_item_img_item_item));
        helper.addOnClickListener(R.id.iv_shopping_item_delete_item_item);
        helper.addOnClickListener(R.id.delete);
        helper.addOnClickListener(R.id.llt_shopping_goods_item_all);
        helper.addOnClickListener(R.id.iv_shopping_item_add_item_item);
        CheckBox itemCheckBox = helper.getView(R.id.cb_shopping_item_check_item_item);
        helper.addOnClickListener(R.id.cb_shopping_item_check_item_item);
        itemCheckBox.setTag(helper.getAdapterPosition());
        if (helper.getAdapterPosition() == (int) itemCheckBox.getTag()) {
            itemCheckBox.setChecked(item.isCheck());
        }
        adderView adderView = helper.getView(R.id.addr_view);
        adderView.setValue(item.getAmount());
        adderView.setOnValueChangeListene(new adderView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value, View btn_reduce) {
                int type = 0;
                if(item.getAmount()>value){
                    type=-1;
                }else if(item.getAmount()<value){
                    type=1;
                }
                item.setAmount(value);
                numRefresh(type, item);
                Intent intent = new Intent("Calculation");
                mContext.sendBroadcast(intent);
            }
        });
    }

    private void numRefresh(int type, JYShopItemBean item) {
        SubscriberOnNextListener<BaseBean<List<JYShopCarBean>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<JYShopCarBean>>>() {
            @Override
            public void onNext(BaseBean<List<JYShopCarBean>> baseBean) {
                try {
                    if (HttpMethods.SUCCESS_CODE == baseBean.getCode() ) {
                        Log.i("------------", baseBean.toString());

                    } else {
                        RxToast.normal(baseBean.getMsg());

                    }
                } catch (Exception e) {

                }


            }
        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(mContext));
        map.put("cart_id", item.getCart_id());
        map.put("type", type);
        HttpMethods.getInstance().cart_amount(new ProgressSubscriber<>(onNextListener, mContext, false), map);
    }
}
