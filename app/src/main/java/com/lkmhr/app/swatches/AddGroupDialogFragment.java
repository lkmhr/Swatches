package com.lkmhr.app.swatches;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class AddGroupDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_group, container, false);

        final Button create = (Button) v.findViewById(R.id.create);
        final Button cancel = (Button) v.findViewById(R.id.cancel);
        final EditText name = (EditText) v.findViewById(R.id.group_name);
        final EditText desc = (EditText) v.findViewById(R.id.group_desc);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()) {
                    DatabaseHandler db = new DatabaseHandler(getActivity());

                    db.addGroup(new ColorGroup(name.getText().toString(), desc.getText().toString()));

                    AddGroupDialogListener activity = (AddGroupDialogListener) getActivity();
                    activity.onAddGroup(new ColorGroup(name.getText().toString(),desc.getText().toString()));
                    dismiss();
                } else {
                    name.requestFocus();
                    name.setError("Name should not be blank!");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;

    }

    @Override
    @NonNull
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

    public interface AddGroupDialogListener {
        void onAddGroup(ColorGroup colorGroup);
    }

}
