package qz.userdictionary;

import android.provider.UserDictionary;
import java.util.Dictionary;
import qz.userdictionary.ViewModel.Dialogs;
import java.util.Dictionary;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
import qz.userdictionary.ViewModel.mAdpView;
import qz.userdictionary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<TextItems> textitem;
    mAdpView adapter;
    UserDictionaryHelper dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());

        textitem = new ArrayList<TextItems>();
        adapter = new mAdpView(textitem);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.keys.setText("coba");
        binding.checkleng.setText("makan ikan tapi nete");
        dictionary = new UserDictionaryHelper(this);

        binding.checkleng.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence arg0, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence text, int start, int before, int count) {
                        String TargetAngka = binding.tergetAngka.getText().toString();

                        if (TargetAngka.isEmpty() || TargetAngka.isBlank()) {
                            binding.tergetAngka.setText("45");
                            TargetAngka = binding.tergetAngka.getText().toString();
                        }

                        int hut = text.length();
                        String hit = String.valueOf(hut);
                        binding.panjang.setText(hit);

                        if (hut > Integer.valueOf(TargetAngka)) {
                            binding.panjang.setTextColor(Color.RED);
                        } else {
                            binding.panjang.setTextColor(Color.BLACK);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });

        binding.add.setOnClickListener(
                (v) -> {
                    String kata = binding.checkleng.getText().toString();
                    String keys = binding.keys.getText().toString();
                    TextItems teks = new TextItems();
                    teks.setText(kata);
                    teks.setShort(keys);
                    teks.setFrek("250");
                    dictionary.setItem(teks);
                    dictionary.applyToLocal("in_ID");
                });
    }

    @Override
    protected void onStart() {
        check();
        super.onStart();
        // TODO: Implement this method
    }

    void check() {
        if (dictionary.isMyInputMethodEnabled()) {
            Toast.makeText(this, "HIDUP", Toast.LENGTH_LONG).show();
        } else {
            new Dialogs(this);
        }
    }
}
