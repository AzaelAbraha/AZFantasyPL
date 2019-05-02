package com.example.azfantasypl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignUpActivity extends AppCompatActivity {

    final int NUM_USERS = 20;

    Context mContext = this;

    private Button signupButton;
    private EditText emailText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText password2Text;
    private DatabaseReference mDatabase;

    String dbUsername[] = new String[NUM_USERS];
    String dbEmail[] = new String[NUM_USERS];
    int count = 0;

    String mUsername;
    String mEmail;
    String mPassword;
    String mPassword2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        signupButton = findViewById(R.id.bt_createAcc);
        emailText = findViewById(R.id.et_newEmailAddress);
        usernameText = findViewById(R.id.et_newUsername);
        passwordText = findViewById(R.id.et_newPassword);
        password2Text = findViewById(R.id.et_PasswordConfirm);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    count = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        dbUsername[count] = document.getString("username");
                        dbEmail[count] = document.getString("email");
                        count++;
                    }
                }else{
                    Toast.makeText(getApplicationContext(),("ERROR!! CAN NOT ACCESS DATABASE."),Toast.LENGTH_LONG).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = emailText.getText().toString();
                mUsername = usernameText.getText().toString();
                mPassword = passwordText.getText().toString();
                mPassword2 = password2Text.getText().toString();
                /**/

//                ValueEventListener userListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        User user = dataSnapshot.getValue(User.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                }
//                mDatabase.addListenerForSingleValueEvent();

                if(checkForError(mUsername,mEmail,mPassword,mPassword2)) {
                    WriteNewUser(mUsername, mEmail, mPassword);
                    Intent intent = new Intent(mContext, SelectPlayersActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void WriteNewUser(String username, String email, String password){
        User newUser = new User(username, email, password,0);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("USER_KEY", username);
        preferencesEditor.apply();

        db.collection("users").document(username).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"User added to the database.",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Unable to add user to the database.",Toast.LENGTH_LONG).show();
                }
        });
    }

    public boolean checkForError(String username, String email, String password, String password2){

        for(int i=0; i<count; i++){
            if(username.equals(dbUsername[i])){    // username exists
                Toast.makeText(getApplicationContext(),("This username is already taken. Please choose another username."),Toast.LENGTH_LONG).show();
                return false;
            }
            if(email.equals(dbEmail[i])){  // email exists
                Toast.makeText(getApplicationContext(),("An account already exists with this email. Please use another email address."),Toast.LENGTH_LONG).show();
                return false;
            }

        }
        if (email.equals("")) {  // email is empty
            Toast.makeText(getApplicationContext(),("Please enter an email address."),Toast.LENGTH_LONG).show();
            return false;
        }
        if (username.equals("")) {  // username is empty
            Toast.makeText(getApplicationContext(),("Please enter a username."),Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.equals("")) {  // password is empty
            Toast.makeText(getApplicationContext(),("Please enter a password."),Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(password2)) { // passwords do not match
            Toast.makeText(getApplicationContext(), ("passwords do not match!!"), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
}
