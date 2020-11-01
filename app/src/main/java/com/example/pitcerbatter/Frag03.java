package com.example.pitcerbatter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Frag03 extends Flag02 {
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    int i = 0;
    TextView[] tv = new TextView[9];
    String[] member = {" "," "," "," "," "," "," "," "," "};
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.starting_member, container, false);
        reading = view;
        final ArrayList data = new ArrayList<>();
        final ListView listView = (ListView)view.findViewById(R.id.listView2);
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplication()
                , android.R.layout.simple_list_item_1, data);

        final Context context = getActivity().getApplicationContext();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        readData(view);

        view.findViewById(R.id.input_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for(int k=0;k<9;k++){
                    try {
                        if(member[k].contentEquals(" ")) {
                            count++;
                        }
                    }catch (Exception e){
                        Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                    }
                }
                if(count>0){
                    Toast.makeText(context,"スタメンを決めてください",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Re_ThirdActivity.class);
                    intent.putExtra("member", member);
                    startActivity(intent);
                }
            }
        });
        view.findViewById(R.id.DELETE_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention_Alldelete();
                readData(view);
            }
        });
        view.findViewById(R.id.INSERT_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final EditText editText = new EditText(getActivity());
                editText.setHint(R.string.write);
                new AlertDialog.Builder(getActivity())
                        .setTitle("選手名を入力してください")
                        .setView(editText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                names=editText.getText().toString();
                                insertData(names);
                                readData(view);
                            }
                        })
                        .show();
            }
        });
        view.findViewById(R.id.RESET_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv[0] = (TextView) getActivity().findViewById(R.id.Startingmember);
                tv[1] = (TextView) getActivity().findViewById(R.id.Startingmember2);
                tv[2] = (TextView) getActivity().findViewById(R.id.Startingmember3);
                tv[3] = (TextView) getActivity().findViewById(R.id.Startingmember4);
                tv[4] = (TextView) getActivity().findViewById(R.id.Startingmember5);
                tv[5] = (TextView) getActivity().findViewById(R.id.Startingmember6);
                tv[6] = (TextView) getActivity().findViewById(R.id.Startingmember7);
                tv[7] = (TextView) getActivity().findViewById(R.id.Startingmember8);
                tv[8] = (TextView) getActivity().findViewById(R.id.Startingmember9);
                for(i=0;i<9;i++){
                    member[i] = " ";
                    tv[i].setText(" ");
                }
                i = 0;
            }
        });
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tv[0] = (TextView) getActivity().findViewById(R.id.Startingmember);
        tv[1] = (TextView) getActivity().findViewById(R.id.Startingmember2);
        tv[2] = (TextView) getActivity().findViewById(R.id.Startingmember3);
        tv[3] = (TextView) getActivity().findViewById(R.id.Startingmember4);
        tv[4] = (TextView) getActivity().findViewById(R.id.Startingmember5);
        tv[5] = (TextView) getActivity().findViewById(R.id.Startingmember6);
        tv[6] = (TextView) getActivity().findViewById(R.id.Startingmember7);
        tv[7] = (TextView) getActivity().findViewById(R.id.Startingmember8);
        tv[8] = (TextView) getActivity().findViewById(R.id.Startingmember9);

        member[i] = (String)parent.getItemAtPosition(position);
        tv[i].setText(member[i]);
        i++;
        if(i==9){
            i = 0;
        }
    }
    private void del(SQLiteDatabase db,String name){
        db.delete( "Batters_table", "NAME=?", new String[]{name});
    }
    private void Alldel(SQLiteDatabase db){
        db.delete( "Batters_table", null, null);
    }
    private void readData(View view){
        final ArrayList data = new ArrayList<>();
        final ListView listView = (ListView)view.findViewById(R.id.listView2);
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplication()
                , android.R.layout.simple_list_item_1, data);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Context context = getActivity().getApplicationContext();
        if(helper == null){
            helper = new TestOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }
        try {

            String sql = "SELECT NAME FROM Batters_table;";
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();

            String x="";
            for (int i = 0; i < c.getCount(); i++) {
                data.add(c.getString(0));
                c.moveToNext();
            }
            c.close();
            // tv.setText(sbuilder.toString());
        //    Toast.makeText(context,"データ読み込みに成功しました",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context,"データ読み込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
    private void insertData(String com){
        Context context = getActivity().getApplicationContext();
        if(helper == null){
            helper = new TestOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("NAME", com);
            cv.put("bats", 0);
            cv.put("strokes", 0);
            cv.put("hit", 0);
            cv.put("total_runs", 0);
            cv.put("fdball", 0);
            cv.put("strikeout", 0);
            cv.put("bant", 0);
            cv.put("fly", 0);
            cv.put("left", 0);
            cv.put("centor", 0);
            cv.put("right", 0);

            db.insert("Batters_table", null, cv);
         //   Toast.makeText(context,"データの書き込みに成功しました",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context,"データ書き込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void attention_delete(final String player_name) {
        new AlertDialog.Builder(getActivity())
                .setTitle("確認")
                .setMessage("選手名【"+player_name+"】\n\nこの選手のデータを消去しますか")
                .setPositiveButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                })
                .setNegativeButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        del(db,player_name);
                        readData(reading);
                    }
                })
                .show();
    }
}
