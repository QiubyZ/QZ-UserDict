package qz.charlenght;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import java.util.ArrayList;
import qz.charlenght.Model.TextItems;
import qz.charlenght.Model.UserDictionaryHelper;
import qz.charlenght.ViewModel.mAdpView;
import qz.charlenght.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<TextItems> textitem;
    private static final int PERMISSION_REQUEST_CODE = 123;
    mAdpView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        RequestPermission();

        textitem = new ArrayList<TextItems>();
        adapter = new mAdpView(textitem);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        binding.keys.setText("coba");
        binding.checkleng.setText("makan ikan tapi nete");

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
                    try {
                        UserDictionaryHelper dict = new UserDictionaryHelper(v.getContext(), teks);
                        dict.applyToLocal("in_ID");
                    } catch (Exception err) {
                        Toast.makeText(v.getContext(), err.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    void RequestPermission() {
        
        SnackbarOnDeniedPermissionListener dialogperm =
                SnackbarOnDeniedPermissionListener.Builder.with(binding.getRoot(), "Jejdndnd")
                        .withOpenSettingsButton("Settings")
                        .withCallback(
                                new Snackbar.Callback() {

                                    @Override
                                    public void onDismissed(Snackbar arg0, int arg1) {
                                        super.onDismissed(arg0, arg1);
                                    }

                                    @Override
                                    public void onShown(Snackbar arg0) {
//                                        arg0.setAction(arg0.getView().getId(), (v) -> {
//                                            Intent intent =
//                                                new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
//                                        startActivity(intent);
//                                        });
//                    
                                        super.onShown(arg0);
                                    }
                                })
                        .build();
        

        DexterBuilder build =
                Dexter.withContext(this)
                        .withPermission(Manifest.permission.BIND_INPUT_METHOD)
                        .withListener(dialogperm);
        build.check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
