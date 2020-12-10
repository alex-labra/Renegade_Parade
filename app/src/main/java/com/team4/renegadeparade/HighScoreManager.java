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
    Written by: Nathan
    Tested by: Rey, Nathan, Alex, and Zayn
    Debugged by: Nathan
 */

public class HighScoreManager
{
    private final UUID userID;
    private final FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ValueEventListener listener;
    private List<Integer> highscores;

    //default constructor
    public HighScoreManager()
    {
        //initializes variables and begins authentication process
        highscores = new ArrayList<Integer>();
        userID = new DeviceUuidFactory(MainActivity.getInstance()).getDeviceUuid();
        mAuth = FirebaseAuth.getInstance();
        authenticate();
    }

    public int getHighScore()
    {
        //if there are no high scores, return 0. Otherwise return the top high score.
        if (highscores.isEmpty())
            return 0;
        return highscores.get(0);
    }

    public List<Integer> getTopScores(int amount)
    {
        List<Integer> list = new ArrayList<Integer>();
        //Loops through the high scores and returns the number of scores equal to the amount argument.
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

    //Set high score to position in list and update server to reflect changes
    public void setHighScore(int position, int newScore)
    {
        highscores.add(position, newScore);
        if (databaseReference != null)
            databaseReference.setValue(highscores);
    }

    //Adds high score to list, returns false if the score wasn't an improvement over previous scores.
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

    //Authenticate with server backend to allow for communication between the client and server.
    private void authenticate()
    {
        //Checks if the firebase auth variable initialized correctly
        if (mAuth != null)
        {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(MainActivity.getInstance(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Connection to the server was successful
                                //The message was printed to the log to let me know.
                                System.out.println("Successfully connected to server!");
                                user = mAuth.getCurrentUser();
                                finishInitializing();
                            } else {
                                //Connection to the server was not successful
                                //The message was printed to the log to let me know.
                                System.out.println("Connection to server failed!");
                            }
                        }
                    });
        }
    }
    //Setup connection to the server database
    private void finishInitializing()
    {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users").child(userID.toString()).child("High Scores");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Read data from server
                System.out.println("Reading high score from server.");
                if (dataSnapshot.getValue() != null)
                {
                    //Data actually exists
                    //The returned data type by default is an object so I had to use a generic type indicator to return the data as a list of integers.
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
        //Add listener to read data from database
        databaseReference.addListenerForSingleValueEvent(listener);
    }

    //Method to remove the data listener. I don't think it was necessary since I only listen for a single value instead of continuously.
    public void removeListener()
    {
        if (listener != null)
            databaseReference.removeEventListener(listener);
    }
}
