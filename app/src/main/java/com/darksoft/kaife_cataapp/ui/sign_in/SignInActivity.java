package com.darksoft.kaife_cataapp.ui.sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.darksoft.kaife_cataapp.MainActivity;
import com.darksoft.kaife_cataapp.databinding.ActivitySignInBinding;
import com.darksoft.kaife_cataapp.ui.sign_up.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SignInActivity extends AppCompatActivity {

    private static ActivitySignInBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        buttons();

    }

    private void buttons() {

        binding.btnSignIn.setOnClickListener(v -> {
            signIn();
        });

        binding.labelSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void signIn() {

        binding.btnSignIn.setVisibility(View.INVISIBLE);
        binding.loading.setVisibility(View.VISIBLE);

        String email = binding.fieldEmail.getText().toString();
        String password = binding.fieldPassword.getText().toString();

        if (!email.equals("") && !password.equals("")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                goMainScreen();
                            } else {
                                binding.btnSignIn.setVisibility(View.VISIBLE);
                                binding.loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }else{
            binding.btnSignIn.setVisibility(View.VISIBLE);
            binding.loading.setVisibility(View.INVISIBLE);
            Toast.makeText(SignInActivity.this, "Debe ingresar un email o un password", Toast.LENGTH_SHORT).show();
        }

    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUI(FirebaseUser user) {


    }
}