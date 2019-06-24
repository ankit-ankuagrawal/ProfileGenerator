package com.example.profilegenerator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.profilegenerator.R;
import com.example.profilegenerator.model.ProfileModel;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private List<ProfileModel> profileModelList;

    public ProfileAdapter(List<ProfileModel> profileModelList) {
        this.profileModelList = profileModelList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvUserName;
        private TextView tvPasscode;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.ivImage = (ImageView) itemView.findViewById(R.id.ivIamge);
            this.tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            this.tvPasscode = (TextView) itemView.findViewById(R.id.tvPasscode);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView tvUserName = holder.tvUserName;
        TextView tvPasscode = holder.tvPasscode;
        ImageView ivImage = holder.ivImage;

        tvUserName.setText(profileModelList.get(listPosition).getUserName());
        tvPasscode.setText(profileModelList.get(listPosition).getPasscode());
        ivImage.setImageBitmap(profileModelList.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return profileModelList.size();
    }
}
