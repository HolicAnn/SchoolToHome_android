package com.example.easyreader;

public class Login_state
{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private int state;

    private String data;

    public void setData(String data){this.data=data;}

    public String getData(){return data;}
}
