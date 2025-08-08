package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignupBinding
import io.github.jan.supabase.gotrue.gotrue

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(Supabase.client.gotrue))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(email, password)
            } else {
                Toast.makeText(this, "メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToLoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finish signup activity to prevent going back to it
        }

        // Keep social buttons for UI, but they are not functional
        binding.appleSignupButton.setOnClickListener {
            Toast.makeText(this, "Appleでのサインアップは現在実装中です", Toast.LENGTH_SHORT).show()
        }
        binding.lineSignupButton.setOnClickListener {
            Toast.makeText(this, "LINEでのサインアップは現在実装中です", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.authResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "サインアップ成功。ログインしてください。", Toast.LENGTH_LONG).show()
                // Navigate to login screen after successful signup
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "サインアップに失敗しました", Toast.LENGTH_SHORT).show()
            }
        }
    }
}