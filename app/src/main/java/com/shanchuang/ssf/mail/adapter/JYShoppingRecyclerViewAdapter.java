package com.shanchuang.ssf.mail.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.shanchuang.ssf.utils.SwipeItemLayout;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JYShoppingRecyclerViewAdapter extends BaseQuickAdapter<JYShopCarBean.CartBean, BaseViewHolder> {

    OnOneClick onOneClick;
    private Context context;
    private CheckInterface checkInterface;
    private ChangeNumberInterface changeNumberInterface;

    public JYShoppingRecyclerViewAdapter(Context context, @Nullable List<JYShopCarBean.CartBean> data) {
        super(R.layout.recyclerview_shopping_item, data);
        this.context = context;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setChangeNumberInterface(ChangeNumberInterface changeNumberInterface) {
        this.changeNumberInterface = changeNumberInterface;
    }

    public void setOnOneClick(OnOneClick onOneClick) {
        this.onOneClick = onOneClick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final JYShopCarBean.CartBean item) {
        helper.setText(R.id.tv_shopping_item_name_item, item.getMer().getTitle());
        helper.addOnClickListener(R.id.tv_shopping_item_name_item);
        final CheckBox groupCheckBox = helper.getView(R.id.cb_shopping_item_check_item);
        helper.addOnClickListener(R.id.cb_shopping_item_check_item);
        groupCheckBox.setTag(helper.getAdapterPosition());
        if (helper.getAdapterPosition() == (int) groupCheckBox.getTag()) {
            groupCheckBox.setChecked(item.isCheck());
        }
        RecyclerView recyclerView = helper.getView(R.id.recycler_shopping_item_view);
        final JYShoppingItemRecyclerViewAdapter shoppingItemRecyclerViewAdapter =
                new JYShoppingItemRecyclerViewAdapter(context, item.getGoods());
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(shoppingItemRecyclerViewAdapter);

        /*
        父布局的checkbox的点击事件,并改变数据源状态，且改变子布局的数据源状态
        接口回调位置也不能改变，涉及到计算金额顺序
         */
        groupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                item.setCheck(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    for (JYShopItemBean shoppingItem : item.getGoods()) {
                        shoppingItem.setCheck(true);
                    }
                } else {
                    for (JYShopItemBean shoppingItem : item.getGoods()) {
                        shoppingItem.setCheck(false);
                    }
                }
                shoppingItemRecyclerViewAdapter.notifyDataSetChanged();
                if (checkInterface != null) {
                    checkInterface.checkGroup(helper.getAdapterPosition(), checkBox.isChecked());
                }
            }
        });

        //子布局里面checkBox的点击选中与否,改变数据源的状态并且回调到外边
        shoppingItemRecyclerViewAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i;
                switch (view.getId()) {
                    case R.id.cb_shopping_item_check_item_item:
                        CheckBox checkBox = (CheckBox) view;
                        item.getGoods().get(position).setCheck(checkBox.isChecked());
                        if (checkInterface != null) {
                            checkInterface.checkChild(helper.getAdapterPosition(), groupCheckBox, position, checkBox.isChecked());
                        }
                        break;
                    case R.id.delete:
                        deleteGoods(shoppingItemRecyclerViewAdapter.getData().get(position).getCart_id(), shoppingItemRecyclerViewAdapter, position, helper);
                        break;
                    case R.id.iv_shopping_item_delete_item_item:
                        if (changeNumberInterface != null) {
                            changeNumberInterface.delete(helper.getAdapterPosition(), position);
                        }
                        shoppingItemRecyclerViewAdapter.notifyDataSetChanged();
                        break;
                    case R.id.iv_shopping_item_add_item_item:
                        if (changeNumberInterface != null) {
                            changeNumberInterface.add(helper.getAdapterPosition(), position);
                        }
                        shoppingItemRecyclerViewAdapter.notifyDataSetChanged();
                        break;

                    case R.id.llt_shopping_goods_item_all:
                        if (onOneClick != null) {
                            onOneClick.oneClick(helper.getAdapterPosition(), position);
                        }
                        break;
                }
            }
        });
    }

    private void deleteGoods(String id, JYShoppingItemRecyclerViewAdapter shopCarAdapter, int position, BaseViewHolder helper) {
        SubscriberOnNextListener<BaseBean<List<JYShopCarBean>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<JYShopCarBean>>>() {
            @Override
            public void onNext(BaseBean<List<JYShopCarBean>> baseBean) {
                try {
                    if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                        Log.i("------------", baseBean.toString());
                        if (shopCarAdapter.getData().size() > 1) {
                            shopCarAdapter.getData().remove(position);
                            shopCarAdapter.notifyDataSetChanged();
                        } else {
                            getData().remove(helper.getAdapterPosition());
                            notifyDataSetChanged();
                        }
                        if(getData().isEmpty()){
                            Intent intent =new Intent("Calculation");
                            intent.putExtra("type",3);
                           mContext.sendBroadcast(intent);
                        }
                        RxToast.normal("删除成功");

                    } else {
                        RxToast.normal(baseBean.getMsg());

                    }
                } catch (Exception e) {

                }


            }
        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(mContext));
        map.put("cart_id", id);
        HttpMethods.getInstance().cart_del(new ProgressSubscriber<>(onNextListener, mContext, false), map);
//        HttpMethods.getInstance().delcart(new ProgressSubscriber<>(onNextListener, mContext, false), SharedHelper.readId(mContext), id);

    }

    public interface CheckInterface {
        void checkGroup(int groupPosition, boolean isChecked);

        void checkChild(int groupPosition, CheckBox checkBox, int childPosition, boolean isChecked);
    }


    public interface ChangeNumberInterface {
        void add(int groupPosition, int childPosition);

        void delete(int groupPosition, int childPosition);

    }

    public interface OnOneClick {
        void oneClick(int outpos, int inpos);
    }
}
