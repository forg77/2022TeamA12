package edu.zstu.examsys.util;

import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.Condition;

public class JSONUtils {
    private JSONUtils(){

    }

    public static Condition setCondition(JSONObject body){
        Condition con = new Condition();
        con.setOffset(body.getInteger("offset"));
        con.setMax(body.getInteger("max"));
        con.setOrder(body.getString("order"));
        con.setDesc(body.getBoolean("desc"));
        return con;
    }
}
