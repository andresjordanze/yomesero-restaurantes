package android.hmkcode.com.yomeserorestaurantes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * Created by Andres on 27/07/2015.
 */
public class Order implements Serializable{
    public Float consumo;
    public String estado;
    public int mesa;
    public int rest;
    public int id;

    public String toJSON(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("consumo", consumo);
            jsonObject.put("estado", estado);
            jsonObject.put("mesa", mesa);
            jsonObject.put("rest", rest);
            return jsonObject.toString();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
    public String fromJSON(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            consumo = Float.parseFloat(jsonObject.getString("consumo"));
            estado = jsonObject.getString("estado");
            mesa = Integer.parseInt(jsonObject.getString("mesa"));
            rest = Integer.parseInt(jsonObject.getString("rest"));
            id = Integer.parseInt(jsonObject.getString("id"));
            return show();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }

    public void parseFromJson(JSONObject json){
        try {
            id = Integer.parseInt(json.getString("id"));
            consumo = Float.parseFloat(json.getString("consumo"));
            estado = json.getString("estado");
            mesa = Integer.parseInt(json.getString("mesa"));
            rest = Integer.parseInt(json.getString("rest"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String show(){
        String result="";
        result += consumo;
        result += "\n";
        result += estado;
        result += mesa;
        result += rest;
        return result;
    }

    @Override
    public String toString(){
        return "Consumo: "+Float.toString(consumo)+"\n"+"estado: "+estado+"\n"+"Mesa: "+Integer.toString(mesa)+"\n"+"Time: "+Integer.toString(rest);
    }
}
