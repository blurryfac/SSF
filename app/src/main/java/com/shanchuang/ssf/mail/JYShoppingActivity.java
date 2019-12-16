package com.shanchuang.ssf.mail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.ShopMsgActivity;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.JYShopCarBean;
import com.shanchuang.ssf.bean.JYShopItemBean;
import com.shanchuang.ssf.mail.adapter.JYShoppingRecyclerViewAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 */
public class JYShoppingActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener, JYShoppingRecyclerViewAdapter.CheckInterface, JYShoppingRecyclerViewAdapter.ChangeNumberInterface {


    int number;
    private RecyclerView mShoppingRecyclerView;
    private JYShoppingRecyclerViewAdapter mShoppingRecyclerViewAdapter;
    private List<JYShopCarBean.CartBean> mShoppingList = new ArrayList<>();
    private TextView mTvTotal, mTvPay, mTvCollect, mTvDelete;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getIntExtra("type",0)==3){
                tv_none.setVisibility(View.VISIBLE);
            }else {
                double totalPrice = 0;
                for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
                    for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                        if (shoppingItem.isCheck()) {
                            totalPrice = totalPrice + ((shoppingItem.getPrice())
                                    * (shoppingItem.getAmount()));
                        }
                    }
                }
                mTvTotal.setText(String.format("%.2f", totalPrice));
            }


        }
    };
    private Double total = 0.00;
    private CheckBox mAllCheckBox;
    private LinearLayout mDeleteLinearLayout;
    private boolean flag;
    private LinkedHashSet<String> mShopping = new LinkedHashSet<>();
    private List<JYShopItemBean> mShoppingItem = new ArrayList<>();
    private SmartRefreshLayout mSrl;
    private ImageView mBackImg;
    private TextView toolbarTitle;
    private TextView tv_none;
    private JYShoppingActivity mContext;
    private boolean isShowDialog = true;

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
//        if (isChecked) {
//            for (ShoppingBean.ShoppingItem shoppingItem : mShoppingList.get(groupPosition).getShoppingItems()) {
//                total = total + ((shoppingItem.getPrice()) * shoppingItem.getNumber());
//            }
//        } else {
//            for (ShoppingBean.ShoppingItem shoppingItem : mShoppingList.get(groupPosition).getShoppingItems()) {
//                total = total - ((shoppingItem.getPrice()) * shoppingItem.getNumber());
//            }
//        }
//        mTvTotal.setText(String.format("%.2f", total));
        double totalPrice = 0;
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                if (shoppingItem.isCheck()) {
                    totalPrice = totalPrice + ((shoppingItem.getPrice())
                            * (shoppingItem.getAmount()));
                }
            }
        }
        mTvTotal.setText(String.format("%.2f", totalPrice));

        if (isGroupCheckAll()) {
            mAllCheckBox.setChecked(true);
        } else {
            mAllCheckBox.setChecked(false);
        }
//        if (isCheckAll()) {
//            mTvPay.setBackgroundColor(getResources().getColor(R.color.colorRed));
//        } else {
//            mTvPay.setBackgroundColor(getResources().getColor(R.color.colorGray));
//        }
    }

    @Override
    public void checkChild(int groupPosition, CheckBox checkBox, int childPosition, boolean isChecked) {
//        if (isChecked) {
//            total = total + ((mShoppingList.get(groupPosition).getShoppingItems().get(childPosition).getPrice())
//                    * (mShoppingList.get(groupPosition).getShoppingItems().get(childPosition).getNumber()));
//        } else {
//            total = total - ((mShoppingList.get(groupPosition).getShoppingItems().get(childPosition).getPrice())
//                    * (mShoppingList.get(groupPosition).getShoppingItems().get(childPosition).getNumber()));
//        }
//        mTvTotal.setText(String.format("%.2f", total));

        double totalPrice = 0;
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                if (shoppingItem.isCheck()) {
                    totalPrice = totalPrice + ((shoppingItem.getPrice())
                            * (shoppingItem.getAmount()));
                }
            }
        }
        mTvTotal.setText(String.format("%.2f", totalPrice));
        if (isCheckAll(groupPosition)) {
            checkBox.setChecked(true);//子元素全选,父元素设置选中
            mShoppingList.get(groupPosition).setCheck(true);
        } else {
            checkBox.setChecked(false);//反选
            mShoppingList.get(groupPosition).setCheck(false);
        }

        if (isGroupCheckAll()) {
            mAllCheckBox.setChecked(true);
        } else {
            mAllCheckBox.setChecked(false);
        }

        if (isCheckAll()) {
//            mTvPay.setBackgroundColor(getResources().getColor(R.color.colorRed));
        } else {
//            mTvPay.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }

    /**
     * @return 判断组元素里面的子元素是否全选
     */
    private boolean isCheckAll(int position) {
        for (JYShopItemBean shoppingItem : mShoppingList.get(position).getGoods()) {
            if (!shoppingItem.isCheck()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return 判断元素是否选中
     */
    private boolean isCheckAll() {
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            for (JYShopItemBean shoppingItem : shoppingBean.getGoods())
                if (shoppingItem.isCheck()) {
                    return true;
                }
        }
        return false;
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isGroupCheckAll() {
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            if (!shoppingBean.isCheck()) {
                return false;
            }
        }
        return true;
    }

    //购物车添加数量
    @Override
    public void add(int groupPosition, int childPosition) {
        double totalPrice = 0;
        number = mShoppingList.get(groupPosition).getGoods().get(childPosition).getAmount();
//        if (number >= mShoppingList.get(groupPosition).getGoods().get(childPosition).getCount()) {
////            MainApplication.getInstance().showToast("库存不够啦");
//        } else {
//            number++;
//            mShoppingList.get(groupPosition).getGoods().get(childPosition).setNum(number);
//        }
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                if (shoppingItem.isCheck()) {
                    totalPrice = totalPrice + ((shoppingItem.getPrice())
                            * (shoppingItem.getAmount()));
                }
            }
        }
        mTvTotal.setText(String.format("%.2f", totalPrice));
    }

    //购物车减少数量
    @Override
    public void delete(int groupPosition, int childPosition) {
        double totalPrice = 0;
        number = mShoppingList.get(groupPosition).getGoods().get(childPosition).getAmount();
        if (number == 1) {
//            MainApplication.getInstance().showToast("不能再少啦");
        } else {
            number--;
            mShoppingList.get(groupPosition).getGoods().get(childPosition).setAmount(number);
        }
        for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
            for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                if (shoppingItem.isCheck()) {
                    totalPrice = totalPrice + (shoppingItem.getPrice())
                            * (shoppingItem.getAmount());
                }
            }
        }
        mTvTotal.setText(String.format("%.2f", totalPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                if (flag) {
                    menu.setText("编辑");
                    mDeleteLinearLayout.setVisibility(View.GONE);
                    flag = false;
                } else {
                    menu.setText("完成");
                    mDeleteLinearLayout.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.tv_shopping_collect:

                break;


            case R.id.tv_shopping_pay://pay

                if (isCheckAll()) {
                    String idstr ="";
                    for (JYShopCarBean.CartBean bean : mShoppingList) {
                        List<JYShopItemBean> list = bean.getGoods();
                        for (JYShopItemBean bean0 : list) {
                            if (bean0.isCheck()) {
                                if (idstr.isEmpty()) {
                                    idstr = bean0.getCart_id();
                                } else {
                                    idstr = idstr + "," + bean0.getCart_id();
                                }
                            }
                        }
                    }

                    Intent intent = new Intent(this, ConfirmOrderActivity.class);
                    intent.putExtra("pay_type",2);
                    intent.putExtra("id",idstr);
                    startActivity(intent);
                } else {
                    RxToast.normal("没有选中商品");
                }

                break;
            case R.id.tv_shopping_delete:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jy_activity_shopping;
    }

    @Override
    protected void initView() {
        toolbarTitle = findViewById(R.id.toolbar_title);
        tv_none = findViewById(R.id.tv_none);
        tv_none.setOnClickListener(this);
        toolbarTitle.setText("购物车");
        mSrl = findViewById(R.id.srl);
        mSrl.setRefreshHeader(new ClassicsHeader(this));
        mShoppingRecyclerView = findViewById(R.id.recycler_shopping_view);
        mAllCheckBox = findViewById(R.id.check_shopping_box);
        mTvTotal = findViewById(R.id.tv_shopping_total);
        mDeleteLinearLayout = findViewById(R.id.ll_shopping_delete);
        mTvPay = findViewById(R.id.tv_shopping_pay);
        mTvCollect = findViewById(R.id.tv_shopping_collect);
        mTvDelete = findViewById(R.id.tv_shopping_delete);
        IntentFilter intentFilter = new IntentFilter("Calculation");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void initData() {
        mShoppingRecyclerViewAdapter = new JYShoppingRecyclerViewAdapter(this, mShoppingList);
        mShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mShoppingRecyclerView.setAdapter(mShoppingRecyclerViewAdapter);
        mShoppingRecyclerViewAdapter.setCheckInterface(this);
        mShoppingRecyclerViewAdapter.setOnItemChildClickListener(this);
        mShoppingRecyclerViewAdapter.setChangeNumberInterface(this);
        mShoppingRecyclerViewAdapter.setOnOneClick(new JYShoppingRecyclerViewAdapter.OnOneClick() {
            @Override
            public void oneClick(int outpos, int inpos) {
                Bundle bundle=new Bundle();
                bundle.putString("id", mShoppingList.get(outpos).getGoods().get(inpos).getId());
                RxActivityTool.skipActivity(JYShoppingActivity.this, ShopDetailActivity.class,bundle);

            }
        });

        mSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                mShoppingList.clear();
                mShoppingRecyclerViewAdapter.notifyDataSetChanged();
                getShop();
            }
        });
        initEvent();
        getShop();
    }

    private void getShop() {

        SubscriberOnNextListener<BaseBean<JYShopCarBean>> onNextListener = baseBean -> {
            try {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()&&!baseBean.getData().getCart().isEmpty()) {
                    Log.i("------------", baseBean.toString());
                    tv_none.setVisibility(View.INVISIBLE);
                    mShoppingList.addAll(baseBean.getData().getCart());
                    mShoppingRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    tv_none.setVisibility(View.VISIBLE);
                    RxToast.normal(baseBean.getMsg());

                }
            } catch (Exception e) {

            }


        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().cart_lists(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }

    protected void initEvent() {
        mAllCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAllCheckBox.isChecked()) {
                    total = 0.00;
                    for (int i = 0; i < mShoppingList.size(); i++) {
                        mShoppingList.get(i).setCheck(true);
                        for (int j = 0; j < mShoppingList.get(i).getGoods().size(); j++) {
                            mShoppingList.get(i).getGoods().get(j).setCheck(true);
                            total = total + mShoppingList.get(i).getGoods().get(j).getPrice();
                        }
                    }
//                    mTvPay.setBackgroundColor(getResources().getColor(R.color.colorRed));

                } else {
                    total = 0.00;
                    for (int i = 0; i < mShoppingList.size(); i++) {
                        mShoppingList.get(i).setCheck(false);
                        for (int j = 0; j < mShoppingList.get(i).getGoods().size(); j++) {
                            mShoppingList.get(i).getGoods().get(j).setCheck(false);
                        }
                    }
//                    mTvPay.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
                double totalPrice = 0;
                for (JYShopCarBean.CartBean shoppingBean : mShoppingList) {
                    for (JYShopItemBean shoppingItem : shoppingBean.getGoods()) {
                        if (shoppingItem.isCheck()) {
                            totalPrice = totalPrice + (shoppingItem.getPrice())
                                    * (shoppingItem.getAmount());
                        }
                    }
                }
                mTvTotal.setText(String.format("%.2f", totalPrice));

//                mTvTotal.setText(String.format("%.2f", total));
                mShoppingRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        mTvPay.setOnClickListener(this);
        mTvCollect.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_shopping_item_name_item:
                //商家详情
                Bundle bundle=new Bundle();
                bundle.putString("id", mShoppingList.get(position).getMer().getId());
                RxActivityTool.skipActivity(JYShoppingActivity.this, ShopMsgActivity.class,bundle);
                break;
        }
    }


}
