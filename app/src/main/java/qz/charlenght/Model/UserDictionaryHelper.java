package qz.charlenght.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.UserDictionary;
import java.util.Dictionary;
import java.util.Locale;
import qz.charlenght.Model.TextItems;
import qz.charlenght.Model.UserDictionaryHelper;

public class UserDictionaryHelper {
    Context appid;
    TextItems item;

    public UserDictionaryHelper(Context appid, TextItems item) {
        this.appid = appid;
        this.item = item;
    }

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
}
