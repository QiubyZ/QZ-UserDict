package qz.userdictionary.ViewModel;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import qz.userdictionary.MainActivity;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
import qz.userdictionary.Service.AddItemOnService;

public class TeksAction extends AppCompatActivity {

    public static String WORD_KEYS = ":";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        String words = getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
        Actions(words);
    }

    void Actions(String x) {
        if (x.contains(WORD_KEYS)) {
            int firstColonIndex = x.indexOf(":");
            String key1 = x.substring(0, firstColonIndex);
            String key2 = x.substring(firstColonIndex + 1);
            Toast.makeText(this, String.format("Add %s %s", key1, key2), 1).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Data.Builder dataBuilder = new Data.Builder();
                dataBuilder.putString("key1", key1);
                dataBuilder.putString("key2", key2);
                Data data = dataBuilder.build();
                OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(AddItemOnService.class)
                        .setInputData(data).build();
                WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new UserDictionaryHelper(getApplicationContext()).add(
                                new TextItems(
                                        key2,
                                        key1,
                                        "250",
                                        String.valueOf(UserDictionary.Words.LOCALE_TYPE_ALL)));
                    }
                }).start();

            }
            finish();
        } else {
            startActivity(
                    new Intent(getApplicationContext(), MainActivity.class)
                            .putExtra("DATA", x.toString())

            );
        }
        finish();
    }
}
