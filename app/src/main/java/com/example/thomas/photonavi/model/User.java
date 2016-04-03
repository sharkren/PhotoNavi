package com.example.thomas.photonavi.model;

/**
 * Created by Administrator on 2016-04-03.
 */
public class User {

    private String email;
    private String name;
    private String birth;
    private String cellPhone;
    private String useNavi;
    private String address;
    private String addressDetail;
    private String autoRecommand;
    private String pushRecv;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUseNavi() {
        return useNavi;
    }

    public void setUseNavi(String useNavi) {
        this.useNavi = useNavi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAutoRecommand() {
        return autoRecommand;
    }

    public void setAutoRecommand(String autoRecommand) {
        this.autoRecommand = autoRecommand;
    }

    public String getPushRecv() {
        return pushRecv;
    }

    public void setPushRecv(String pushRecv) {
        this.pushRecv = pushRecv;
    }

    public static User getUserData() {
        return userData;
    }

    public static void setUserData(User userData) {
        User.userData = userData;
    }

    private static User userData = new User();

    public static User getInstance() {

        return userData;
    }

    private User() {
    }


}
