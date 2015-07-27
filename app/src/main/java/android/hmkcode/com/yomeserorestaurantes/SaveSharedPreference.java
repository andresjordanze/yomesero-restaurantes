package android.hmkcode.com.yomeserorestaurantes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alex on 7/27/15.
 */
public class SaveSharedPreference {

    static final String PREF_USER_ID = "userid";
    static final String PREF_USER_REST = "userrest";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserId(Context ctx, String userid){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID,userid);
        editor.commit();
    }

    public static void setUserRest(Context ctx, String userrest){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_REST,userrest);
        editor.commit();
    }

    public static String getUserId(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_ID,"");
    }

    public static String getUserRest(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_REST,"");
    }

}
