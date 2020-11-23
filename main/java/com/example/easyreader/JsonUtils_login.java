package com.example.easyreader;

import com.google.gson.Gson;

public class JsonUtils_login {
    public String parseLoginStateFromJson(String jsonData) {
        String result = "";
        Gson gson = new Gson();
        Login_state login_state = gson.fromJson(jsonData, Login_state.class);
        result += "&#160;&#160;state---->" + login_state.getState() + "<br/>";
        result += "&#160;&#160;msg---->" + login_state.getMsg() + "<br/>";
        return result;
    }
}
