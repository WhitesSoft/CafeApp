package com.darksoft.kaife_cataapp.ui.sign_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.darksoft.kaife_cataapp.databinding.ActivitySignUpBinding;
import com.darksoft.kaife_cataapp.ui.sign_in.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        buttons();
    }

    private void buttons() {
        binding.btnSignUp.setOnClickListener(v -> {

            binding.loading.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.INVISIBLE);

            String email = binding.fieldEmail.getText().toString();
            String password = binding.fieldPassword.getText().toString();

            if (!email.equals("")  && !password.equals("")){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                goLoginScreen();
                            } else {
                                Toast.makeText(SignUpActivity.this, "No se pudo registrar la cuenta", Toast.LENGTH_SHORT).show();
                                binding.loading.setVisibility(View.INVISIBLE);
                                binding.btnSignUp.setVisibility(View.VISIBLE);
                            }
                        });
            }else{
                binding.loading.setVisibility(View.INVISIBLE);
                binding.btnSignUp.setVisibility(View.VISIBLE);
            }


        });
    }

    private void goLoginScreen() {
        Toast.makeText(SignUpActivity.this, "Cuenta registrada", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

        finish();
    }
}