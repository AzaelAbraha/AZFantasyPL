package com.example.azfantasypl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPlayersActivity extends AppCompatActivity {

    final int PLAYER_SIZE = 20;
    List<Player> playerList;
    List<Player> selectedPlayersList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context mContext = this;

    String username;
    String playerId[] = new String[PLAYER_SIZE];
    String firstName[] = new String[PLAYER_SIZE];
    String lastName[] = new String[PLAYER_SIZE];
    String team[] = new String[PLAYER_SIZE];
    String position[] = new String[PLAYER_SIZE];
    long points[] = new long [PLAYER_SIZE];
    int imageRes[] = new int [PLAYER_SIZE];
    CheckBox mCheckBox[] = new CheckBox[PLAYER_SIZE];
    int count = 0;

    RecyclerView myRecyvlerView;
    PlayerListRecyclerViewAdapter myAdapter;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        playerList = new ArrayList<>();
        selectedPlayersList = new ArrayList<>();

        username = mPreferences.getString("USER_KEY","");

//        playerList.add(new Player("Sergio","Aguero","Manchester City", "FWD", R.drawable.icon_aguero,19));
//        playerList.add(new Player("Paul","Pogba","Manchester United", "MID", R.drawable.icon_pogba,15));
//        playerList.add(new Player("Mo","Salah","Liverpool", "FWD", R.drawable.icon_salah,13));
//        playerList.add(new Player("David","De Gea","Manchester United", "GK", R.drawable.icon_de_gea,11));
//        playerList.add(new Player("Eden","Hazard","Chelsea", "MID", R.drawable.icon_hazard,17));

//        playerList.add(new Player("Kevin","De Bruyne","Manchester City", "MID", R.drawable.icon_de_bruyne,5));
//        playerList.add(new Player("Harry","Kane","Tottenham", "FWD", R.drawable.icon_kane,17));
//        playerList.add(new Player("P.E","Aubameyang","Arsenal", "FWD", R.drawable.icon_aubameyang,14));

//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));

        db.collection("players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    count = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        playerId[count] = document.getId();
                        firstName[count] = document.getString("first_name");
                        lastName[count] = document.getString("last_name");
                        team[count] = document.getString("team");
                        position[count] = document.getString("position");
                        points[count] = document.getLong("points");


                        imageRes[count] = mContext.getResources().getIdentifier("icon_" + playerId[count], "drawable", mContext.getPackageName());

                        playerList.add(new Player(playerId[count],firstName[count],lastName[count],team[count],position[count], imageRes[count], points[count]));
                        Log.d("Player ID: ","\n" + document.getId() + "\n\tFirst: " + firstName[count] + "\n\tLast: " + lastName[count]);
                        count++;
                    }
                    Log.d("Count: ","" + count);
                    myRecyvlerView = (RecyclerView) findViewById(R.id.recyclerview_players);
                    myAdapter = new PlayerListRecyclerViewAdapter(mContext,playerList);
                    myRecyvlerView.setLayoutManager(new GridLayoutManager(mContext,1));
                    myRecyvlerView.setAdapter(myAdapter);
//                    populateList(count);
                }else{
                    Toast.makeText(getApplicationContext(),("Error loading players from database!"),Toast.LENGTH_LONG).show();
                }
            }
        });

//        Log.d("Count: ","count is: " + count);
//        for(int i = 0; i < count; i++){
//            playerList.add(new Player(firstName[i],lastName[i],"TEAM", "POS", R.drawable.icon_aguero,points[i]));
//            Log.d("Player Added: ","" + firstName[count] + " " + lastName[count]);
//        }

//        RecyclerView myRecyvlerView = (RecyclerView) findViewById(R.id.recyclerview_players);
//        PlayerListRecyclerViewAdapter myAdapter = new PlayerListRecyclerViewAdapter(this,playerList);
//        myRecyvlerView.setLayoutManager(new GridLayoutManager(this,1));
//        myRecyvlerView.setAdapter(myAdapter);
    }

    public void onApplyClicked(View view){
        int numSelected = myAdapter.mSelectedPlayersList.size();
        String Display = "";
        if(numSelected == 5) {
            Map<String, Object> team = new HashMap<>();
            Map<String, Object> InitialPts = new HashMap<>();
            Display = "";
            for (Player p : myAdapter.mSelectedPlayersList) {
                Display = Display + p.getfName() + " " + p.getlName() + "\n";
                Toast.makeText(getApplicationContext(), Display, Toast.LENGTH_LONG).show();
            }

            myAdapter.mSelectedPlayersList.get(0).getId();
            team.put("player1", myAdapter.mSelectedPlayersList.get(0).getId());
            team.put("player2", myAdapter.mSelectedPlayersList.get(1).getId());
            team.put("player3", myAdapter.mSelectedPlayersList.get(2).getId());
            team.put("player4", myAdapter.mSelectedPlayersList.get(3).getId());
            team.put("player5", myAdapter.mSelectedPlayersList.get(4).getId());
            InitialPts.put("player1_initial", myAdapter.mSelectedPlayersList.get(0).getPoints());
            InitialPts.put("player2_initial", myAdapter.mSelectedPlayersList.get(1).getPoints());
            InitialPts.put("player3_initial", myAdapter.mSelectedPlayersList.get(2).getPoints());
            InitialPts.put("player4_initial", myAdapter.mSelectedPlayersList.get(3).getPoints());
            InitialPts.put("player5_initial", myAdapter.mSelectedPlayersList.get(4).getPoints());
            db.collection("users").document(username).update(team).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"failed to save selected players.",Toast.LENGTH_LONG).show();
                }
            });
            db.collection("users").document(username).update(InitialPts).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"failed to save initial player points.",Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);

        }else{
            if (numSelected < 5) {
                Display = ("You have only selected " + numSelected + " players. Please select 5.");
                Toast.makeText(getApplicationContext(), Display, Toast.LENGTH_LONG).show();
            }
            if (numSelected > 5) {
                Display = ("You have selected " + numSelected + " players. Please select only 5.");
                Toast.makeText(getApplicationContext(), Display, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void populateList(int count){

        playerList = new ArrayList<>();

        for(int i = 0; i < count; i++){
            playerList.add(new Player(playerId[i], firstName[i],lastName[i],team[i], position[i], R.drawable.icon_player,points[i]));
        }

        RecyclerView myRecyvlerView = (RecyclerView) findViewById(R.id.recyclerview_players);
        PlayerListRecyclerViewAdapter myAdapter = new PlayerListRecyclerViewAdapter(this,playerList);
        myRecyvlerView.setLayoutManager(new GridLayoutManager(this,1));
        myRecyvlerView.setAdapter(myAdapter);
    }
}
