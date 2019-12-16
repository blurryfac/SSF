package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/28
 */
public class AuthBean {

        /**
         * userinfo : {"id":9,"nickname":"´_&gt;`酷","mobile":"18238212218","school_id":1}
         * fangshi : 0
         */

        private UserinfoBean userinfo;
        private int fangshi;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public int getFangshi() {
            return fangshi;
        }

        public void setFangshi(int fangshi) {
            this.fangshi = fangshi;
        }

        public static class UserinfoBean {
            /**
             * id : 9
             * nickname : ´_&gt;`酷
             * mobile : 18238212218
             * school_id : 1
             */

            private String id;
            private String nickname;
            private String mobile;
            private String school_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
            }
        }
}
