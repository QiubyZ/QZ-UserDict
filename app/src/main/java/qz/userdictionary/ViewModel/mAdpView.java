package qz.userdictionary.ViewModel;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
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

        holder.bind.shortcut.setText(item.getShort());
        holder.bind.freq.setText(String.valueOf(item.getFrek()));
        holder.bind.textitem.setText(item.getText());
        
        holder.bind.delete.setOnClickListener(
                (v) -> {
                    dict.clearSelectItem(item.get_id());
                    items.remove(posisi);
                    notifyItemRemoved(posisi);
                    notifyItemRangeChanged(posisi, getItemCount());
                    dict.manager().restartInput(holder.bind.getRoot());
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
        
        holder.bind.edit.setOnClickListener((v) -> {
                new DialogEdit(v.getContext(), item);
                notifyItemChanged(posisi);
                notifyItemChanged(posisi, getItemCount());
        });

        dict.getAppid()
                .getContentResolver()
                .registerContentObserver(UserDictionary.CONTENT_URI, true, userdicob);
        
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
            notifyDataSetChanged();
            super.onChange(arg0, arg1);
            // TODO: Implement this method
        }
    }
}
