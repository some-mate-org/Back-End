package org.somemate.demo.mbti.dto;

public class Mbti {
    private String my_mbti;
    private String title;
    private String desc;

    public Mbti(String my_mbti, String title, String desc) {
        this.my_mbti = my_mbti;
        this.title = title;
        this.desc = desc;
    }

    public String getMy_mbti() {
        return my_mbti;
    }

    public void setMy_mbti(String my_mbti) {
        this.my_mbti = my_mbti;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
