package com.dofun.sxl.bean;

public class VideoBean extends BaseBean {
    private String videoUrl;
    private String name;
    private String teacherName;
    private String second;
    private long createTime;
    private String imageUrl;
    private int id;
    private Teacher teacher;

    public static class Teacher extends BaseBean {
        /**
         * avatarUrl : img/teacher.png
         * course : {"favorited":0,"id":10,"status":0,"subject":0}
         * courseId : 10
         * description : 从教三十年的好老师
         * id : 1
         * name : 宋小宝
         * status : 0
         */

        private String avatarUrl;
        private CourseBean course;
        private int courseId;
        private String description;
        private int id;
        private String name;
        private int status;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class CourseBean extends BaseBean {
            /**
             * favorited : 0
             * id : 10
             * status : 0
             * subject : 0
             */

            private int favorited;
            private int id;
            private int status;
            private int subject;

            public int getFavorited() {
                return favorited;
            }

            public void setFavorited(int favorited) {
                this.favorited = favorited;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSubject() {
                return subject;
            }

            public void setSubject(int subject) {
                this.subject = subject;
            }
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
