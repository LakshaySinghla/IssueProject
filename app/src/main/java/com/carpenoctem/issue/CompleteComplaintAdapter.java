package com.carpenoctem.issue;

import android.support.v7.widget.RecyclerView;
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


/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class CompleteComplaintAdapter extends RecyclerView.Adapter {

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, date, detail;
        public ListViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.dp);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            detail = (TextView) itemView.findViewById(R.id.comment);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title, name, date, description, share, comment, userName;
        ImageView img, up, down ,close, userImg;
        LinearLayout ll;
        EditText writeComment;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            description = (TextView) itemView.findViewById(R.id.description);
            comment = (TextView) itemView.findViewById(R.id.comment);
            share = (TextView) itemView.findViewById(R.id.share);
            img = (ImageView) itemView.findViewById(R.id.dp);
            up = (ImageView) itemView.findViewById(R.id.upvote);
            down = (ImageView) itemView.findViewById(R.id.downvote);

            ll = (LinearLayout) itemView.findViewById(R.id.comment_container);
            close = (ImageView) itemView.findViewById(R.id.close);
            writeComment = (EditText) itemView.findViewById(R.id.write_comment);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userImg = (ImageView) itemView.findViewById(R.id.user_dp);

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.GONE);
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll.setVisibility(View.GONE);
                    comment.setVisibility(View.VISIBLE);
                    writeComment.setText("");
                }
            });

            writeComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if(writeComment.getText().length() == 0){
                            Toast.makeText(completeComplaintActivity,"Enter Some Description", Toast.LENGTH_LONG).show();
                        }
                        else{
                            completeComplaintActivity.performComment( data.getId(), writeComment.getText().toString() );
                            ll.setVisibility(View.GONE);
                            comment.setVisibility(View.VISIBLE);
                            writeComment.setText("");
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private ComplaintData data;
    private int HView = 0, LView = 1;
    private CompleteComplaintActivity completeComplaintActivity;

    void setComplaintData(ComplaintData data){
        this.data = data;
    }

    public void setCompleteComplaintActivity(CompleteComplaintActivity completeComplaintActivity) {
        this.completeComplaintActivity = completeComplaintActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == LView) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item_view, parent, false);
            return new ListViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_header_view, parent, false);
            return new HeaderViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).detail.setText(data.getList().get(position-1).getBody() );
            ((ListViewHolder) holder).date.setText(data.getList().get(position-1).getDate() );
            ((ListViewHolder) holder).name.setText( "Name " + data.getList().get(position-1).getUserId() );
        }
        else{
            ((HeaderViewHolder)holder).title.setText( data.getTitle() );
            ((HeaderViewHolder)holder).description.setText( data.getDescription() );
            ((HeaderViewHolder)holder).name.setText( "Name " + data.getUserId() );
            ((HeaderViewHolder)holder).userName.setText("Name " + data.getUserId());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HView;
        }
        else return LView;
    }

    @Override
    public int getItemCount() {
        return (data.getList().size()+1);
    }
}
