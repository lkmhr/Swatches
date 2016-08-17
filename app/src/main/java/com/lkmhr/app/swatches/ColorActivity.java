package com.lkmhr.app.swatches;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils.DatabaseHandler;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.Color;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.ColorAdapter;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.GridDecoration;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.List;
import java.util.Random;

public class ColorActivity extends AppCompatActivity implements
        ColorAdapter.OnItemClickListener, ColorAdapter.OnItemLongClickListener,
        ColorDialogFragment.ColorDialogListener{

    private int groupId;
    private List<Color> colors;
    private ColorAdapter colorAdapter;
    private RecyclerView recyclerList;
    private View empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(getIntent()!=null) {
            groupId = getIntent().getExtras().getInt("GROUP");
        } else {
            finish();
        }

        DatabaseHandler db = new DatabaseHandler(this);
        colors  = db.getColors(groupId);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        empty = (View) findViewById(R.id.empty);
        if(colors.size()==0) {
            empty.setVisibility(View.VISIBLE);
            recyclerList.setVisibility(View.GONE);
        }

        colorAdapter = new ColorAdapter(this, colors);
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

                    int r = new Random().nextInt(256);
                    int g = new Random().nextInt(256);
                    int b = new Random().nextInt(256);

                    final ColorPicker cp = new ColorPicker(ColorActivity.this, r, g, b);
                    cp.show();

                    Button okColor = (Button)cp.findViewById(R.id.okColorButton);
                    okColor.setText("Save");

                    okColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Color color = new Color("",Color.makeRGBtoHex(cp.getRed(),cp.getGreen(),cp.getBlue()),groupId);

                            final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            db.addColor(color);

                            colors.add(color);
                            colorAdapter.notifyDataSetChanged();

                            empty.setVisibility(View.GONE);
                            recyclerList.setVisibility(View.VISIBLE);

                            cp.dismiss();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onItemClicked(int position) {
        if(position>=0) {

            String hex = colors.get(position).getHexCode();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Color", hex);
            clipboard.setPrimaryClip(clip);

            CoordinatorLayout layout = (CoordinatorLayout)findViewById(R.id.coordinator);
            if(layout!=null){
                Snackbar.make(layout,"Copied : " + hex, Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onItemLongClicked(int position, Color color) {
        DialogFragment fragment = new ColorDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable("COLOR",color);
        args.putInt("INDEX",position);

        fragment.setArguments(args);
        fragment.show(getSupportFragmentManager(), "color dialog");
        return false;
    }

    @Override
    public void onModify(final Color color, final int position) {
        if( color!=null ) {

            int rgbColor = Color.makeHexToRGB(color.getHexCode());

            final ColorPicker cp = new ColorPicker(ColorActivity.this,
                    android.graphics.Color.red(rgbColor),
                    android.graphics.Color.green(rgbColor),
                    android.graphics.Color.blue(rgbColor));
            cp.show();

            Button okColor = (Button)cp.findViewById(R.id.okColorButton);
            okColor.setText("Save");
            okColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String hex = Color.makeRGBtoHex(cp.getRed(),cp.getGreen(),cp.getBlue());
                    color.setHexCode(hex);

                    final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    db.updateColor(color);

                    colors.get(position).setHexCode(hex);
                    colorAdapter.notifyDataSetChanged();

                    cp.dismiss();
                }
            });
        }
    }

    @Override
    public void onDelete(int position) {
        colors.remove(position);
        colorAdapter.notifyDataSetChanged();

        if(colors.size()==0) {
            empty.setVisibility(View.VISIBLE);
            recyclerList.setVisibility(View.GONE);
        }
    }
}
