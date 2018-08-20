package banchan.nexters.com.nanigoandroid.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;


import java.io.UnsupportedEncodingException;
import java.util.UUID;


public class Utils {


    public  String getUuid(Context context) {
        if (PreferenceManager.getInstance(context).getUuid().equals("")) {
            setUuid(context);
        }
        String uuid = PreferenceManager.getInstance(context).getUuid();
        return uuid;
    }

    private void setUuid(final Context context) {
        UUID deviceUUID = null;
        String usercode = "";
        final String androidUniqueID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            if (androidUniqueID != "") {
                deviceUUID = UUID.nameUUIDFromBytes(androidUniqueID.getBytes("utf8"));
            } else {

                String anotherUniqueID = null;
                try {
                    anotherUniqueID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                if (anotherUniqueID != null) {
                    deviceUUID = UUID.nameUUIDFromBytes(anotherUniqueID.getBytes("utf8"));
                } else {
                    deviceUUID = UUID.randomUUID();
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        usercode = deviceUUID.toString();
        PreferenceManager.getInstance(context).setUuid(usercode);
    }



    public static boolean isHangul(char ch) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        if (Character.UnicodeBlock.HANGUL_SYLLABLES == block || Character.UnicodeBlock.HANGUL_JAMO == block || Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO == block) {
            return true;
        }
        return false;
    }
}
