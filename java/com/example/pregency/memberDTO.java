package com.example.pregency;

public class memberDTO {

    private String id;
    private String pw;
    private String pwcheck;
    private String name;
    private String num;
    private String nick;

    public memberDTO(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPwcheck() {
        return pwcheck;
    }

    public void setPwcheck(String pwcheck) {
        this.pwcheck = pwcheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public memberDTO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }


    public memberDTO(String id, String pw, String pwcheck, String name, String num, String nick) {
        this.id = id;
        this.pw = pw;
        this.pwcheck = pwcheck;
        this.name = name;
        this.num = num;
        this.nick = nick;
    }
}
