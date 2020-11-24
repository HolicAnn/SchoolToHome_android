package com.example.easyreader;

import com.google.gson.Gson;

public class JsonUtils_detail {
    public String parseLoginStateFromJson(String jsonData) {
        String result = "";
        Gson gson = new Gson();
        Detail detail = gson.fromJson(jsonData, Detail.class);
        //result += "&#160;&#160;state---->" + login_state.getState() + "<br/>";
        result = detail.getState()+detail.getMsg()+detail.getData() ;
        return result;
    }
}
