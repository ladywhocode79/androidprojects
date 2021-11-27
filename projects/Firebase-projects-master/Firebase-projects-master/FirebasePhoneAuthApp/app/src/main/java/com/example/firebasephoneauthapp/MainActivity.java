package com.example.firebasephoneauthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText edt_phone_no,edt_verification_code;
    Button bt_send_verification_code,bt_login;
    String codeSent;

    private static final String TAG = "PhoneAuthActivity";


    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        edt_phone_no = findViewById(R.id.edt_phone_no);
        edt_verification_code = findViewById(R.id.edt_code);

        bt_send_verification_code = findViewById(R.id.bt_send_code);
        bt_login = findViewById(R.id.bt_login);

        bt_send_verification_code.setOnClickListener(this);
        bt_login.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(), HomePage2.class);
                    startActivity(intent);
                    finish();
                }
            }


        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.bt_send_code:
                sendVerificationCode();

            case R.id.bt_login:
                verifyCode();


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }




    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void sendVerificationCode() {
        String phoneNumber = edt_phone_no.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }// OnVerificationStateChangedCallbacks


        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(getApplicationContext(),"Quota Exceeded for the number",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeSent = s;
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Toast.makeText(getApplicationContext(),"Verification code sent successfully!",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCodeSent:" + codeSent);
            }


        };

    private void verifyCode()
    {
        String code = edt_verification_code.getText().toString();
        try {
            // [START verify_with_code]
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //FirebaseUser user = task.getResult().getUser();
                            Intent i  = new Intent(getApplicationContext(),HomePage2.class);
                            startActivity(i);


                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(getApplicationContext(),"Incorrect Verification Code",Toast.LENGTH_SHORT).show();
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]

                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]
}
