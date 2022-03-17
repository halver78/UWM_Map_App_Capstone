package com.example.uwmmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BuildingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        String[] uwmBuild = getResources().getStringArray(R.array.uwmBuildings);

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uwmBuild);

        final ListView theListView = (ListView) findViewById(R.id.theListView);

        theListView.setAdapter(adapter);


    }
}