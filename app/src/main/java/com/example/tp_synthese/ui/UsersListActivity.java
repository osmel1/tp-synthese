package com.example.tp_synthese.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tp_synthese.LoginActivity;
import com.example.tp_synthese.R;
import com.example.tp_synthese.adpters.UserAdapter;
import com.example.tp_synthese.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {
    private RecyclerView userRecyclerView;
    private ArrayList<User> userList;
    private UserAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        mAuth = FirebaseAuth.getInstance();
        mDbRef = FirebaseDatabase.getInstance().getReference(); //getReference()

        userList = new ArrayList<>();
        adapter = new UserAdapter(this, userList);

        // use recyclerview
        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(adapter);

        // realtime database load
        mDbRef.child("Users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // get one user
                    User currentUser = postSnapshot.getValue(User.class);
                    // add user to list
                    if (mAuth.getCurrentUser() != null && !mAuth.getCurrentUser().getUid().equals(currentUser.getUserId())) {
                        userList.add(currentUser);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}