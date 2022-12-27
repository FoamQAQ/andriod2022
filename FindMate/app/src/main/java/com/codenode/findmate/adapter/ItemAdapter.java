package com.codenode.findmate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codenode.findmate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface ItemClickListener {
        void toggleAllow(Item  item);

    }

    private Context context;
    private List<Item> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public ItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item itemObj = datas.get(position);
        holder.item_title.setText(itemObj.getTitle());
        holder.item_des.setText(itemObj.getDes());
        holder.rootView.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.toggleAllow(itemObj);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public  void setData(List datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView item_title;
        TextView item_des;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            item_title = rootView.findViewById(R.id.item_title);
            item_des = rootView.findViewById(R.id.item_des);
        }
    }
}
