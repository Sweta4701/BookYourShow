package com.example.bookshow;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    private static final String TAG = "Signup";

    private EditText edtUsername, edtPassword, edtConfirmPassword;

    private Button btnSignIn, btnLogin;

    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        edtConfirmPassword = findViewById(R.id.repassword);
        btnSignIn = findViewById(R.id.btnsignup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName, password, confirmPassword;
//
                userName = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                confirmPassword = edtConfirmPassword.getText().toString();
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(Signup.this, "Please fill all the Details!", Toast.LENGTH_LONG).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        if (dbHelper.checkUsername(userName)) {
                            Toast.makeText(Signup.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Proceed Login
                        boolean signupSuccess = dbHelper.insertData(userName, password);
                        if (signupSuccess) {
                            Toast.makeText(Signup.this, "User SignUp Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onClick: else");
                            Toast.makeText(Signup.this, "User Signup Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "Password do not Match!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNext = new Intent(Signup.this, MainActivity.class);
                startActivity(iNext);
            }
        });

    }

    /*
     * Please presss tis combination    Ctrl + Alt + L   use to reformat the code
     * */
}