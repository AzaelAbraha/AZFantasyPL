package com.example.azfantasypl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyTeamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private FloatingActionButton FAB;

    final int PLAYER_SIZE = 20;
    String playerId[] = new String[PLAYER_SIZE];
    String firstName[] = new String[PLAYER_SIZE];
    String lastName[] = new String[PLAYER_SIZE];
    String team[] = new String[PLAYER_SIZE];
    String position[] = new String[PLAYER_SIZE];
    long points[] = new long[PLAYER_SIZE];
    int imageRes;

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

    private Button edit_bt;
    private TextView tv_player1;
    private TextView tv_player2;
    private TextView tv_player3;
    private TextView tv_player4;
    private TextView tv_player5;
    private ImageView iv_player1;
    private ImageView iv_player2;
    private ImageView iv_player3;
    private ImageView iv_player4;
    private ImageView iv_player5;

    String username;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.azfantasypl";

    int count = 0;

    Context mContext;

    public MyTeamFragment() {
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
        View v = inflater.inflate(R.layout.fragment_my_team, container, false);

        mContext = v.getContext();
        edit_bt = (Button) v.findViewById(R.id.bt_edit_team);
        edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectPlayersActivity.class);
                startActivity(intent);
            }
        });
        tv_player1 = (TextView) v.findViewById(R.id.tv_player_name_ls);
        tv_player2 = (TextView) v.findViewById(R.id.tv_player_name_rs);
        tv_player3 = (TextView) v.findViewById(R.id.tv_player_name_lm);
        tv_player4 = (TextView) v.findViewById(R.id.tv_player_name_rm);
        tv_player5 = (TextView) v.findViewById(R.id.tv_player_name_gk);
        iv_player1 = (ImageView) v.findViewById(R.id.iv_player_ls);
        iv_player2 = (ImageView) v.findViewById(R.id.iv_player_rs);
        iv_player3 = (ImageView) v.findViewById(R.id.iv_player_lm);
        iv_player4 = (ImageView) v.findViewById(R.id.iv_player_rm);
        iv_player5 = (ImageView) v.findViewById(R.id.iv_player_gk);

        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        username = mPreferences.getString("USER_KEY","");

//        player1ID = mPreferences.getString("PLAYER1","player1");
//        player2ID = mPreferences.getString("PLAYER2","player2");
//        player3ID = mPreferences.getString("PLAYER3","player3");
//        player4ID = mPreferences.getString("PLAYER4","player4");
//        player5ID = mPreferences.getString("PLAYER5","player5");

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    count = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        if(document.getId().equals(username)) {
                            player1ID = document.getString("player1");
                            player2ID = document.getString("player2");
                            player3ID = document.getString("player3");
                            player4ID = document.getString("player4");
                            player5ID = document.getString("player5");
                        }
                        count++;
                    }
                }else{
                    Toast.makeText(mContext.getApplicationContext(),("ERROR!! CANNOT ACCESS DATABASE."),Toast.LENGTH_LONG).show();
                }
            }
        });
        db.collection("players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getId().equals(player1ID)){
                            String toString = document.getString("first_name") + " " + document.getString("last_name");
                            tv_player1.setText(toString);
                            imageRes =  mContext.getResources().getIdentifier("icon_" + document.getId(), "drawable", mContext.getPackageName());
                            iv_player1.setImageResource(imageRes);
                        }
                        if(document.getId().equals(player2ID)){
                            String toString = document.getString("first_name") + " " + document.getString("last_name");
                            tv_player2.setText(toString);
                            imageRes =  mContext.getResources().getIdentifier("icon_" + document.getId(), "drawable", mContext.getPackageName());
                            iv_player2.setImageResource(imageRes);
                        }
                        if(document.getId().equals(player3ID)){
                            String toString = document.getString("first_name") + " " + document.getString("last_name");
                            tv_player3.setText(toString);
                            imageRes =  mContext.getResources().getIdentifier("icon_" + document.getId(), "drawable", mContext.getPackageName());
                            iv_player3.setImageResource(imageRes);
                        }
                        if(document.getId().equals(player4ID)){
                            String toString = document.getString("first_name") + " " + document.getString("last_name");
                            tv_player4.setText(toString);
                            imageRes =  mContext.getResources().getIdentifier("icon_" + document.getId(), "drawable", mContext.getPackageName());
                            iv_player4.setImageResource(imageRes);
                        }
                        if(document.getId().equals(player5ID)){
                            String toString = document.getString("first_name") + " " + document.getString("last_name");
                            tv_player5.setText(toString);
                            imageRes =  mContext.getResources().getIdentifier("icon_" + document.getId(), "drawable", mContext.getPackageName());
                            iv_player5.setImageResource(imageRes);
                        }
                    }
                }
            }
        });

        for(int i=0; i<count; i++) {
            if(playerId[i] == player1ID) {
                tv_player1.setText(firstName[i] + " " + lastName[i]);
            }

            if(playerId[i] == player2ID)
                tv_player2.setText(firstName[i] + " " + lastName[i]);

            if(playerId[i] == player3ID)
                tv_player3.setText(firstName[i] + " " + lastName[i]);

            if(playerId[i] == player4ID)
                tv_player4.setText(firstName[i] + " " + lastName[i]);

            if(playerId[i] == player2ID)
                tv_player5.setText(firstName[i] + " " + lastName[i]);
        }

        player1_initialPts = mPreferences.getLong("PLAYER1_INITIAL",0);
        player2_initialPts = mPreferences.getLong("PLAYER2_INITIAL",0);
        player3_initialPts = mPreferences.getLong("PLAYER3_INITIAL",0);
        player4_initialPts = mPreferences.getLong("PLAYER4_INITIAL",0);
        player5_initialPts = mPreferences.getLong("PLAYER5_INITIAL",0);

//        FAB = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
//        FAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SelectPlayersActivity.class);
//                startActivity(intent);
//            }
//        });
        return v;
    }

    public void onEditTeamClicked (View view){
        Intent intent = new Intent(getActivity(), SelectPlayersActivity.class);
        startActivity(intent);
    }

//
//    // TODO: Rename method, update argument and hook method into UI event


}
