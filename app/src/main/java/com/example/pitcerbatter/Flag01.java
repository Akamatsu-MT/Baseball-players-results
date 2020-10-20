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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class Flag01 extends Fragment implements AdapterView.OnItemClickListener {
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    String names = "";
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.flag01_layout, container, false);

        final ArrayList data = new ArrayList<>();
        final ListView listView = (ListView)view.findViewById(R.id.listView);
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplication()
                , android.R.layout.simple_list_item_1, data);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        readData(view);

        view.findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(view);
            }
        });
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
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
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alldel(db);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String msg = (String)parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity().getApplicationContext(), SecondActivity.class);
        intent.putExtra("playername", msg);
        startActivity(intent);
    }
    private void Alldel(SQLiteDatabase db){
        db.delete( "Pitchers_table", null, null);
    }
    private void readData(View view){
        final ArrayList data = new ArrayList<>();
        final ListView listView = (ListView)view.findViewById(R.id.listView);
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
            String sql = "select NAME from Pitchers_table;";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                data.add(cursor.getString(0));
                cursor.moveToNext();
            }

            // 忘れずに！
            cursor.close();
            // tv.setText(sbuilder.toString());
            Toast.makeText(context,"データ読み込みに成功しました",Toast.LENGTH_SHORT).show();
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
            cv.put("total_out2",0);
            cv.put("total_points",0);
            cv.put("total_strike_outs",0);
            cv.put("total_foul",0);
            cv.put("total_swing",0);
            cv.put("total_miss_strike",0);
            cv.put("total_miss_ball",0);
            cv.put("total_goro",0);
            cv.put("total_liner",0);
            cv.put("total_fly",0);
            cv.put("total_fourball",0);
            cv.put("bats",0);
            cv.put("strokes",0);
            cv.put("total_singlehit",0);
            cv.put("total_twobasehit",0);
            cv.put("total_threebasehit",0);
            cv.put("total_homerun",0);
            db.insert("Pitchers_table", null, cv);
            Toast.makeText(context,"データの書き込みに成功しました",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(context,"データ書き込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
}
