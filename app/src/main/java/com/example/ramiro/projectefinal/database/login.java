package com.example.ramiro.projectefinal.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.activities.MainActivity;
import com.example.ramiro.projectefinal.activities.memory;
import com.example.ramiro.projectefinal.activities.perfil;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.Serializable;

public class login extends AppCompatActivity implements Serializable {


    String photo;

    private MyDataBaseHelper myDataBaseHelper;

    private EditText editText1,editText2;
    private String PREFS_NAME = "principal";
    private String PREFS_NAME2 = "memory";
    SignInButton signbutton;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signbutton = (SignInButton) findViewById(R.id.sign_in_button);

        initFirebaseAuth();

        initGoogleLogin();

        Button l = (Button) findViewById(R.id.button_login);
        Button s = (Button) findViewById(R.id.button_signin);

        editText1 = (EditText) findViewById(R.id.editText_usuario);
        editText2 = (EditText) findViewById(R.id.editText_password);

        myDataBaseHelper = MyDataBaseHelper.getInstance(this);



        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = editText1.getText().toString();
                String cont = editText2.getText().toString();
                String contra = myDataBaseHelper.queryRow1(us);
                if (contra == null) {
                    Intent i = new Intent(login.this,login_fail.class);
                    startActivity(i);
                }
                else {
                    if (!contra.equals(cont)) {
                        Cursor c = myDataBaseHelper.queryTable3(us);
                        if (c.moveToFirst()) {
                            do {
                                photo = c.getString(c.getColumnIndex(MyDataBaseContract.Table3.COLUMN_PHOTO));
                            } while (c.moveToNext());
                        }
                        myDataBaseHelper.updateRow3(us,"CONTRASEÑA INCORRECTA",photo);
                        Toast.makeText(getApplicationContext(),"CONTRASEÑA INCORRECTA",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("myBoolean", true);
                        editor.apply();
                        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME2,0);
                        SharedPreferences.Editor editor2 = settings2.edit();
                        editor2.putString("myString",us);
                        editor2.apply();
                        Intent i = new Intent(login.this, perfil.class);
                        //i.putExtra("usuari",us);
                        startActivity(i);
                        finish();
                    }

                }

            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,signin.class);
                startActivity(i);
                //finish();
            }
        });


        signbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    updateUI();
                }

            }
        };

    }


    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                        }
                    }
                });
    }


    private void updateUI(){
        FirebaseUser u= mAuth.getCurrentUser();
        String name = u.getDisplayName();
        String correu = u.getEmail();
        String photo = u.getPhotoUrl().toString();
        myDataBaseHelper.createRow2("3",name,"NO SCORED");
        myDataBaseHelper.createRow1(name,name,"",correu);
        myDataBaseHelper.createRow3(name,"NO LAST NOTIFICATION",photo);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("myBoolean", true);
        editor.apply();
        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME2,0);
        SharedPreferences.Editor editor2 = settings2.edit();
        editor2.putString("myString",name);
        editor2.apply();
        Toast.makeText(getApplicationContext(),"USUARIO REGISTRADO",Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        Intent i = new Intent(this,perfil.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myDataBaseHelper.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
