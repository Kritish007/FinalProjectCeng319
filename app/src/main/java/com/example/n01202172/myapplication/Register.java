package com.example.n01202172.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button register;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        message = findViewById(R.id.registerMessage);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
        String chkUser = username.getText().toString();
                if (chkUser.trim().equals("")) {
                    Toast.makeText(Register.this, "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }
                String chkPass = password.getText().toString();
                if (chkPass.trim().equals("")) {
                    Toast.makeText(Register.this, "Please enter a Password", Toast.LENGTH_LONG).show();
                    return;
                }
                    if (!String.valueOf(confirmPassword.getText().toString()).equals(String.valueOf(password.getText().toString()))) {
                        Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        createNewUser(String.valueOf(username.getText()), String.valueOf(password.getText()));
                        Toast.makeText(Register.this, "Account is being Registered", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    private void createNewUser(String email, String password){
        // TODO: Create new users on Firebase.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MapleLeaf", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            message.setText("New user[ "+ user.getEmail() + "] is now registered");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            onBackPressed();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MapleLeaf", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Create new user failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

