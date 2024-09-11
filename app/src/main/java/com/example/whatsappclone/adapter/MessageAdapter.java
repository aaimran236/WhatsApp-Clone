package com.example.whatsappclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.MessageActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.model.Chat;
import com.example.whatsappclone.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<Chat> mChat;
    private String imageURL;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private FirebaseUser fUser;

    public MessageAdapter(Context context, List<Chat> mChat, String imageURL) {
        this.context = context;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_right, parent, false);

        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_left, parent, false);
        }
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat=mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageURL.equals("default") | imageURL==null){
            holder.profileImage.setImageResource(R.drawable.whatsapp);
        }else {
            Glide.with(context)
                    .load(imageURL)
                    .into(holder.profileImage);
        }

        if (position== mChat.size()-1){
            if (chat.isIsseen()){
                holder.seen_text.setText("Seen");
            }else {
                holder.seen_text.setText("Delivered");
            }
        }else{
            holder.seen_text.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView show_message,seen_text;
        private ImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profileImage=itemView.findViewById(R.id.profile_image);
            seen_text=itemView.findViewById(R.id.text_seen_status);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}