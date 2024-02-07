package qz.userdictionary.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.List;

public class UserDictionaryHelper {
    Context appid;
    public UserDictionaryHelper(Context appid) {
        this.appid = appid;
    }

    public UserDictionaryHelper() {}

    public void add(TextItems item) {
        ContentValues c = new ContentValues();
        c.put(UserDictionary.Words.APP_ID, appid.getPackageName());
        c.put(UserDictionary.Words.LOCALE, item.getLocale());
        c.put(UserDictionary.Words.WORD, item.getText());
        c.put(UserDictionary.Words.FREQUENCY, item.getFrek());
        c.put(UserDictionary.Words.SHORTCUT, item.getShort());
        ContentResolver content = appid.getContentResolver();
        content.insert(UserDictionary.Words.CONTENT_URI, c);
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
                            UserDictionary.Words.SHORTCUT,
                            UserDictionary.Words._ID,
                            UserDictionary.Words.LOCALE
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
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserDictionary.Words._ID));
                    String locale = cursor.getString(cursor.getColumnIndexOrThrow(UserDictionary.Words.LOCALE));
                
                    listof.add(new TextItems(word, shortcut, String.valueOf(Freq), id, locale));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listof;
    }
    
    public void update(TextItems item){
        ContentValues c = new ContentValues();
        c.put(UserDictionary.Words.APP_ID, appid.getPackageName());
        c.put(UserDictionary.Words.LOCALE, item.getLocale());
        c.put(UserDictionary.Words.WORD, item.getText());
        c.put(UserDictionary.Words.FREQUENCY, item.getFrek());
        c.put(UserDictionary.Words.SHORTCUT, item.getShort());
        ContentResolver content = appid.getContentResolver();
        content.update(
            UserDictionary.Words.CONTENT_URI, c,
            UserDictionary.Words._ID + " = ?", 
            new String[]{String.valueOf(item.get_id())}
            );
    }

    public void clearAllDictionary() {
        appid.getContentResolver().delete(UserDictionary.Words.CONTENT_URI, null, null);
    }

    public void clearSelectItem(int id) {
        appid.getContentResolver()
                .delete(
                        UserDictionary.Words.CONTENT_URI,
                        UserDictionary.Words._ID + " = ?",
                        new String[] {String.valueOf(id)});
    }

    public boolean isMyInputMethodEnabled() {
        boolean isEnabled = false;
        InputMethodManager inputMethodManager =  manager();
        List<InputMethodInfo> inputMethodList = inputMethodManager.getEnabledInputMethodList();
        for (InputMethodInfo inputMethodInfo : inputMethodList) {
            if (inputMethodInfo.getPackageName().equals(appid.getPackageName())) {
                isEnabled = true;
                break;
            }
        }

        return isEnabled;
    }
    
    public InputMethodManager manager(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) appid.getSystemService(appid.INPUT_METHOD_SERVICE);
        return inputMethodManager;
    }

    public Context getAppid() {
        return this.appid;
    }

    public void setAppid(Context appid) {
        this.appid = appid;
    }
}
