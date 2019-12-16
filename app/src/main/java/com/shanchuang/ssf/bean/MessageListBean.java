package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/20
 */
public class MessageListBean {
        /**
         * news : [{"id":4,"title":"央视快评：让尊老敬老助老传统发扬光大","createtime":1570504638},{"id":3,"title":"人民日报评论员：从实际出发抓重点问题","createtime":1570504614},{"id":5,"title":"1周内2邻国接连力挺中国 杜特尔特再向美国开炮","createtime":1570504670},{"id":2,"title":"中央连续2次授予他荣誉称号:谢绝专车 多步行上班","createtime":1570504590}]
         * next : 0
         */

        private int next;
        private List<NewsBean> news;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * id : 4
             * title : 央视快评：让尊老敬老助老传统发扬光大
             * createtime : 1570504638
             */

            private String id;
            private String title;
            private String createtime;

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

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }
}
