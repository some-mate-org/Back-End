package org.somemate.demo.user.dto;

public class RecommendedUser {
    private int idx;
    private String profile;
    private String openchat_link;
    private String name;
    private String gender;
    private String age;
    private String mbti;
    private String desc;

    public RecommendedUser(int idx, String profile, String openchat_link, String name, String gender, String age, String mbti, String desc) {
        this.idx = idx;
        this.profile = profile;
        this.openchat_link = openchat_link;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.mbti = mbti;
        this.desc = desc;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getOpenchat_link() {
        return openchat_link;
    }

    public void setOpenchat_link(String openchat_link) {
        this.openchat_link = openchat_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
