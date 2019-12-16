package com.shanchuang.ssf.bean;

import com.google.gson.annotations.SerializedName;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/23
 */
public class GroupBean {


    private int next;
    private List<ChengzhangBean> chengzhang;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public List<ChengzhangBean> getChengzhang() {
        return chengzhang;
    }

    public void setChengzhang(List<ChengzhangBean> chengzhang) {
        this.chengzhang = chengzhang;
    }

    public static class ChengzhangBean {
        /**
         * content : 方式发生的鼎折覆餗水电费
         * createtime : 1546526598
         * images : null
         */

        @SerializedName("content")
        private String contentX;
        private int createtime;
        private List<String> images;
        private List<LocalMedia> imgs;


        public List<LocalMedia> getImgs() {
            return imgs;
        }

        public void setImgs(List<LocalMedia> imgs) {
            this.imgs = imgs;
        }

        public String getContentX() {
            return contentX;
        }

        public void setContentX(String contentX) {
            this.contentX = contentX;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
