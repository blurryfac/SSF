package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/14
 */
public class SchoolDataBean {

    /**
     * school : [{"id":2,"title":"郑州九中"},{"id":1,"title":"中心学校"}]
     * next : 0
     */

    private int next;
    private List<SchoolBean> school;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public List<SchoolBean> getSchool() {
        return school;
    }

    public void setSchool(List<SchoolBean> school) {
        this.school = school;
    }

    public static class SchoolBean {
        /**
         * id : 2
         * title : 郑州九中
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
