package com.kingnet.sdk.collectdata.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataMessage {

    public String project;
    public String did;
    public String ouid;
    public String timestamp;
    public String event;
    public String eventid;
    public JSONObject properties;

    //json 格式转化
    public String toJson(){
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("project", project);
            jsonObject.put("did", did);
            jsonObject.put("ouid", ouid);
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("event", event);
            jsonObject.put("eventid", eventid);
            jsonObject.put("properties",properties);
            jsonArray.put(jsonObject);
            return jsonArray.toString();
        }catch (Exception e) {
            return "";
        }
    }

}
