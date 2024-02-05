package qz.userdictionary.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import qz.userdictionary.Model.TextItems;

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

    public List<TextItems> getListItem() {
        List<TextItems> listof = new ArrayList<TextItems>();
        ContentResolver resolver = appid.getContentResolver();
        Cursor cursor =
                resolver.query(
                        UserDictionary.Words.CONTENT_URI,
                        new String[] {
                            UserDictionary.Words.WORD,
                            UserDictionary.Words.FREQUENCY,
                            UserDictionary.Words.SHORTCUT
                        },
                        null,
                        null,
                        UserDictionary.Words.WORD + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                    String word =
                            cursor.getString(cursor.getColumnIndexOrThrow(UserDictionary.Words.WORD));
                    String shortcut =
                            cursor.getString(cursor.getColumnIndexOrThrow(UserDictionary.Words.SHORTCUT));
                    int Freq = cursor.getInt(cursor.getColumnIndexOrThrow(UserDictionary.Words.FREQUENCY));
                    listof.add(new TextItems(word, shortcut, String.valueOf(Freq)));
                
            } while (cursor.moveToNext());
        }

        return listof;
    }

    public void clearAllDictionary() {
        appid.getContentResolver().delete(UserDictionary.Words.CONTENT_URI, null, null);
    }

    public void clearSelectItem(String kata) {
        appid.getContentResolver()
                .delete(
                        UserDictionary.Words.CONTENT_URI,
                        UserDictionary.Words.WORD + " = ?",
                        new String[] {kata});
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
