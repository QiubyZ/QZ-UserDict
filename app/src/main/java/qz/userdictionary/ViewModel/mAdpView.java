package qz.userdictionary.ViewModel;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.ViewModel.mAdpView;
import qz.userdictionary.databinding.CostumViewTextitemBinding;

public class mAdpView extends RecyclerView.Adapter<mAdpView.VH> {

    List<TextItems> items;

    public mAdpView(List<TextItems> items) {
        this.items = items;
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
    public void onBindViewHolder(mAdpView.VH holder, int arg1) {
        TextItems item = items.get(arg1);
        
        holder.bind.textitem.setText(item.getText());
        holder.bind.freq.setText(String.valueOf(item.getFrek()));
        
    }

    @Override
    public qz.userdictionary.ViewModel.mAdpView.VH onCreateViewHolder(ViewGroup arg0, int arg1) {
        return new VH(CostumViewTextitemBinding.inflate(LayoutInflater.from(arg0.getContext()), arg0, false));
    }
}
