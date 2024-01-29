package qz.charlenght;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import qz.charlenght.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int panjang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        
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
