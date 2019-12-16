package com.shanchuang.ssf.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultBean implements Parcelable {

    /**
     * score : 0.88424289226532
     * name : 豆瓣绿
     * baike_info : {"baike_url":"http://baike.baidu.com/item/%E8%B1%86%E7%93%A3%E7%BB%BF/528814","image_url":"http://imgsrc.baidu.com/baike/pic/item/203fb80e7bec54e766de0954b3389b504fc26a4d.jpg","description":"豆瓣绿(学名：Peperomia tetraphylla (Forst.f.) Hook.et Arn.)是胡椒科，草胡椒属多年生常绿草本植物。多年生肉质丛生草本，茎匍匐，多分枝，叶片密集，大小近相等，带肉质，有透明腺点，阔椭圆形或近圆形，两端钝或圆，叶脉细弱，通常不明显；叶柄短，穗状花序单生，顶生和腋生，花序轴密被毛；苞片近圆形，有短柄，盾状；花药近椭圆形，花丝短；子房卵形，浆果近卵形，2-4月及9-12月开花。分布于中国台湾、福建、广东、广西、贵州、云南、四川及甘肃南部和西藏南部。美洲、大洋洲、非洲及亚洲热带和亚热带地区亦有分布。生于潮湿的石上或枯树上。该种全草药用。内服治风湿性关节炎、支气管炎；外敷治扭伤、骨折、痈疮疖肿等。(概述图参考来源：中国自然标本馆)"}
     */

    private double score;
    private String name;
    private BaikeInfoBean baike_info;



    public ResultBean() {

    }

    protected ResultBean(Parcel in) {
        score = in.readDouble();
        name = in.readString();
        baike_info = in.readParcelable(BaikeInfoBean.class.getClassLoader());
    }

    public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
        @Override
        public ResultBean createFromParcel(Parcel in) {
            return new ResultBean(in);
        }

        @Override
        public ResultBean[] newArray(int size) {
            return new ResultBean[size];
        }
    };

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaikeInfoBean getBaike_info() {
        return baike_info;
    }

    public void setBaike_info(BaikeInfoBean baike_info) {
        this.baike_info = baike_info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(score);
        dest.writeString(name);
        dest.writeParcelable(baike_info, flags);
    }


    public static class BaikeInfoBean implements Parcelable{
        /**
         * baike_url : http://baike.baidu.com/item/%E8%B1%86%E7%93%A3%E7%BB%BF/528814
         * image_url : http://imgsrc.baidu.com/baike/pic/item/203fb80e7bec54e766de0954b3389b504fc26a4d.jpg
         * description : 豆瓣绿(学名：Peperomia tetraphylla (Forst.f.) Hook.et Arn.)是胡椒科，草胡椒属多年生常绿草本植物。多年生肉质丛生草本，茎匍匐，多分枝，叶片密集，大小近相等，带肉质，有透明腺点，阔椭圆形或近圆形，两端钝或圆，叶脉细弱，通常不明显；叶柄短，穗状花序单生，顶生和腋生，花序轴密被毛；苞片近圆形，有短柄，盾状；花药近椭圆形，花丝短；子房卵形，浆果近卵形，2-4月及9-12月开花。分布于中国台湾、福建、广东、广西、贵州、云南、四川及甘肃南部和西藏南部。美洲、大洋洲、非洲及亚洲热带和亚热带地区亦有分布。生于潮湿的石上或枯树上。该种全草药用。内服治风湿性关节炎、支气管炎；外敷治扭伤、骨折、痈疮疖肿等。(概述图参考来源：中国自然标本馆)
         */

        private String baike_url;
        private String image_url;
        private String description;

        protected BaikeInfoBean(Parcel in) {
            baike_url = in.readString();
            image_url = in.readString();
            description = in.readString();
        }

        public static final Creator<BaikeInfoBean> CREATOR = new Creator<BaikeInfoBean>() {
            @Override
            public BaikeInfoBean createFromParcel(Parcel in) {
                return new BaikeInfoBean(in);
            }

            @Override
            public BaikeInfoBean[] newArray(int size) {
                return new BaikeInfoBean[size];
            }
        };

        public String getBaike_url() {
            return baike_url;
        }

        public void setBaike_url(String baike_url) {
            this.baike_url = baike_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(baike_url);
            dest.writeString(image_url);
            dest.writeString(description);
        }
    }
}