package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class ExercisesAdvertBean {
        /**
         * advert : {"image":"http://test.k12.com/uploads/20191018/d82c59243c9fb6bc799f1812a4fbee1f.jpg"}
         */

        private AdvertBean advert;

        public AdvertBean getAdvert() {
            return advert;
        }

        public void setAdvert(AdvertBean advert) {
            this.advert = advert;
        }

        public static class AdvertBean {
            /**
             * image : http://test.k12.com/uploads/20191018/d82c59243c9fb6bc799f1812a4fbee1f.jpg
             */

            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
}
