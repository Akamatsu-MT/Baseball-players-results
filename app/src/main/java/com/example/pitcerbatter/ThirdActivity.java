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

import androidx.appcompat.app.AppCompatActivity;


public class ThirdActivity extends AppCompatActivity {
    private TestOpenHelper helper;
    private SQLiteDatabase db;

    float left = 0;
    float centor = 0;
    float right = 0;
    float hit=0;
    float bats=0;
    float strokes=0;
    float total_runs=0;
    float fdball = 0;
    int strikeout = 0;
    int fly=0;
    int bant=0;
    String Plname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        try {
            String[] member = intent.getStringArrayExtra("member");
            Plname = member[0];
        }catch(Exception e){
            Plname = intent.getStringExtra("playername");
        }

        final TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(Plname);

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

        final Button D_button = findViewById(R.id.Delete_button);
        D_button.setOnClickListener(buttonClick);

        final Button R_button = findViewById(R.id.readbutton);
        R_button.setOnClickListener(buttonClick);

        final Button S_button = findViewById(R.id.save_button);
        S_button.setOnClickListener(buttonClick);
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_1:
                    strokes=buttonmove(1,1);
                    break;
                case R.id.button_2:
                    strokes=buttonmove(1,2);
                    break;
                case R.id.button_3:
                    strokes=buttonmove(1,3);
                    break;
                case R.id.button_4:
                    strokes=buttonmove(1,4);
                    break;
                case R.id.fdball_button:
                    bats+=1;
                    fdball+=1;
                    print();
                    break;
                case R.id.out_button:
                    strokes=buttonmove(0,0);
                    break;
                case R.id.bant_button:
                    bats+=1;
                    bant+=1;
                    print();
                    break;
                case R.id.fly_button:
                    bats+=1;
                    fly+=1;
                    print();
                    break;
                case R.id.strikeout_button:
                    bats+=1;
                    strokes+=1;
                    strikeout+=1;
                    print();
                    break;
                case R.id.Delete_button:
                    Alldel(Plname);
                    break;
                case R.id.readbutton:
                    readData(Plname);
                    print();
                    break;
                case R.id.save_button:
                    insertData(Plname);
                    break;
            }
        }
        private void Alldel(String name){
            Context context = getApplicationContext();
            if(helper == null){
                helper = new TestOpenHelper(getApplicationContext());
            }

            if(db == null){
                db = helper.getWritableDatabase();
            }
            try {
                ContentValues cv = new ContentValues();
                cv.put("NAME", name);
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

                db.update("Batters_table", cv, "NAME=?", new String[]{name});
                // Toast.makeText(context,"データの書き込みに成功しました",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context,"データ読み込みに成功しました",Toast.LENGTH_SHORT).show();
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
    };
    public float buttonmove(final float h,final float i){
        final CharSequence[] result = {"レフト方向", "センター方向", "ライト方向"};
        new AlertDialog.Builder(ThirdActivity.this)
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
                                print();
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
        return strokes;
    }
    public void hitmove(float h,float i){
        hit+=h;
        bats+=1;
        strokes+=1;
        total_runs+=i;
    }
    public void print(){
        final TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(Plname +"\n\n"+
                String.format("通算%.0f",bats) + "打席"+
                "\n打率　：" + String.format("%.4f (%.0f/%.0f)", hit/strokes,hit,strokes) +
                "\n出塁率：" + String.format("%.4f (%.0f/%.0f)", (hit+fdball)/bats,(hit+fdball),bats) +
                "\n長打率：" + String.format("%.4f (%.0f/%.0f)", total_runs/strokes,total_runs,strokes) +
                "\nＯＰＳ：" + String.format("%.4f",(total_runs/strokes+((hit+fdball)/bats))) +
                "\n四死球：" + String.format("%.0f",fdball)+
                "個\n三振　：" + strikeout +
                "個\n犠打　：" + bant +
                "個\n犠飛　：" + fly +
                "個\n打球方向" +
                "\n左："+String.format("%4.2f", left*100/(left+centor+right)) +"%" +
                "\n中："+String.format("%4.2f", centor*100/(left+centor+right)) +"%" +
                "\n右："+String.format("%4.2f", right*100/(left+centor+right))+ "%"
        );
    }
}
