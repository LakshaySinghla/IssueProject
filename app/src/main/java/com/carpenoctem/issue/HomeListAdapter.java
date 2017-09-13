package com.carpenoctem.issue;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class HomeListAdapter extends RecyclerView.Adapter{

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, name, description, share, comment, user_name;
        ImageView img , up, down, user_img, close;
        LinearLayout comment_container;
        EditText writeComment;
        public ListViewHolder(View itemView) {
            super(itemView);

            title= (TextView) itemView.findViewById(R.id.title);
            name= (TextView) itemView.findViewById(R.id.name);
            date= (TextView) itemView.findViewById(R.id.date);
            description= (TextView) itemView.findViewById(R.id.description);
            share= (TextView) itemView.findViewById(R.id.share);
            comment = (TextView) itemView.findViewById(R.id.comment);
            img= (ImageView) itemView.findViewById(R.id.dp);
            user_img= (ImageView) itemView.findViewById(R.id.user_dp);
            close= (ImageView) itemView.findViewById(R.id.close);
            down= (ImageView) itemView.findViewById(R.id.downvote);
            up= (ImageView) itemView.findViewById(R.id.upvote);
            comment_container = (LinearLayout) itemView.findViewById(R.id.comment_container);
            writeComment = (EditText) itemView.findViewById(R.id.write_comment);

            writeComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        homeFragment.performComment( list.get(getAdapterPosition()-1).getId(),writeComment.getText().toString(), getAdapterPosition()-1 );
                        comment_container.setVisibility(View.GONE);
                        comment.setVisibility(View.VISIBLE);
                        writeComment.setText("");
                        return true;
                    }
                    return false;
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comment_container.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.GONE);
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writeComment.setText("");
                    comment_container.setVisibility(View.GONE);
                    comment.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView name,write;
        EditText title, description;
        ImageView img, close;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            write = (TextView) itemView.findViewById(R.id.write);
            title = (EditText) itemView.findViewById(R.id.complaint_title);
            description = (EditText) itemView.findViewById(R.id.complaint_description);
            img = (ImageView) itemView.findViewById(R.id.dp);
            close = (ImageView) itemView.findViewById(R.id.close);

            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    write.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    write.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);
                }
            });

            description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if(title.getText().length() <= 0){
                            Toast.makeText(homeFragment.getContext(),"Enter Some Title", Toast.LENGTH_LONG).show();
                        }
                        else if(description.getText().length() <= 0){
                            Toast.makeText(homeFragment.getContext(),"Enter Some Description", Toast.LENGTH_LONG).show();
                        }
                        else{
                            homeFragment.performComplaint( title.getText().toString(), description.getText().toString() );
                            write.setVisibility(View.VISIBLE);
                            title.setVisibility(View.GONE);
                            description.setVisibility(View.GONE);
                            close.setVisibility(View.GONE);
                            description.setText("");
                            title.setText("");
                        }
                        return true;
                    }
                    return false;
                }
            });

        }
    }

    int HView =0 , LView =1;
    HomeFragment homeFragment;
    ArrayList<ComplaintData> list;

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public void setList(ArrayList<ComplaintData> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_view, parent, false);
            return new HeaderViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_view, parent, false);
            return new ListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder)holder).name.setText( "Name " + getPreference("user_id") );
        }
        else{
            ((ListViewHolder)holder).title.setText(list.get(position-1).getTitle() );
            ((ListViewHolder)holder).date.setText("Date"+list.get(position-1).getDate() );
            ((ListViewHolder)holder).name.setText( "Name" + list.get(position-1).getUserId() );
            ((ListViewHolder)holder).description.setText(list.get(position-1).getDescription() );
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return HView;
        else return LView;
    }

    @Override
    public int getItemCount() {
        return (list.size()+1);
    }

    public String getPreference(String key ){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(homeFragment.getContext());
        return preferences.getString(key, null);
    }
}
