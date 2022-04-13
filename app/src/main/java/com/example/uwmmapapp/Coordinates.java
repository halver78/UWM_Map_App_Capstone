package com.example.uwmmapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Coordinates {
    public static Map<String,double[]> coordList;

    public static void init(){

        coordList = new HashMap<String,double[]>();
        double[] unioncoord = {43.075144698206685,-87.88242486618464};
        double[] mellcoord = {43.075294568299284, -87.87981831571807};
        double[] mitchcoord = {43.075610954500405, -87.87929101756576};
        double[] purincoord = {43.07501854906647, -87.8776627022242};
        double[] emscoord = {43.07588501409983, -87.88577940588156};

        coordList.put("UWM Student Union", unioncoord);
        coordList.put("Mellencamp Hall", mellcoord);
        coordList.put("Mitchell Hall", mitchcoord);
        coordList.put("Purin Hall", purincoord);
        coordList.put("Engineering and Math. Science Building", emscoord);

    }
    public static Map<String,double[]> getcoords(){
        return coordList;
    }


}
