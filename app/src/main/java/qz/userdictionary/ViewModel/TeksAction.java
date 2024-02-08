package qz.userdictionary.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import qz.userdictionary.MainActivity;

public class TeksAction extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        String words = getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
        Toast.makeText(this, words, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class).putExtras(getIntent()));
        finish();
        // TODO: Implement this method
    }
}
