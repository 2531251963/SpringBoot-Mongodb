package com.example.mongodb.bean;

/**
 * @ClassName User
 * @Description User
 * @Author Liyihe
 * @Date 19-8-11 下午11:48
 * @Version 1.0
 */
public class User {

    private String id;
    private String name;
    private String phonenumber;
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
