package qz.userdictionary.ViewModel;
import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;
import android.view.View;
import qz.userdictionary.R;
import android.view.inputmethod.InputBinding;
import qz.userdictionary.databinding.CostumInputViewBinding;
public class CostumKeyBoard extends InputMethodService{
    @Override
    public View onCreateInputView() {
        CostumInputViewBinding binding = CostumInputViewBinding.inflate(LayoutInflater.from(getApplicationContext()));
        return binding.getRoot();
    }
}
