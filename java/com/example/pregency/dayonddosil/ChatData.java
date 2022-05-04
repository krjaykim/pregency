package com.example.pregency.dayonddosil;

/**
 * Created by KPlo on 2018. 11. 3..
 */

public class ChatData {
    private String msg;
    private String nickname;



    //필수!
    public ChatData(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}