package com.shanchuang.ssf.net.entity;

/**
 * Created by JY on 2018/2/7.
 */

public class UserBean {

        /**
         * user : {"avatar":"http://test.k12.com/assets/img/avatar.png","nickname":"188****2691"}
         * tel : 0371-56236958
         */

        private UserInfoBean user;
        private String tel;

        public UserInfoBean getUser() {
            return user;
        }

        public void setUser(UserInfoBean user) {
            this.user = user;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public static class UserInfoBean {
            /**
             * avatar : http://test.k12.com/assets/img/avatar.png
             * nickname : 188****2691
             */

            private String avatar;
            private String nickname;
            private String school;

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
}
