package com.example.azfantasypl;

import android.provider.ContactsContract;

public class Player {

    private String id;
    private String fName;
    private String lName;
    private String team;
    private String position;
    private int image;
    private long points;


    public Player(){

    }

    public Player(String id, String First, String Last, String Team, String Position, int Image, long Points){
        this.id = id;
        this.fName = First;
        this.lName = Last;
        this.team = Team;
        this.position = Position;
        this.image = Image;
        this.points = Points;
    }

    public String getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public int getImage() {
        return image;
    }

    public long getPoints() {
        return points;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPoints(long points) {
        this.points = points;
    }

}
