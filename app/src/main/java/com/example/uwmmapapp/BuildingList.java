package com.example.uwmmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BuildingList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        ListView s = (ListView) findViewById(R.id.theListView);;

        String[] uwmBuild = getResources().getStringArray(R.array.uwmBuildings);

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uwmBuild);

        s.setAdapter(adapter);

        s.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id){
        // do stuff: if a page exists, display it
        String data = (String)parent.getItemAtPosition(position);
        Log.d("View value as String", data);
        if(data.compareTo("UWM Student Union") == 0) {
            setContentView(R.layout.activity_union_map);
        }
        else if(data.compareTo("Bolton Hall") == 0){
            setContentView(R.layout.activity_bolton_hall);
        }
        else if(data.compareTo("Lubar Hall") == 0){
            setContentView(R.layout.activity_lubar_hall);
        }
        else if(data.compareTo("Engineering and Math. Science Building") == 0){
            setContentView(R.layout.activity_ems_map);
        }
        else if(data.compareTo("Chemistry Building") == 0){
            setContentView(R.layout.activity_chem_map);
        }
    }
}