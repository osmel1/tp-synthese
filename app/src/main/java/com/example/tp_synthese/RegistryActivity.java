package com.example.tp_synthese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_synthese.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistryActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private TextView loginText;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;
    DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        loginText = findViewById(R.id.goToLogintxt);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // show the visibility of progress bar to show loading
                progressbar.setVisibility(View.VISIBLE);

                // Take the value of two edit texts in Strings
                String email, password;
                email = String.valueOf(emailTextView.getText());
                password = String.valueOf(passwordTextView.getText());

                // validations for input email and password
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter email!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter password!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // signin existing user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("info", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();
                                    refrence = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                                    User userRD = new User(userId, email, "default");
                                    refrence.setValue(userRD).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegistryActivity.this, "Account Created.",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegistryActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegistryActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(RegistryActivity.this,LoginActivity.class);
                        startActivity(intent1);
                    }
                }
        );
    }
}