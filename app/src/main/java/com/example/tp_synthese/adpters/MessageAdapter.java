package com.example.tp_synthese.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_synthese.R;
import com.example.tp_synthese.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int ITEM_RECEIVE = 1;
    private static final int ITEM_SENT = 2;

    private final Context context;
    private final ArrayList<Message> messageList;

    public MessageAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_RECEIVE) {
            // Inflate receive
            View view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false);
            return new ReceiveViewHolder(view);
        } else {
            // Inflate sent
            View view = LayoutInflater.from(context).inflate(R.layout.sent, parent, false);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);

        if (holder instanceof SentViewHolder) {
            // Do stuff for sent view holder
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.sentMessage.setText(currentMessage.getMessage());
        } else if (holder instanceof ReceiveViewHolder) {
            // Do stuff for receive view holder
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            viewHolder.receiveMessage.setText(currentMessage.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currentMessage.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class SentViewHolder extends ViewHolder {
        TextView sentMessage;

        SentViewHolder(View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.txt_sent_message);
        }
    }

    static class ReceiveViewHolder extends ViewHolder {
        TextView receiveMessage;

        ReceiveViewHolder(View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
        }
    }
}
