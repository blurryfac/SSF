package com.shanchuang.ssf.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/25
 */
public class ShopEvaBean {
        /**
         * pinglun : [{"id":1,"title":"2019高端独角兽学生礼物男女儿童文具套装","image":"http://test.k12.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg","content":"东西很不多的","createtime":1523569269,"images":["http://test.k12.com/uploads/20191017/ebb077db26b998a9bd344d628b26347d.jpg","http://test.k12.com/uploads/20191017/45fe9878bbb7bcfe33c72e35b9de0c6d.jpg","http://test.k12.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"]}]
         * next : 0
         */

        private int next;
        private List<PinglunBean> pinglun;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class PinglunBean {
            /**
             * id : 1
             * title : 2019高端独角兽学生礼物男女儿童文具套装
             * image : http://test.k12.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg
             * content : 东西很不多的
             * createtime : 1523569269
             * images : ["http://test.k12.com/uploads/20191017/ebb077db26b998a9bd344d628b26347d.jpg","http://test.k12.com/uploads/20191017/45fe9878bbb7bcfe33c72e35b9de0c6d.jpg","http://test.k12.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"]
             */

            private String id;
            private String title;
            private String image;
            private String content;
            private String createtime;
            private List<String> images;
            private List<LocalMedia> localMedia;

            public List<LocalMedia> getLocalMedia() {
                return localMedia;
            }

            public void setLocalMedia(List<LocalMedia> localMedia) {
                this.localMedia = localMedia;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
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
