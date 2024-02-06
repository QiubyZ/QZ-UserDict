package qz.userdictionary.ViewModel;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
import qz.userdictionary.ViewModel.mAdpView;
import qz.userdictionary.databinding.CostumViewTextitemBinding;

public class mAdpView extends RecyclerView.Adapter<mAdpView.VH> {

    List<TextItems> items;
    private UserDictOb userdicob;
    
    public mAdpView(List<TextItems> items, Handler handler) {
        this.items = items;
        userdicob = new UserDictOb(handler);
    }

    class VH extends RecyclerView.ViewHolder {
        CostumViewTextitemBinding bind;

        public VH(CostumViewTextitemBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(mAdpView.VH holder, int posisi) {
        TextItems item = items.get(posisi);
        
        UserDictionaryHelper dict = new UserDictionaryHelper(holder.bind.getRoot().getContext());
        dict.setItem(item);

        holder.bind.shortcut.setText(dict.getItem().getShort());
        holder.bind.freq.setText(String.valueOf(dict.getItem().getFrek()));
        holder.bind.textitem.setText(dict.getItem().getText());
        
        holder.bind.delete.setOnClickListener(
                (v) -> {
                    dict.clearSelectItem(dict.getItem().get_id());
                    items.remove(posisi);
                    notifyItemRemoved(posisi);
                    notifyItemRangeChanged(posisi, items.size());
                    //dict.manager().restartInput(holder.bind.getRoot());
                    // notifyDataSetChanged();
                });
        holder.bind
                .getRoot()
                .setOnClickListener(
                        (v) -> {
                            Toast.makeText(
                                            v.getContext(),
                                            String.valueOf(posisi),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        });

//        dict.getAppid()
//                .getContentResolver()
//                .registerContentObserver(UserDictionary.CONTENT_URI, true, userdicob);
        }

    @Override
    public qz.userdictionary.ViewModel.mAdpView.VH onCreateViewHolder(ViewGroup arg0, int arg1) {
        return new VH(
                CostumViewTextitemBinding.inflate(
                        LayoutInflater.from(arg0.getContext()), arg0, false));
    }

    private class UserDictOb extends ContentObserver {
        Handler handler;
        public UserDictOb(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean arg0, Uri arg1) {
            super.onChange(arg0, arg1);
            // notifyDataSetChanged();
            // TODO: Implement this method
        }
    }
}
