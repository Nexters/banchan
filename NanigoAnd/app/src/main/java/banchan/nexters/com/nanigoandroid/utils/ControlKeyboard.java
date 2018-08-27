package banchan.nexters.com.nanigoandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ellen on 2017. 2. 21..
 */

public class ControlKeyboard {
    /* Show Keyboard */
    public static void show(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = activity.getCurrentFocus();
        if (focus != null)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /* Hide Keyboard */
    public static void hide(Activity activity) {

//        InputMethodManager inputMethodManager = (InputMethodManager) activity
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        View focus = activity.getCurrentFocus();
//        if (focus != null)
//            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);


        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


    }


    public static InputFilter filter() {
        InputFilter filterAlphaNum = new InputFilter() {

            /**
             * 영문~숫자만 특수문자 제한
             **/
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
//                if (!ps.matcher(source).matches()) {
//                    return "";
//                }
//                return null;
//            }

            /**
             * 공백만 입력 안되도록
             */
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (Character.isSpaceChar(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
            }
        };
        return filterAlphaNum;

    }

}
