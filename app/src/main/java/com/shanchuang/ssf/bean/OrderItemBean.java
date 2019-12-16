package com.shanchuang.ssf.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/13
 */
public class OrderItemBean implements Parcelable {
    /**
     * gid : 1
     * amount : 1
     * price : 129
     * title : 2019高端独角兽学生礼物男女儿童文具套装
     * image : http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg
     */

    private String gid;
    private int amount;
    private String price;
    private String content;
    private String title;
    private String image;
    private List<LocalMedia> localMedia;

    protected OrderItemBean(Parcel in) {
        gid = in.readString();
        amount = in.readInt();
        price = in.readString();
        content = in.readString();
        title = in.readString();
        image = in.readString();
        localMedia = in.createTypedArrayList(LocalMedia.CREATOR);
    }

    public static final Creator<OrderItemBean> CREATOR = new Creator<OrderItemBean>() {
        @Override
        public OrderItemBean createFromParcel(Parcel in) {
            return new OrderItemBean(in);
        }

        @Override
        public OrderItemBean[] newArray(int size) {
            return new OrderItemBean[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public List<LocalMedia> getLocalMedia() {
        return localMedia;
    }

    public void setLocalMedia(List<LocalMedia> localMedia) {
        this.localMedia = localMedia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gid);
        dest.writeInt(amount);
        dest.writeString(price);
        dest.writeString(content);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeTypedList(localMedia);
    }
}
