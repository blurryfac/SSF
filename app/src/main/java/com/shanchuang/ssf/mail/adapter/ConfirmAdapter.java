package com.shanchuang.ssf.mail.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ShopCarBean;
import com.vondear.rxtool.RxDataTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 佚名
 * @time 2018/6/15
 */
public class ConfirmAdapter extends BaseQuickAdapter<ShopCarBean.OrderBean, BaseViewHolder> {


    public ConfirmAdapter(int layoutResId, @Nullable List<ShopCarBean.OrderBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ShopCarBean.OrderBean item) {
        helper.addOnClickListener(R.id.tv_coupon);
        helper.setText(R.id.tv_shop_name, item.getMer().getTitle());
        helper.setText(R.id.tv_goods_price, "¥ "+item.getXiaoji());
        if(RxDataTool.isEmpty(item.getChoicePrice())){
            helper.setText(R.id.tv_goods_coupon, "¥ 0");
            helper.setText(R.id.tv_coupon, "选择优惠券");
        }else {
            helper.setText(R.id.tv_coupon, "已选择");
            helper.setText(R.id.tv_goods_coupon, "¥ "+item.getChoicePrice());
        }

//        if(item.getCoupon()==null){
//            helper.setText(R.id.tv_coupon, "请选择优惠券");
//        }else {
//            helper.setText(R.id.tv_coupon, item.getCoupon());
//        }

//        int shopNum = 0;
//        BigDecimal mBDShopPrice = new BigDecimal(0);
//        for (int i = 0; i < item.getGoods().size(); i++) {
//            shopNum = shopNum + item.getGoods().get(i).getAmount();
//            BigDecimal num = new BigDecimal(item.getGoods().get(i).getAmount());
//            BigDecimal Price = new BigDecimal(item.getGoods().get(i).getPrice());
//            BigDecimal single = num.multiply(Price).setScale(2, BigDecimal.ROUND_HALF_UP);
//            mBDShopPrice = mBDShopPrice.add(single).setScale(2, BigDecimal.ROUND_HALF_UP);

//            item.setShop_price(mBDShopPrice.toString());
//            item.setShop_num(String.valueOf(shopNum));
//        }
//        Log.d(TAG, "convert: 共" + shopNum+"件,¥"+mBDShopPrice.toString());
//        RxTextTool.getBuilder("")
//                .append("小计").setForegroundColor(mContext.getResources().getColor(R.color.color_black))
//                .append(" ¥"+mBDShopPrice.toString()).setForegroundColor(mContext.getResources().getColor(R.color.color_yellow))
//                .into(helper.getView(R.id.tv_shop_price));
        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        ((RecyclerView) helper.getView(R.id.rec_shop)).setLayoutManager(linear);
        ConfirmItemAdapter confirmItemAdapter = new ConfirmItemAdapter(R.layout.item_shop_car4, item.getGoods());
        confirmItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        ((RecyclerView) helper.getView(R.id.rec_shop)).setAdapter(confirmItemAdapter);

    }



}
