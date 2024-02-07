package qz.userdictionary.ViewModel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import qz.userdictionary.R;

public class Dialogs extends AlertDialog.Builder implements DialogInterface.OnClickListener {
    public Dialogs(Context ctx) {
        super(ctx);
        Oncreate();
    }

    void Oncreate() {
        setTitle(R.string.dialog_title);
        setMessage(R.string.dialog_messages);
        setPositiveButton(R.string.dialog_goto, this);
        setNegativeButton(R.string.dialog_dismis, this);
        setCancelable(false);
        create().show();
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        switch (arg1) {
            case -1:
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                getContext().startActivity(intent);
                break;
            case -2:
                arg0.dismiss();
                break;
        }
    }
}
