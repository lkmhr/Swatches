package com.lkmhr.app.swatches;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils.DatabaseHandler;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.Color;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.ColorAdapter;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.GridDecoration;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.GroupAdapter;

import java.util.List;

public class ColorActivity extends AppCompatActivity {

    int groupId;
    List<Color> colors;
    ColorAdapter colorAdapter;
    RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(getIntent()!=null) {
            groupId = getIntent().getExtras().getInt("GROUP");
        } else {
            finish();
        }

        DatabaseHandler db = new DatabaseHandler(this);

        colors  = db.getColors(groupId);
        colorAdapter = new ColorAdapter(this, colors);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);

        if(recyclerList!=null) {
            recyclerList.setAdapter(colorAdapter);
            recyclerList.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            recyclerList.addItemDecoration(new GridDecoration(3, 4, false));
            recyclerList.setHasFixedSize(true);
        }

        if (fab != null){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

}
