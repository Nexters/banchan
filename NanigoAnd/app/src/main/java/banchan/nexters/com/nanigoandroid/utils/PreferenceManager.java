package banchan.nexters.com.nanigoandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is for use sharedPreferences to easy on evenywhere.
 * Implement singleton pattern. If you want add value.
 * You can modify sample code.
 * <p/>
 * Sampe code. (You will change all key(KEY,Key,key) to value name use before)
 * private  String KEY = "key";
 * private String key = "";
 * <p/>
 * public void setKey(String key){
 * this.key = key;
 * mEditor.putString(KEY,key);
 * mEditor.apply();
 * }
 * <p/>
 * public String getKey(){
 * if(key.equals("")){
 * key = mPrefs.getString("KEY", "");
 * }
 * return key;
 * }
 * //background select
 * private  String BGSELECT = "bgSelect";
 * private int bgSelect = 1;
 * <p/>
 * public void setBgSelect(int bgSelect){
 * this.bgSelect = bgSelect;
 * mEditor.putInt(BGSELECT, bgSelect);
 * mEditor.apply();
 * }
 * <p/>
 * public int getBgSelect(){
 * if(bgSelect == 1){
 * bgSelect = mPrefs.getInt(BGSELECT, 1);
 * }
 * return bgSelect;
 * }
 */

/*
 *getpref
 * PreferenceManager.getInstance().getPhonenum()
 *
 * setpref
 * PreferenceManager.getInstance().setPphoneNum(phone);
 *
 */



public class PreferenceManager {
    private String PREF_NAME = "nanigo_prefs.xml";

    private static PreferenceManager instance;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public static PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context);
        }
        return instance;
    }


    private PreferenceManager(Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }


    private String ONBOARD = "ONBOARD";

    public void setOnboard(boolean key) {
        mEditor.putBoolean(ONBOARD, key);
        mEditor.apply();
    }

    public boolean getOnboard() {
        return mPrefs.getBoolean(ONBOARD, true);
    }


    private String UUID = "UUID";

    public void setUuid(String key) {
        mEditor.putString(UUID, key);
        mEditor.apply();
    }

    public String getUuid() {
        return mPrefs.getString(UUID, "");
    }



    private String USERID = "USERID";

    public void setUserId(String key) {
        mEditor.putString(USERID, key);
        mEditor.apply();
    }

    public String getUserId() {
        return mPrefs.getString(USERID, "");
    }



    private String USERNAME = "USERNAME";

    public void setUserName(String key) {
        mEditor.putString(USERNAME, key);
        mEditor.apply();
    }

    public String getUserName() {
        return mPrefs.getString(USERNAME, "");
    }




    //clear
    public void clear(String key) {
        mEditor.remove(key);
        mEditor.apply();
    }

    public void allClear() {
        mEditor = mPrefs.edit();
        mEditor.clear();
        mEditor.apply();
    }
}
