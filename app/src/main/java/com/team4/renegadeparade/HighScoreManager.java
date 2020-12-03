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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
    Class created by Nathan
 */

public class HighScoreManager
{
    private UUID userID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ValueEventListener listener;
    private List<Integer> highscores;


    public HighScoreManager()
    {
        highscores = new ArrayList<Integer>();
        userID = new DeviceUuidFactory(MainActivity.getInstance()).getDeviceUuid();
        mAuth = FirebaseAuth.getInstance();
        authenticate();
    }

    public int getHighScore()
    {
        if (highscores.isEmpty())
            return 0;
        return highscores.get(0);
    }

    public List<Integer> getTopScores(int amount)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i<highscores.size(); i++)
        {
            if (i < amount)
            {
                list.add(highscores.get(i));
            }
            else
                break;
        }
        return list;
    }

    public void setHighScore(int position, int newScore)
    {
        highscores.add(position, newScore);
        if (databaseReference != null)
            databaseReference.setValue(highscores);
    }

    public boolean addScoreToList(int newScore)
    {
        if (newScore == 0)
            return false;
        for (int i = 0; i<highscores.size(); i++)
        {
            if (highscores.get(i) < newScore)
            {
                setHighScore(i,newScore);
                return true;
            }
        }
        setHighScore(highscores.size(), newScore);
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
        databaseReference = database.getReference("users").child(userID.toString()).child("High Scores");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get data from server
                System.out.println("Reading high score from server.");
                if (dataSnapshot.getValue() != null)
                {
                    GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {};
                    highscores = dataSnapshot.getValue(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting data failed, log a message
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
