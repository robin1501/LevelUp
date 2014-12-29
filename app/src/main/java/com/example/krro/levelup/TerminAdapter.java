package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;


public class TerminAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public TerminAdapter(Context context, Cursor c) {
        super(context, c);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.terminliste, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView zeitpunkt = (TextView)view.findViewById(R.id.tvDatum);
        zeitpunkt.setText(cursor.getString(2));

        TextView workout = (TextView)view.findViewById(R.id.tvWorkout);
        workout.setText(cursor.getString(3));

        final int terminID = cursor.getInt(0);
        final int workoutID = cursor.getInt(1);
        final long kalendereintrag = cursor.getLong(4);
        final Context todoContext = context;

        ImageView delete = (ImageView)view.findViewById(R.id.ivDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                alert.setTitle("Termin wirklich löschen?");

                alert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dbHelper = new DBHelper(todoContext);
                        db = dbHelper.getWritableDatabase();

                        db.delete("todo_workouthead", "t_id = " + terminID, null);

                        db.close();

                        if(kalendereintrag != 0)
                        {

                        }

                        ((TodoWorkoutActivity)todoContext).setListView();
                    }
                });

                alert.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        ImageView start = (ImageView)view.findViewById(R.id.ivStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uebungDetail = new Intent(todoContext, StartWorkoutActivty.class);
                uebungDetail.putExtra("terminID", terminID);
                uebungDetail.putExtra("workoutID",workoutID);
                todoContext.startActivity(uebungDetail);

            }
        });
    }
}
