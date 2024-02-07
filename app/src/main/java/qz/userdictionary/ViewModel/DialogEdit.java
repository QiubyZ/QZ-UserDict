package qz.userdictionary.ViewModel;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
import qz.userdictionary.databinding.EditDilaogsBinding;
import qz.userdictionary.R;

public class DialogEdit extends AlertDialog.Builder implements DialogInterface.OnClickListener {
    EditDilaogsBinding editdialogbind;
    UserDictionaryHelper userdictionaryhelper;
    TextItems teksitem;

    public DialogEdit(Context ctx, TextItems Teksitem) {
        super(ctx);
        this.teksitem = Teksitem;
        ViewDialog();
    }

    void ViewDialog() {
        setCancelable(false);
        userdictionaryhelper = new UserDictionaryHelper(getContext());
        editdialogbind = EditDilaogsBinding.inflate(LayoutInflater.from(getContext()));
        setView(editdialogbind.getRoot());

        editdialogbind.viewEditKeys.setText(teksitem.getShort());
        editdialogbind.viewEditWords.setText(teksitem.getText());

        setPositiveButton(R.string.submit, this);
        setNegativeButton(R.string.cancel, this);

        show().create();
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        if (arg1 == -1) {
            String shortcut = editdialogbind.viewEditKeys.getText().toString();
            String word = editdialogbind.viewEditWords.getText().toString();
            
            teksitem.setShort(shortcut);
            teksitem.setText(word);
            
            userdictionaryhelper.update(teksitem);
        }
        arg0.dismiss();
    }
}
