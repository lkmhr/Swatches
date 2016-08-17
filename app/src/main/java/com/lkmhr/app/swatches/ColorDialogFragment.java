package com.lkmhr.app.swatches;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils.DatabaseHandler;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.Color;

public class ColorDialogFragment extends DialogFragment {

    private int position;
    private Color color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_color, container, false);

        color = getArguments().getParcelable("COLOR");
        position = getArguments().getInt("INDEX");

        final Button modify = (Button) v.findViewById(R.id.modify);
        final Button delete = (Button) v.findViewById(R.id.delete);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDialogListener activity = (ColorDialogListener) getActivity();
                activity.onModify(color, position);
                dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.deleteColor(color.getId());

                ColorDialogListener activity = (ColorDialogListener) getActivity();
                activity.onDelete(position);
                dismiss();
            }
        });

        return v;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public interface ColorDialogListener {
        void onModify(Color color, int position);
        void onDelete(int position);
    }

}
