package android.hmkcode.com.yomeserorestaurantes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 4/11/15.
 */
public class Item {
    public String item_name;
    public String item_description;
    public String item_type;
    public int item_time;
    public float item_price;
    public String item_image;
    public int restaurant_id;
    public int quantity;
    public int id;


    public void parseFromJson(JSONObject json){
        try {
            item_name = json.getString("item_name");
            item_description = json.getString("item_description");
            item_type = json.getString("item_type");
            item_time = Integer.parseInt(json.getString("item_time"));
            item_price = Float.parseFloat(json.getString("item_price"));
            restaurant_id = Integer.parseInt(json.getString("restaurant_id"));
            item_image = json.getString("item_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getOrdenItem(JSONObject json){
        try {
            item_name = json.getString("item_name");
            item_image = json.getString("item_image");
            quantity = Integer.parseInt(json.getString("cantidad"));
            id = Integer.parseInt(json.getString("orden_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String show(){
        String result="";
        result += item_name;
        result += "\n";
        result += item_description;
        result += item_image;
        return result;
    }

    @Override
    public String toString(){
        return "Name: "+item_name+"\n"+"Quantity: "+Integer.toString(quantity)+"\n"+"Id: "+Integer.toString(id);
    }
}
