package org.somemate.demo.user.dto;

public class User {
    private int idx;
    private String openchatLink;
    private String name;
    private String userId;
    private String password;
    private String mbti;
    private String refreshToken;
    private int gender;
    private String profile;
    private int age;


    public User(int idx, String openchat_link, String name, String password, String mbti, int age, int gender, String profile, String refreshToken) {
        this.idx = idx;
        this.openchatLink = openchatLink;
        this.name = name;
        this.password = password;
        this.refreshToken = refreshToken;
        this.gender = gender;
        this.profile = profile;
        this.age = age;
        this.mbti = mbti;
        this.userId = userId;

    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOpenChatLink() {
        return openchatLink;
    }

    public void setOpenChatLink(String openchat_link) {
        this.openchatLink = openchat_link;
    }

    @Override
    public String toString() {
        return "User [idx=" + idx + ", openchatLink=" + openchatLink + ", name=" + name + ", userId=" + userId + ", refreshToken=" + refreshToken + ", gender=" + gender + ", profile=" + profile + ", age=" + age + "]";
    }

}
