package qz.userdictionary;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.provider.UserDictionary;
import android.widget.Toast;
import qz.userdictionary.ViewModel.Dialogs;

import android.text.Editable;
import android.text.TextWatcher;
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
    int MAX_LENGT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            String words = getIntent().getStringExtra(Intent.EXTRA_PROCESS_TEXT);
            if (words != null) {
                if (words.length() > MAX_LENGT) {
                    binding.viewCounter.setError(getString(R.string.warning_lenght));
                }
                binding.inputWords.setText(words);
            }
        }

        textitem = new ArrayList<TextItems>();
        adapter = new mAdpView(textitem, new Handler(Looper.getMainLooper()));
        dictionary = new UserDictionaryHelper(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        textitem.addAll(dictionary.getListItem());
        adapter.notifyDataSetChanged();

        binding.inputTergetAngka.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence arg0, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        String empt = binding.inputTergetAngka.getText().toString();
                        checktargetAngka(empt);
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });

        binding.inputWords.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence t, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence text, int start, int before, int count) {
                        String TargetAngka = binding.inputTergetAngka.getText().toString();
                        if (TargetAngka.isBlank() || TargetAngka.isEmpty()) {
                            TargetAngka = "45";
                            binding.inputTergetAngka.setText(TargetAngka);
                        }
                        if (text.length() > MAX_LENGT) {
                            binding.viewCounter.setError(
                                    getString(R.string.warning_lenght));
                        binding.viewCounter.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                        binding.viewCounter.setErrorIconTintList(ColorStateList.valueOf(Color.RED));
                        }
                        else if (text.length() > Integer.valueOf(TargetAngka)) {
                            binding.viewCounter.setError(getString(R.string.warning_words));
                            binding.viewCounter.setErrorTextColor(ColorStateList.valueOf(R.color.grey));
                            binding.viewCounter.setErrorIconTintList(ColorStateList.valueOf(R.color.grey));
                        }
                        else {
                            binding.viewCounter.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });

        binding.add.setOnClickListener(
                (v) -> {
                    String kata = binding.inputWords.getText().toString();
                    String keys = binding.inputKeys.getText().toString();

                    addData(
                            new TextItems(
                                    kata,
                                    keys,
                                    "250",
                                    String.valueOf(UserDictionary.Words.LOCALE_TYPE_ALL)));
                });

        binding.clearAll.setOnClickListener(
                (v) -> {
                    ClearAllDictionary();
                });
        
        binding.exportdict.setOnClickListener((v) -> {
            pesan("TEST BUTTON");
        });
        binding.importdict.setOnClickListener((v) -> {
            pesan("TEST BUTTON");
        });
    }
    
    String getStringSource(int s){
        return getResources().getString(s);
    }
    void setWarningMSG(boolean activate) {
        if (activate) {}
    }

    void checktargetAngka(String empt) {
        if (!empt.isEmpty()) {
            if (!(Integer.valueOf(empt) > MAX_LENGT)) {
                binding.viewCounter.setCounterMaxLength(Integer.valueOf(empt));
                binding.viewCounter.setErrorEnabled(false);

            } else {
                binding.viewCounter.setErrorEnabled(true);
                binding.inputTergetAngka.setError(
                        getResources().getString(R.string.warning_lenght));
                binding.viewCounter.setError(getResources().getString(R.string.warning_lenght));
            }
        }
    }

    void ClearAllDictionary() {
        dictionary.clearAllDictionary();
        textitem.clear();
        adapter.notifyDataSetChanged();
    }

    void addData(TextItems item) {
        textitem.clear();

        dictionary.add(item);

        textitem.addAll(dictionary.getListItem());
        adapter.notifyDataSetChanged();
    }

    void ExportDictionary() {}

    void ImportDictionary() {}

    @Override
    protected void onStart() {
        check();
        super.onStart();
        // TODO: Implement this method
    }

    void check() {
        if (!dictionary.isMyInputMethodEnabled()) {
            new Dialogs(this);
        }
    }
    void pesan(String s){
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }
}
