package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class JYShopItemBean {
    /**
     * id : 2
     * title : 中性笔GP-1008学生用水笔签字0.5笔芯蓝黑批发黑色红笔水性碳素圆珠笔
     * image : http://test.k12.com/uploads/20191017/bbea5e835e462f7952b9d0370d439f3f.jpg
     * price : 22.5
     * amount : 2
     * cart_id : 3
     */
    private boolean  isCheck;
    private String id;
    private String jianjie;
    private String title;
    private String image;
    private Double price;
    private int amount;
    private String cart_id;

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
}
