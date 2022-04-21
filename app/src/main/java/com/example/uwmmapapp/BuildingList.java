package com.example.uwmmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        //if(view.toString().compareTo("UWM Student Union") == 0) {
            Intent intent = new Intent(BuildingList.this, MapView.class);
            startActivity(intent);
        //}
    }
}