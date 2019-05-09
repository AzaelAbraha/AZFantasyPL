package com.example.azfantasypl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayerListRecyclerViewAdapter extends RecyclerView.Adapter<PlayerListRecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    public List<Player> mPlayerList;
    public List<Player> mSelectedPlayersList;


    public PlayerListRecyclerViewAdapter(Context mContext, List<Player> mPlayerList) {
        this.mContext = mContext;
        this.mPlayerList = mPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_player, viewGroup,false);
        mSelectedPlayersList = new ArrayList<>();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        String fullName =  mPlayerList.get(i).getfName() + " " + mPlayerList.get(i).getlName();
        String playerInfo = mPlayerList.get(i).getTeam() + ", " + mPlayerList.get(i).getPosition();
        myViewHolder.mPlayerIcon.setImageResource(mPlayerList.get(i).getImage());
        myViewHolder.mPlayerName.setText(fullName);
        myViewHolder.mPlayerInfo.setText(playerInfo);


        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chkbx = (CheckBox) v;
                if(chkbx.isChecked()){
                    mSelectedPlayersList.add(mPlayerList.get(pos));
                    Toast.makeText(mContext.getApplicationContext(), "Player Selected", Toast.LENGTH_SHORT).show();
                }else{
                    mSelectedPlayersList.remove(mPlayerList.get(pos));
                    Toast.makeText(mContext.getApplicationContext(), "Player Unselected", Toast.LENGTH_SHORT).show();
                }

//                if(isSelected(mPlayerList.get(pos).getId())){
//                    chkbx.setChecked(true);
//                }else{
//                    chkbx.setChecked(false);
//
//                }

            }
        });

        if(isSelected(mPlayerList.get(i).getId())){
            myViewHolder.mCheckBox.setChecked(true);
        }else{
            myViewHolder.mCheckBox.setChecked(false);
        }

        myViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myViewHolder.mCheckBox.toggle();
            }
        });


    }

    public boolean isSelected(String playerID){
        boolean isSelected = false;
        for(int i =0; i<mSelectedPlayersList.size(); i++){
            if(mSelectedPlayersList.get(i).getId().equals(playerID)){
                isSelected = true;
            }
        }

        return isSelected;
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView mCardView;
        ImageView mPlayerIcon;
        TextView mPlayerName;
        TextView mPlayerInfo;
        CheckBox mCheckBox;

        ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerIcon = (ImageView) itemView.findViewById(R.id.iv_player);
            mPlayerName = (TextView) itemView.findViewById(R.id.tv_player);
            mPlayerInfo = (TextView) itemView.findViewById(R.id.tv_playerInfo);
            mCardView = (CardView) itemView.findViewById(R.id.cardview_player);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_player);

            mCheckBox.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemclick){
            this.itemClickListener = itemclick;
        }


        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
