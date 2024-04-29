package com.example.tp_synthese.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tp_synthese.R;
import com.example.tp_synthese.adpters.MessageAdapter;
import com.example.tp_synthese.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private EditText messageBox;
    private ImageView sendButton;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;
    private DatabaseReference mDbRef;

    private String receiverRoom;
    private String senderRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String receiverUid = intent.getStringExtra("uid");

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDbRef = FirebaseDatabase.getInstance().getReference();

        senderRoom = receiverUid + senderUid;
        receiverRoom = senderUid + receiverUid;

        getSupportActionBar().setTitle(name);

        chatRecyclerView = findViewById(R.id.charRecyclerView);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sentButton);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        mDbRef.child("chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageList.clear();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Message message = postSnapshot.getValue(Message.class);
                            messageList.add(message);
                        }
                        messageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        sendButton.setOnClickListener(view -> {
            String message = messageBox.getText().toString();
            Message messageObject = new Message(message, senderUid);

            mDbRef.child("chats").child(senderRoom).child("messages").push()
                    .setValue(messageObject)
                    .addOnSuccessListener(aVoid ->
                            mDbRef.child("chats").child(receiverRoom).child("messages").push()
                                    .setValue(messageObject)
                    );

            messageBox.setText("");
        });
    }
}