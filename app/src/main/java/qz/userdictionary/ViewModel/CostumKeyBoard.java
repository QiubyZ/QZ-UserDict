package qz.userdictionary.ViewModel;
import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import qz.userdictionary.R;
import android.view.inputmethod.InputBinding;
import qz.userdictionary.databinding.CostumInputViewBinding;
public class CostumKeyBoard extends InputMethodService{
    @Override
    public View onCreateInputView() {
        CostumInputViewBinding binding = CostumInputViewBinding.inflate(LayoutInflater.from(getApplicationContext()));
        return binding.getRoot();
    }
    @Override
    public void onDisplayCompletions(CompletionInfo[] arg0) {
        super.onDisplayCompletions(arg0);
        // TODO: Implement this method
    }
}
