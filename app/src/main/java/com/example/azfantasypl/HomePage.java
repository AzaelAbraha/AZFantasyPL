package com.example.azfantasypl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    String username;
    private HomeFragment homeFragment;
    private MyTeamFragment myteamFragment;

    private TextView mTextMessage;
    private TextView points;

    List<Player> playerList;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle extras = getIntent().getExtras();

        homeFragment = new HomeFragment();
        myteamFragment = new MyTeamFragment();

        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        switchFragment(homeFragment);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_Nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        playerList = new ArrayList<>();
//        playerList.add(new Player("Sergio","Aguero","Manchester City", "FWD", R.drawable.icon_aguero,19));
//        playerList.add(new Player("Kevin","De Bruyne","Manchester City", "MID", R.drawable.icon_de_bruyne,5));
//        playerList.add(new Player("Paul","Pogba","Manchester United", "MID", R.drawable.icon_pogba,15));
//        playerList.add(new Player("Harry","Kane","Tottenham", "FWD", R.drawable.icon_kane,17));
//        playerList.add(new Player("P.E","Aubameyang","Arsenal", "FWD", R.drawable.icon_aubameyang,14));
//        playerList.add(new Player("Mo","Salah","Liverpool", "FWD", R.drawable.icon_salah,13));
//        playerList.add(new Player("David","De Gea","Manchester United", "GK", R.drawable.icon_de_gea,11));
//        playerList.add(new Player("Eden","Hazard","Chelsea", "MID", R.drawable.icon_hazard,17));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));
//        playerList.add(new Player("First","Last","Team", "Position", R.drawable.icon_player,0));

//        RecyclerView myRecyvlerView = (RecyclerView) findViewById(R.id.recyclerview_players);
//        PlayerListRecyclerViewAdapter myAdapter = new PlayerListRecyclerViewAdapter(this,playerList);
//        myRecyvlerView.setLayoutManager(new GridLayoutManager(this,1));
//        myRecyvlerView.setAdapter(myAdapter);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(homeFragment);
                    return true;
                case R.id.navigation_myteam:
                    switchFragment(myteamFragment);
                    return true;
                case R.id.navigation_logout:
                    logout();
                    return true;
            }
            return false;
        }
    };

    public void logout(){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("USER_KEY", "");
        preferencesEditor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }
}
