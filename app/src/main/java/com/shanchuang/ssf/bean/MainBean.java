package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/30
 */
public class MainBean {
        /**
         * advert : [{"id":5,"image":"http://k12.w.shanchuang360.com/uploads/20191018/73294fca6dc8fcd0ea10e51a53606072.jpg","url":1,"tz_id":3},{"id":4,"image":"http://k12.w.shanchuang360.com/uploads/20191018/156b4441459eb7850afa584bcae73a83.jpg","url":2,"tz_id":2}]
         * news : [{"id":4,"title":"央视快评：让尊老敬老助老传统发扬光大"},{"id":3,"title":"人民日报评论员：从实际出发抓重点问题"},{"id":5,"title":"1周内2邻国接连力挺中国 杜特尔特再向美国开炮"},{"id":2,"title":"中央连续2次授予他荣誉称号:谢绝专车 多步行上班"}]
         * map : {"lng":"113.72721","lat":"34.773487"}
         * jianjie : http://k12.w.shanchuang360.com/k12/article/index.html
         * school :
         */

        private MapBean map;
        private String jianjie;
        private String school;
        private List<AdvertBean> advert;
        private List<NewsBean> news;

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class MapBean {
            /**
             * lng : 113.72721
             * lat : 34.773487
             */

            private String lng;
            private String lat;

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }



        public static class NewsBean {
            /**
             * id : 4
             * title : 央视快评：让尊老敬老助老传统发扬光大
             */

            private String id;
            private String title;

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
        }
}
