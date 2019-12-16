package com.shanchuang.ssf.net.entity;

/**
 * Created by JY on 2017/11/20.
 */

public class LoginBean {

    /**
     * userinfo : {"id":2,"mobile":"15139091146","nickname":"加油向未来","avatar":"http://test.k12.com/uploads/20190610/939b006647cca3fe884eb59ba238b0ab.png","school_id":"学校id"}
     */

    private UserinfoBean userinfo;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * id : 2
         * mobile : 15139091146
         * nickname : 加油向未来
         * avatar : http://test.k12.com/uploads/20190610/939b006647cca3fe884eb59ba238b0ab.png
         * school_id : 学校id
         */

        private String id;
        private String mobile;
        private String nickname;
        private String avatar;
        private String school_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
