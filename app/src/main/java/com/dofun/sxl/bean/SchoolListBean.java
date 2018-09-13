package com.dofun.sxl.bean;

import java.util.List;

public class SchoolListBean extends BaseBean {


    /**
     * classList : [{"className":"1808","createTime":1534820625000,"id":4,"school":{"createTime":1534820625000,"id":1,"schoolName":"武汉市江汉区大兴路小学","schoolRemark":"江汉区大兴路69号","schoolUrl":"85663174","status":0},"schoolId":0,"schoolName":"武汉市江汉区大兴路小学","status":0}]
     * schoolId : 1
     * schoolName : 武汉市江汉区大兴路小学
     */

    private int schoolId;
    private String schoolName;
    private List<ClassListBean> classList;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<ClassListBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassListBean> classList) {
        this.classList = classList;
    }

    public static class ClassListBean extends BaseBean {
        /**
         * className : 1808
         * createTime : 1534820625000
         * id : 4
         * school : {"createTime":1534820625000,"id":1,"schoolName":"武汉市江汉区大兴路小学","schoolRemark":"江汉区大兴路69号","schoolUrl":"85663174","status":0}
         * schoolId : 0
         * schoolName : 武汉市江汉区大兴路小学
         * status : 0
         */

        private String className;
        private long createTime;
        private int id;
        private SchoolBean school;
        private int schoolId;
        private String schoolName;
        private int status;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class SchoolBean {
            /**
             * createTime : 1534820625000
             * id : 1
             * schoolName : 武汉市江汉区大兴路小学
             * schoolRemark : 江汉区大兴路69号
             * schoolUrl : 85663174
             * status : 0
             */

            private long createTime;
            private int id;
            private String schoolName;
            private String schoolRemark;
            private String schoolUrl;
            private int status;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public String getSchoolRemark() {
                return schoolRemark;
            }

            public void setSchoolRemark(String schoolRemark) {
                this.schoolRemark = schoolRemark;
            }

            public String getSchoolUrl() {
                return schoolUrl;
            }

            public void setSchoolUrl(String schoolUrl) {
                this.schoolUrl = schoolUrl;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
