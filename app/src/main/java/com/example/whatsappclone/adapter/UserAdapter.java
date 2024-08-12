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
import com.example.whatsappclone.model.Users;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<Users> mUsers;
    private boolean isActive;

    public UserAdapter(Context context, List<Users> mUsers,boolean isActive) {
        this.context = context;
        this.mUsers = mUsers;
        this.isActive=isActive;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Users user=mUsers.get(position);
        holder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.drawable.whatsapp);
        }else{
            Glide.with(context)
                    .load(user.getImageURL())
                    .into(holder.imageView);
        }

        //status check
        if (isActive){
            if (user.getStatus().equals("online")){
                holder.imageViewOn.setVisibility(View.VISIBLE);
                holder.imageViewOff.setVisibility(View.GONE);
            }else {
                holder.imageViewOn.setVisibility(View.GONE);
                holder.imageViewOff.setVisibility(View.VISIBLE);
            }
        }else {
            holder.imageViewOn.setVisibility(View.GONE);
            holder.imageViewOff.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private ImageView imageView;
        private ImageView imageViewOn;
        private ImageView imageViewOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.userNameTextView);
            imageView=itemView.findViewById(R.id.userImageView);
            imageViewOn=itemView.findViewById(R.id.statusImageOn);
            imageViewOff=itemView.findViewById(R.id.statusImageOff);
        }
    }
}
