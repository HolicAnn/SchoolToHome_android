package com.example.easyreader;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class JsonUtils_login {
    public Login_state parseLoginStateFromJson(String jsonData) {
        String result = "";
        Gson gson = new Gson();
        Login_state login_state = gson.fromJson(jsonData, Login_state.class);
        //System.out.println(login_state.getData());
        //result += "&#160;&#160;state---->" + login_state.getState() + "<br/>";
//        Context ctx = getActivity();
//        SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
//        SharedPreferences.Editor editor = share.edit();
//        String str="?data_id="+login_state.getData();
//        editor.putString("data_id", str);
//        editor.commit();
        //result += login_state.getMsg() ;
        return login_state;
    }
}
