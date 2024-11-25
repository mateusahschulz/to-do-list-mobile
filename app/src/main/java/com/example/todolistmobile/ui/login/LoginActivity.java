package com.example.todolistmobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistmobile.MainActivity;
import com.example.todolistmobile.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btnLogin;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa o ViewModel
        // loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(getApplication())).get(LoginViewModel.class);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Observa o estado do login
        loginViewModel.isLoggedIn().observe(this, isLoggedIn -> {
            if (isLoggedIn) {

                System.out.println("passei por aqui");
                // Se o usuário estiver logado, vai para a MainActivity
                navigateToMainActivity();
            }
        });

        loginViewModel.getLoginError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                // Exibe a mensagem de erro caso o login falhe
                Toast.makeText(LoginActivity.this, "Erro ao realizar login: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o clique no botão de login
        btnLogin.setOnClickListener(v -> {
            String email = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.login(email, password);
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
