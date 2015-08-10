package android.hmkcode.com.yomeserorestaurantes;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Andres on 27/07/2015.
 */
public class Order implements Serializable{
    public Float consumo;
    public String estado;
    public int mesa;
    public int rest;
    public int id;
    public int nit;
    public String name;

    public void parseFromJson(JSONObject json){
        try {
            id = Integer.parseInt(json.getString("id"));
            consumo = Float.parseFloat(json.getString("consumo"));
            estado = json.getString("estado");
            mesa = Integer.parseInt(json.getString("mesa"));
            rest = Integer.parseInt(json.getString("rest"));
            nit = Integer.parseInt(json.getString("nit"));
            name = json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Consumo: "+Float.toString(consumo)+"\n"+"estado: "+estado+"\n"+"Mesa: "+Integer.toString(mesa)+"\n"+"Time: "+Integer.toString(rest);
    }
}
