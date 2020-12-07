package com.example.easyreader;

public class Detail
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

    public Data data;

    public class Data{
        private String username;

        public void setUsername(String username){this.username=username;}

        public String getUsername(){return username;}

        public String nickname;

        public void setNickname(String nickname){this.nickname=nickname;}

        public String getNickname(){return nickname;}

        private String role;

        private String password;

        private String created_time;

        private String classesId;

        public String memo;

        public String XH;
    }

}
