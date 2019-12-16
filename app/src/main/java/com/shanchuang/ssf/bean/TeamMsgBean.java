package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/6
 */
public class TeamMsgBean {
        /**
         * team : {"id":1,"name":"基特·哈灵顿","xueli":"博士","age":37,"renjiao":"4年","image":"http://k12.w.shanchuang360.com/uploads/20191008/241f94ff15de407808e20072ef891423.jpg"}
         * url : http://k12.w.shanchuang360.com/k12/article/team/id/1.html
         */

        private TeamBean team;
        private String url;

        public TeamBean getTeam() {
            return team;
        }

        public void setTeam(TeamBean team) {
            this.team = team;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class TeamBean {
            /**
             * id : 1
             * name : 基特·哈灵顿
             * xueli : 博士
             * age : 37
             * renjiao : 4年
             * image : http://k12.w.shanchuang360.com/uploads/20191008/241f94ff15de407808e20072ef891423.jpg
             */

            private String id;
            private String name;
            private String xueli;
            private String age;
            private String renjiao;
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

            public String getXueli() {
                return xueli;
            }

            public void setXueli(String xueli) {
                this.xueli = xueli;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getRenjiao() {
                return renjiao;
            }

            public void setRenjiao(String renjiao) {
                this.renjiao = renjiao;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
}
