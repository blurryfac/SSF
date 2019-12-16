package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class ShopMsgBean {
        /**
         * store : {"id":2,"title":"郑州九中","mobile":"18838172691"}
         */

        private StoreBean store;

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public static class StoreBean {
            /**
             * id : 2
             * title : 郑州九中
             * mobile : 18838172691
             */

            private int id;
            private String title;
            private String mobile;
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
}
