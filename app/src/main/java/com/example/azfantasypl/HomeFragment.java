package com.example.azfantasypl;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String toString;

    final int PLAYER_SIZE = 100;
    String playerId[] = new String[PLAYER_SIZE];
    String firstName[] = new String[PLAYER_SIZE];
    String lastName[] = new String[PLAYER_SIZE];
    String team[] = new String[PLAYER_SIZE];
    String position[] = new String[PLAYER_SIZE];
    long points[] = new long [PLAYER_SIZE];
    private String player1ID = "p1";
    private String player2ID = "p2";
    private String player3ID = "p3";
    private String player4ID = "p4";
    private String player5ID = "p5";
    private long player1_initialPts;
    private long player2_initialPts;
    private long player3_initialPts;
    private long player4_initialPts;
    private long player5_initialPts;
    int count = 0;
    Context mContext;
    private TextView tv_teamName;
    private TextView tv_totalScore;
    private TextView tv_player1_name;
    private TextView tv_player2_name;
    private TextView tv_player3_name;
    private TextView tv_player4_name;
    private TextView tv_player5_name;
    private TextView tv_player1_pts;
    private TextView tv_player2_pts;
    private TextView tv_player3_pts;
    private TextView tv_player4_pts;
    private TextView tv_player5_pts;
    private String username;
    private long score = 0;
    private long initialScore = 0; // score before latest team update

    private long player1PtsEarned;
    private long player2PtsEarned;
    private long player3PtsEarned;
    private long player4PtsEarned;
    private long player5PtsEarned;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv_teamName = (TextView) view.findViewById(R.id.tv_teamName);
        tv_totalScore = (TextView) view.findViewById(R.id.tv_totalScore);
        tv_player1_name = (TextView) view.findViewById(R.id.tv_player1_name);
        tv_player2_name = (TextView) view.findViewById(R.id.tv_player2_name);
        tv_player3_name = (TextView) view.findViewById(R.id.tv_player3_name);
        tv_player4_name = (TextView) view.findViewById(R.id.tv_player4_name);
        tv_player5_name = (TextView) view.findViewById(R.id.tv_player5_name);
        tv_player1_pts = (TextView) view.findViewById(R.id.tv_player1_points);
        tv_player2_pts = (TextView) view.findViewById(R.id.tv_player2_points);
        tv_player3_pts = (TextView) view.findViewById(R.id.tv_player3_points);
        tv_player4_pts = (TextView) view.findViewById(R.id.tv_player4_points);
        tv_player5_pts = (TextView) view.findViewById(R.id.tv_player5_points);
        score = 0;
        toString = "" + score;
        tv_totalScore.setText(toString);
        mContext = view.getContext();

        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        username = mPreferences.getString("USER_KEY","");


        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    count = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
//                        playerId[count] = document.getId();
//                        firstName[count] = document.getString("first_name");
//                        lastName[count] = document.getString("last_name");
//                        points[count] = document.getLong("points");
                        if(document.getId().equals(username)) {
                            tv_teamName.setText(document.getString("team_name"));
                            player1ID = document.getString("player1");
                            player2ID = document.getString("player2");
                            player3ID = document.getString("player3");
                            player4ID = document.getString("player4");
                            player5ID = document.getString("player5");
                            player1_initialPts = document.getLong("player1_initial");
                            player2_initialPts = document.getLong("player2_initial");
                            player3_initialPts = document.getLong("player3_initial");
                            player4_initialPts = document.getLong("player4_initial");
                            player5_initialPts = document.getLong("player5_initial");
//                            score = document.getLong("score");
//                            initialScore = mPreferences.getLong("INITIAL_SCORE",document.getLong("score"));
                            initialScore = document.getLong("prev_score");
                        }
                        count++;
                    }
                    db.collection("players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document: task.getResult()) {
                                    if(document.getId().equals(player1ID)){
                                        String toString = document.getString("display_name") + ":";
                                        tv_player1_name.setText(toString);
                                        player1PtsEarned = document.getLong("points") - player1_initialPts;
                                        toString = player1PtsEarned + " Pts";
                                        tv_player1_pts.setText(toString);
                                    }
                                    if(document.getId().equals(player2ID)){
                                        String toString = document.getString("display_name") + ":";
                                        tv_player2_name.setText(toString);
                                        player2PtsEarned = document.getLong("points") - player2_initialPts;
                                        toString = player2PtsEarned  + " Pts";
                                        tv_player2_pts.setText(toString);
                                    }
                                    if(document.getId().equals(player3ID)){
                                        String toString = document.getString("display_name") + ":";
                                        tv_player3_name.setText(toString);
                                        player3PtsEarned = document.getLong("points") - player3_initialPts;
                                        toString = player3PtsEarned + " Pts";
                                        tv_player3_pts.setText(toString);
                                    }
                                    if(document.getId().equals(player4ID)){
                                        String toString = document.getString("display_name") + ":";
                                        tv_player4_name.setText(toString);
                                        player4PtsEarned = document.getLong("points") - player4_initialPts;
                                        toString = player4PtsEarned + " Pts";
                                        tv_player4_pts.setText(toString);
                                    }
                                    if(document.getId().equals(player5ID)){
                                        String toString = document.getString("display_name") + ":";
                                        tv_player5_name.setText(toString);
                                        player5PtsEarned = document.getLong("points") - player5_initialPts;
                                        toString= player5PtsEarned + " Pts";
                                        tv_player5_pts.setText(toString);
                                    }
                                }
                                updateScore();
                            }
                        }
                    });
                }else{
                    Toast.makeText(mContext.getApplicationContext(),("ERROR!! CANNOT ACCESS DATABASE."),Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void updateScore(){
        score = initialScore + player1PtsEarned + player2PtsEarned + player3PtsEarned
                + player4PtsEarned  + player5PtsEarned;
        toString = "" + score;
        tv_totalScore.setText(toString);

        Map<String, Object> scoreUpdate = new HashMap<>();
        scoreUpdate.put("score",score);
        db.collection("users").document(username).update(scoreUpdate).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext.getApplicationContext(), "failed to update score", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
