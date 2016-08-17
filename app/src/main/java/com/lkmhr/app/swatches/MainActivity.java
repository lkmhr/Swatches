package com.lkmhr.app.swatches;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils.DatabaseHandler;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.ColorGroup;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.GroupAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddGroupDialogFragment.AddGroupDialogListener,
        GroupAdapter.OnItemClickListener, GroupAdapter.OnItemLongClickListener,
        DeleteGroupDialogFragment.DeleteGroupDialogListener {

    RecyclerView recyclerList;
    GroupAdapter groupAdapter;
    List<ColorGroup> groups;
    View empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        DatabaseHandler db = new DatabaseHandler(this);

        groups  = db.getGroups();
        groupAdapter = new GroupAdapter(this, groups);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        empty = (View) findViewById(R.id.empty);
        if(groups.size() == 0) {
            recyclerList.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }

        if(recyclerList!=null) {
            recyclerList.setAdapter(groupAdapter);
            recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerList.setHasFixedSize(true);
        }

        if(fab!=null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment addFragment = new AddGroupDialogFragment();
                    addFragment.show(getSupportFragmentManager(), "add dialog");
                }
            });
        }
    }

    @Override
    public void onAddGroup(ColorGroup colorGroup) {
        if(groups != null) {

            empty.setVisibility(View.GONE);
            recyclerList.setVisibility(View.VISIBLE);

            groups.add(colorGroup);
            groupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClicked(int position) {
        if(position>=0){
            Intent intent = new Intent(MainActivity.this, ColorActivity.class);
            intent.putExtra("GROUP",groups.get(position).getId());

            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClicked(int position, ColorGroup colorGroup) {
        if(position>=0){

            DialogFragment fragment = new DeleteGroupDialogFragment();

            Bundle args = new Bundle();
            args.putParcelable("GROUP",colorGroup);
            args.putInt("INDEX",position);

            fragment.setArguments(args);
            fragment.show(getSupportFragmentManager(), "delete dialog");
        }
        return false;
    }

    @Override
    public void onDeleteGroup(int position) {
        groups.remove(position);
        groupAdapter.notifyDataSetChanged();

        if(groups.size()==0) {
            empty.setVisibility(View.VISIBLE);
            recyclerList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onModifyGroup(ColorGroup colorGroup, int position) {
        groups.get(position).setName(colorGroup.getName());
        groups.get(position).setDescription(colorGroup.getDescription());
        groupAdapter.notifyDataSetChanged();
    }
}
