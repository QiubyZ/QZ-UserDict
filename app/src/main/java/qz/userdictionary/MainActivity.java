package qz.userdictionary;

import android.os.Handler;
import android.os.Looper;
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
    final int plus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());

        textitem = new ArrayList<TextItems>();
        adapter = new mAdpView(textitem, new Handler(Looper.getMainLooper()));
        dictionary = new UserDictionaryHelper(this);
        
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        
        binding.keys.setText("coba");
        binding.checkleng.setText("makan ikan tapi nete");
        
        textitem.addAll(dictionary.getListItem());
        adapter.notifyDataSetChanged();
        
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
                    binding.viewCounter.setCounterMaxLength(Integer.valueOf(TargetAngka));
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });
        
        
        binding.add.setOnClickListener(
                (v) -> {
                    String kata = binding.checkleng.getText().toString();
                    String keys = binding.keys.getText().toString();
                    addData(new TextItems(kata,keys,"250"),String.valueOf(UserDictionary.Words.LOCALE_TYPE_ALL));
                });
        
        binding.clearAll.setOnClickListener((v) -> {
            ClearAllDictionary();
        });
        
    }
    void ClearAllDictionary(){
        dictionary.clearAllDictionary();
        textitem.clear();
        adapter.notifyDataSetChanged();
    }
    void addData(TextItems item, String LocalId){
        textitem.clear();
        dictionary.setItem(item);
        dictionary.applyToLocal(LocalId);
        textitem.addAll(dictionary.getListItem());
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart() {
        check();
        super.onStart();
        // TODO: Implement this method
    }

    void check() {
        if (dictionary.isMyInputMethodEnabled()) {
            Toast.makeText(this, "Allow", Toast.LENGTH_LONG).show();
        } else {
            new Dialogs(this);
        }
    }
}
