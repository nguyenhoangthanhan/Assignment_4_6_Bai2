package com.andeptrai.baitapandroid014_6_2020.student_manage;

public class Student {
    int mssv;
    String name;
    String email;
    String birth;
    String homeTown;

    public Student(int mssv, String name, String birth, String email, String homeTown) {
        this.mssv = mssv;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.homeTown = homeTown;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }
}
