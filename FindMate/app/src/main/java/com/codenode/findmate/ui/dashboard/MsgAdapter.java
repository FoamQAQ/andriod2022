package com.codenode.findmate.ui.dashboard;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codenode.findmate.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
   private List<Msg> chatList;

   public MsgAdapter(List<Msg> chatList) {
      this.chatList = chatList;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_item,viewGroup,false);
      ViewHolder viewHolder = new ViewHolder(view);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
      Msg chat = chatList.get(i);
      viewHolder.chatText.setText(chat.getContent());
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewHolder.chatText.getLayoutParams());
      params.gravity = Gravity.RIGHT;
      viewHolder.chatText.setLayoutParams(params);
   }

   @Override
   public int getItemCount() {
      return chatList.size();
   }

   static  class ViewHolder extends  RecyclerView.ViewHolder {

      TextView chatText;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);

         chatText = itemView.findViewById(R.id.chatText);
      }
   }
}