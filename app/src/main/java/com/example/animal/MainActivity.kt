package com.example.animal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animal.All_Pets
import com.example.animal.R

class MainActivity : AppCompatActivity() {
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        
        var loginBtn = findViewById<Button>(R.id.loginBtn)
        var emailEditText = findViewById<EditText>(R.id.email)
        var passwordEditText = findViewById<EditText>(R.id.password)
        
        
        
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            
            }
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (emailEditText.text.toString() != "hammadaslam308@gmail.com") {
                    emailEditText.setTextColor(Color.parseColor("#ffff0000"))
                    /*  emailEditText.setTextColor("#ff0000") */
                } else {
                    emailEditText.setTextColor(Color.parseColor("#ff000000"))
                }
            }
            
            override fun afterTextChanged(s: Editable?) {
            
            }
        })
        
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            
            }
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (passwordEditText.text.toString().length >= 6) {
                    passwordEditText.setTextColor(Color.parseColor("#000000"))
                    if (!emailEditText.text.toString().isEmpty() && !passwordEditText.text
                            .toString().isEmpty()
                    ) {
                        loginBtn.setEnabled(true);
                        loginBtn.setBackgroundColor(Color.parseColor("#FF00A5FF"))
                    }
                    
                    
                } else {
                    passwordEditText.setTextColor(Color.parseColor("#ffff0000"))
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundColor(Color.parseColor("#656565"))
                }
            }
            
            override fun afterTextChanged(s: Editable?) {
            
            }
        })
        
        loginBtn.setOnClickListener {
            
            
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()
            
            if (!email.isEmpty() && !password.isEmpty()) {
                if (email == "hammadaslam308@gmail.com" && password == "123456") {
                    /* Toast.makeText(this@MainActivity, "Hello! " + email, Toast.LENGTH_SHORT).show() */
                    val i = Intent(this, All_Pets::class.java)
                    /* i.putExtra("email", email) */
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Please check your credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
            
        }
    }
}