package com.team4.renegadeparade;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class HighScoreManager
{
    private UUID userID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ValueEventListener listener;
    private int highScore;

    public HighScoreManager()
    {
        userID = new DeviceUuidFactory(MainActivity.getInstance()).getDeviceUuid();
        mAuth = FirebaseAuth.getInstance();
        authenticate();
    }

    public int getHighScore()
    {
        return highScore;
    }

    public void setHighScore(int newScore)
    {
        highScore = newScore;
        if (databaseReference != null)
            databaseReference.setValue(highScore);
    }

    public boolean checkHighScore(int newScore)
    {
        if (newScore > highScore)
        {
            setHighScore(newScore);
            return true;
        }
        return false;
    }

    private void authenticate()
    {
        if (mAuth != null)
        {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(MainActivity.getInstance(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Successfully connected to server!");
                                user = mAuth.getCurrentUser();
                                finishInitializing();
                            } else {
                                System.out.println("Connection to server failed!");
                            }
                        }
                    });
        }
    }

    private void finishInitializing()
    {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users").child(userID.toString()).child("High Score");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get data from server
                System.out.println("Reading high score from server.");
                if (dataSnapshot.getValue() != null)
                {
                    highScore = dataSnapshot.getValue(Integer.class);
                }
                else
                {
                    setHighScore(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("Failed to read data from server!");
            }
        };
        databaseReference.addListenerForSingleValueEvent(listener);
    }

    public void removeListener()
    {
        if (listener != null)
            databaseReference.removeEventListener(listener);
    }
}
