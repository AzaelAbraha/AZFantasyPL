package com.example.azfantasypl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int NUM_USERS = 20;
    String username = "";

    List<Player> playerList;
    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;

    String dbUsername[] = new String[NUM_USERS];
    String dbPassword[] = new String[NUM_USERS];
    int count = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = (EditText) findViewById(R.id.et_username);
        passwordInput = (EditText) findViewById(R.id.et_password);
        loginButton = (Button) findViewById(R.id.button_login);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        username = mPreferences.getString("USER_KEY","");

        if(!username.equals("")){
            Login(username);
        }

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    count = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        dbUsername[count] = document.getString("username");
                        dbPassword[count] = document.getString("password");
                        count++;
                    }
                }
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                boolean userExists = false;
                for (int i = 0; i < count; i++) {
                    if (username.equals(dbUsername[i])) {
                        userExists = true;
                        if (password.equals(dbPassword[i])) {
                            Login(username);
                        } else {
                            Toast.makeText(getApplicationContext(), ("Incorrect password."), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                if (!userExists) {
                    Toast.makeText(getApplicationContext(), ("User not found"), Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void Login (String username){

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("USER_KEY", username);
        preferencesEditor.apply();

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void onSignUpClicked (View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
