package qz.userdictionary.Model;

import android.content.ContentValues;
import android.content.Context;
import android.provider.UserDictionary;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.List;
import java.util.Locale;

public class UserDictionaryHelper {
    Context appid;
    TextItems item;

    public UserDictionaryHelper(Context appid) {
        this.appid = appid;
    }

    public UserDictionaryHelper() {}

    public void applyToLocal(String localid) {
        ContentValues c = new ContentValues();
        c.put(UserDictionary.Words.APP_ID, this.appid.getPackageName());
        c.put(UserDictionary.Words.LOCALE, localid);
        c.put(UserDictionary.Words.WORD, this.item.getText());
        c.put(UserDictionary.Words.FREQUENCY, this.item.getFrek());
        c.put(UserDictionary.Words.SHORTCUT, this.item.getShort());
        this.appid.getContentResolver().insert(UserDictionary.Words.CONTENT_URI, c);
    }

    public void applytoAll() {
        for (String locale : Locale.getISOLanguages()) {
            applyToLocal(locale);
        }
    }

    public boolean isMyInputMethodEnabled() {
        boolean isEnabled = false;
        InputMethodManager inputMethodManager =
                (InputMethodManager) appid.getSystemService(appid.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodList = inputMethodManager.getEnabledInputMethodList();
        for (InputMethodInfo inputMethodInfo : inputMethodList) {
            if (inputMethodInfo.getPackageName().equals(appid.getPackageName())) {
                isEnabled = true;
                break;
            }
        }

        return isEnabled;
    }

    public Context getAppid() {
        return this.appid;
    }

    public void setAppid(Context appid) {
        this.appid = appid;
    }

    public TextItems getItem() {
        return this.item;
    }

    public void setItem(TextItems item) {
        this.item = item;
    }
}
