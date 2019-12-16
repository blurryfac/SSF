package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/14
 */
public class TeamIntroBean {

        /**
         * team : [{"id":3,"name":"陶行知","zhiwu":"团队主管","image":"http://k12.w.shanchuang360.com/uploads/20191008/6700abfefcb9ce84482a83c71bb26683.jpg"},{"id":2,"name":"叶澜","zhiwu":"教育家","image":"http://k12.w.shanchuang360.com/uploads/20191008/cf3d82062587c7fb6482dd9a6cf8c91e.jpg"},{"id":1,"name":"基特·哈灵顿","zhiwu":"外教主管","image":"http://k12.w.shanchuang360.com/uploads/20191008/241f94ff15de407808e20072ef891423.jpg"}]
         * next : 0
         */

        private int next;
        private List<TeamBean> team;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<TeamBean> getTeam() {
            return team;
        }

        public void setTeam(List<TeamBean> team) {
            this.team = team;
        }

        public static class TeamBean {
            /**
             * id : 3
             * name : 陶行知
             * zhiwu : 团队主管
             * image : http://k12.w.shanchuang360.com/uploads/20191008/6700abfefcb9ce84482a83c71bb26683.jpg
             */

            private String id;
            private String name;
            private String zhiwu;
            private String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getZhiwu() {
                return zhiwu;
            }

            public void setZhiwu(String zhiwu) {
                this.zhiwu = zhiwu;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
    }
}
