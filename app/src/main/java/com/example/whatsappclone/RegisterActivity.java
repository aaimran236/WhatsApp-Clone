package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsappclone.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameET,passwordET,emailET;
    private Button registerButton;

    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private TextView loginTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        nameET=findViewById(R.id.userEditText);
        passwordET=findViewById(R.id.passEditText);
        emailET=findViewById(R.id.emailEditText);
        registerButton=findViewById(R.id.buttonRegister);
        loginTextView=findViewById(R.id.login_text_view);

        //Firebase Auth
        auth=FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_text,email_text,password_text;
                user_text=nameET.getText().toString();
                email_text=emailET.getText().toString();
                password_text=passwordET.getText().toString();

                if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(email_text) || TextUtils.isEmpty(password_text)){
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    registerNow(user_text,email_text,password_text);
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerNow(final String username,String email,String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId=firebaseUser.getUid();

//                    Toast.makeText(RegisterActivity.this, "id="+userId, Toast.LENGTH_SHORT).show();

                    myRef=FirebaseDatabase.getInstance().getReference("MyUsers").child(userId);
                    String imageURL="default";
//                    HashMap<String ,String> hashMap=new HashMap<>();
//                    hashMap.put("id",userId);
//                    hashMap.put("username",username);
//                    hashMap.put(imageURL,"default");

                    Users user=new Users(userId,username,imageURL,"offline");
                    //opening the MainActivity after successful registration
                    myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent i=new Intent(RegisterActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Could not store user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}