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
import android.widget.EditText;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils.DatabaseHandler;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.ColorGroup;

public class DeleteGroupDialogFragment extends DialogFragment {

    private ColorGroup colorGroup;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete_group, container, false);

        colorGroup = getArguments().getParcelable("GROUP");

        position = getArguments().getInt("INDEX");

        final Button save = (Button) v.findViewById(R.id.save);
        final Button delete = (Button) v.findViewById(R.id.delete);
        final EditText name = (EditText) v.findViewById(R.id.group_name);
        final EditText desc = (EditText) v.findViewById(R.id.group_desc);

        if(colorGroup != null) {
            name.setText(colorGroup.getName());
            desc.setText(colorGroup.getDescription());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")) {
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    colorGroup.setName(name.getText().toString());
                    colorGroup.setDescription(desc.getText().toString());
                    db.updateGroup(colorGroup);

                    DeleteGroupDialogListener activity = (DeleteGroupDialogListener) getActivity();
                    activity.onModifyGroup(colorGroup, position);

                    dismiss();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > -1) {
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.deleteGroup(colorGroup.getId());

                    DeleteGroupDialogListener activity = (DeleteGroupDialogListener) getActivity();
                    activity.onDeleteGroup(position);
                }
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

    public interface DeleteGroupDialogListener {
        void onDeleteGroup(int positioon);
        void onModifyGroup(ColorGroup colorGroup, int positioon);
    }

}
