package qz.charlenght.ViewModel;
import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;
import android.view.View;
import qz.charlenght.R;
import android.view.inputmethod.InputBinding;
import qz.charlenght.databinding.CostumInputViewBinding;
public class CostumKeyBoard extends InputMethodService{
    
    
    
    @Override
    public View onCreateInputView() {
        CostumInputViewBinding binding = CostumInputViewBinding.inflate(LayoutInflater.from(getApplicationContext()));
        return binding.getRoot();
    }
    
}
