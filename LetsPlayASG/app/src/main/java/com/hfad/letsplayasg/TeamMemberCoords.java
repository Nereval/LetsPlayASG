package com.hfad.letsplayasg;

/**
 * Created by Piotrek on 02.02.2018.
 */

public class TeamMemberCoords {
    String userName;
    double x;
    double y;

    public TeamMemberCoords(String userName, String x, String y){
        this.userName = userName;
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }
}
