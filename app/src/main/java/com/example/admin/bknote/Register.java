package com.example.admin.bknote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Admin on 4/10/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener{
    private Button idReg;
    private EditText idUser, idPw;
    private TextView idTv, idLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        idReg = (Button)findViewById(R.id.idReg);
        idUser = (EditText)findViewById(R.id.idUser);
        idPw = (EditText)findViewById(R.id.idPw);
        idLogin = (TextView) findViewById(R.id.idLogin);
        idReg.setOnClickListener(this);
        idLogin.setOnClickListener(this);
    }
    private void regUser()
    {
        String email = idUser.getText().toString().trim();
        String password = idPw.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Vui lòng nhập địa chỉ Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    @Override
    public void onClick(View v) {
        if(v == idReg)
        {
            regUser();
        }
        if (v == idLogin)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
