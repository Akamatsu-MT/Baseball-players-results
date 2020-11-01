package com.example.pitcerbatter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Re_ThirdActivity extends ThirdActivity{
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    int order_number = 0;
    String[] member;
    TextView[] tv2 = new TextView[9];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_activity_third);

        tv2[0] = (TextView) findViewById(R.id.Startingmember);
        tv2[1] = (TextView) findViewById(R.id.Startingmember2);
        tv2[2] = (TextView) findViewById(R.id.Startingmember3);
        tv2[3] = (TextView) findViewById(R.id.Startingmember4);
        tv2[4] = (TextView) findViewById(R.id.Startingmember5);
        tv2[5] = (TextView) findViewById(R.id.Startingmember6);
        tv2[6] = (TextView) findViewById(R.id.Startingmember7);
        tv2[7] = (TextView) findViewById(R.id.Startingmember8);
        tv2[8] = (TextView) findViewById(R.id.Startingmember9);

        Intent intent = getIntent();

        member = intent.getStringArrayExtra("member");
        Plname = member[0];
        for(int aaa=0;aaa<9;aaa++) {
            tv2[aaa].setText(member[aaa]);
        }

        final TextView tv = (TextView) findViewById(R.id.textView);
        readData(Plname);
        print();
        setColor();

        final Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(buttonClick);

        final Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(buttonClick);

        final Button button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(buttonClick);

        final Button button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(buttonClick);

        final Button button5 = findViewById(R.id.fdball_button);
        button5.setOnClickListener(buttonClick);

        final Button button6 = findViewById(R.id.out_button);
        button6.setOnClickListener(buttonClick);

        final Button button7 = findViewById(R.id.bant_button);
        button7.setOnClickListener(buttonClick);

        final Button button8 = findViewById(R.id.fly_button);
        button8.setOnClickListener(buttonClick);

        final Button Strikeout_button = findViewById(R.id.strikeout_button);
        Strikeout_button.setOnClickListener(buttonClick);
    }
    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            float before_stroke=strokes;
            switch (view.getId()) {
                case R.id.button_1:
                    buttonmove2(1, 1);
                    break;
                case R.id.button_2:
                    buttonmove2(1, 2);
                    break;
                case R.id.button_3:
                    buttonmove2(1, 3);
                    break;
                case R.id.button_4:
                    buttonmove2(1, 4);
                    break;
                case R.id.fdball_button:
                    bats += 1;
                    fdball += 1;
                    save();
                    break;
                case R.id.out_button:
                    buttonmove2(0, 0);
                    break;
                case R.id.bant_button:
                    bats += 1;
                    bant += 1;
                    save();
                    break;
                case R.id.fly_button:
                    bats += 1;
                    fly += 1;
                    save();
                    break;
                case R.id.strikeout_button:
                    bats += 1;
                    strokes += 1;
                    strikeout += 1;
                    save();
                    break;
            }
        }
    };
    public void save(){
        insertData(Plname);
        order_number = number(order_number);
        Plname = member[order_number];
        readData(Plname);
        print();
        setColor();
    }
    public int number(int b){
        b++;
        if(b>=9){
            b=0;
        }
        return b;
    }
    public void setColor(){
        for(int z=0;z<9;z++) {
            if(z==order_number) {
                tv2[z].setBackgroundResource(R.drawable.backcolor);
            }
            else{
                tv2[z].setBackgroundResource(R.drawable.white);
            }
        }
    }
    private void Alldel(String name){
        Context context = getApplicationContext();
        try {
            db.delete("Batters_table", "NAME=?", new String[]{name});
        }catch(Exception e){
            Toast.makeText(context,"データ削除に失敗しました",Toast.LENGTH_SHORT).show();
        }
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("NAME", name);
            db.insert("Batters_table", null, cv);
        }catch (Exception e){
            Toast.makeText(context,"データ書き込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
    private void readData(String name){
        Context context = getApplicationContext();
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }
        //  final TextView tv = (TextView) findViewById(R.id.textView);
        try {

            String sql = "select NAME,bats,strokes,hit,total_runs,fdball,strikeout,bant,fly,left,centor,right from Batters_table where NAME = ?;";
            Cursor cursor = db.rawQuery(sql, new String[]{name});
            cursor.moveToFirst();


            for (int i = 0; i < cursor.getCount(); i++) {
                name = cursor.getString(0);
                bats=cursor.getFloat(1);
                strokes=cursor.getFloat(2);
                hit=cursor.getFloat(3);
                total_runs=cursor.getFloat(4);
                fdball = cursor.getFloat(5);
                strikeout = cursor.getInt(6);
                bant=cursor.getInt(7);
                fly=cursor.getInt(8);
                left = cursor.getFloat(9);
                centor = cursor.getFloat(10);
                right = cursor.getFloat(11);
                cursor.moveToNext();
            }
            // 忘れずに！
            cursor.close();
            // tv.setText(sbuilder.toString());
            //Toast.makeText(context,"データ読み込みに成功しました",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context,"データ読み込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
    private void insertData(String com){
        final TextView tv = (TextView) findViewById(R.id.textView);
        Context context = getApplicationContext();
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("NAME", com);
            cv.put("bats", bats);
            cv.put("strokes", strokes);
            cv.put("hit", hit);
            cv.put("total_runs", total_runs);
            cv.put("fdball", fdball);
            cv.put("strikeout", strikeout);
            cv.put("bant", bant);
            cv.put("fly", fly);
            cv.put("left", left);
            cv.put("centor", centor);
            cv.put("right", right);
            try{
                db.update("Batters_table", cv, "NAME=?", new String[]{com});
                //Toast.makeText(context,"データの上書きに成功しました",Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                db.insert("Batters_table", null, cv);
                Toast.makeText(context,"データの書き込みに成功しました",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context,"データ書き込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }
    public void buttonmove2(final float h,final float i){
        final CharSequence[] result = {"レフト方向", "センター方向", "ライト方向"};
        new AlertDialog.Builder(Re_ThirdActivity.this)
                .setTitle(R.string.dlg_title)
                .setItems(
                        result,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                switch(which){
                                    case 0:
                                        hitmove(h,i);
                                        left+=1;
                                        break;
                                    case 1:
                                        hitmove(h,i);
                                        centor+=1;
                                        break;
                                    case 2:
                                        hitmove(h,i);
                                        right+=1;
                                        break;
                                }
                                save();
                            }
                        }
                )
                .setPositiveButton(
                        R.string.dlg_pst_btn,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .show();
    }

}
