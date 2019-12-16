package com.shanchuang.ssf.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/25
 */
public class GoodsEvaBean {
        /**
         * comments : [{"content":"东西很不多的","createtime":1523569269,"images":["http://k12.w.shanchuang360.com/uploads/20191017/ebb077db26b998a9bd344d628b26347d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/45fe9878bbb7bcfe33c72e35b9de0c6d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"],"user_nickname":"加油想未来","user_avatar":"http://k12.w.shanchuang360.com/uploads/20191025/a0f27ea0711b8b981bbc3a338ecc25f6.png"}]
         * next : 0
         */

        private int next;
        private List<CommentsBean> comments;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            /**
             * content : 东西很不多的
             * createtime : 1523569269
             * images : ["http://k12.w.shanchuang360.com/uploads/20191017/ebb077db26b998a9bd344d628b26347d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/45fe9878bbb7bcfe33c72e35b9de0c6d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"]
             * user_nickname : 加油想未来
             * user_avatar : http://k12.w.shanchuang360.com/uploads/20191025/a0f27ea0711b8b981bbc3a338ecc25f6.png
             */

            private String content;
            private String createtime;
            private String user_nickname;
            private String user_avatar;
            private List<String> images;
            private List<LocalMedia> localMedia;

            public List<LocalMedia> getLocalMedia() {
                return localMedia;
            }

            public void setLocalMedia(List<LocalMedia> localMedia) {
                this.localMedia = localMedia;
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

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }
}
