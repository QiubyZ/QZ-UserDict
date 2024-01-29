package qz.charlenght;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import qz.charlenght.Model.TextItems;
import qz.charlenght.ViewModel.mAdpView;
import qz.charlenght.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<TextItems> textitem;
    mAdpView adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        textitem = new ArrayList<TextItems>();
//        textitem.add(new TextItems("Meki ayam", 10));
//        textitem.add(new TextItems("Meki ayam", 10));
//        
        adapter = new mAdpView(textitem);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        
        String TargetAngka = binding.tergetAngka.getText().toString();
        if(binding.tergetAngka.getText().toString().length() < -1){
            binding.tergetAngka.setText(45);
        }
        
        binding.checkleng.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence arg0, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence text, int start, int before, int count) {
                        int hut = text.length();
                        String hit = String.valueOf(hut);
                        binding.panjang.setText(hit);
                    
                        if (hut > Integer.valueOf(TargetAngka)) {
                            binding.panjang.setTextColor(Color.RED);
                        }else{
                            binding.panjang.setTextColor(Color.BLACK);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
