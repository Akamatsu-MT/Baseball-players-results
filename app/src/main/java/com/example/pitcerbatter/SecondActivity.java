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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class SecondActivity extends AppCompatActivity{
    private TestOpenHelper helper;
    private SQLiteDatabase db;

    int strike=0;
    int ball=0;
    int out=0;
    float points=0;
    float strike_outs=0;
    float foul=0;
    float swing=0;
    float miss_strike=0;
    float miss_ball=0;
    float goro=0,liner=0,fly=0;
    float fourball=0;
    float single_hit=0,twobase_hit=0,threebase_hit=0,homerun=0;
    int total_out1=0;

    float total_out2=0;
    float total_points=0;
    float total_strike_outs=0;
    float total_foul=0;
    float total_swing=0;
    float total_miss_strike=0;
    float total_miss_ball=0;
    float total_goro=0;
    float total_liner=0;
    float total_fly=0;
    float total_fourball=0;
    float bats=0;
    float strokes=0;
    float total_singlehit=0;
    float total_twobasehit=0;
    float total_threebasehit=0;
    float total_homerun=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView tv = (TextView) findViewById(R.id.textView);
        final TextView tv2 = (TextView) findViewById(R.id.textView2);
        final TextView tv4 = (TextView) findViewById(R.id.textView4);
        final TextView tv5 = (TextView) findViewById(R.id.textView5);
        final TextView tv6 = (TextView) findViewById(R.id.textView6);
        final TextView points_tv = (TextView) findViewById(R.id.point_textView);
        final TextView tv8 = (TextView) findViewById(R.id.textView8);

        Intent intent = getIntent();
        final String Plname = intent.getStringExtra("playername");
       // final int ID = intent.getIntExtra("N_player",0);
        tv.setText(Plname);
        final String fileName = Plname + ".txt";

        findViewById(R.id.strike_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] result = {"空振り", "見逃し", "ファール"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.dlg1_title)
                        .setItems(
                                result,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        switch(which){
                                            case 0:
                                                swing+=1;
                                                total_swing+=1;
                                                strike+=1;
                                                tv2.setText("ストライク");
                                                break;
                                            case 1:
                                                miss_strike+=1;
                                                total_miss_strike+=1;
                                                strike+=1;
                                                tv2.setText("ストライク");
                                                break;
                                            case 2:
                                                if(strike<=1) {
                                                    strike+=1;
                                                }
                                                foul += 1;
                                                total_foul+=1;
                                                tv2.setText("ファール");
                                                break;
                                        }
                                        if(strike==3){
                                            strike=0;
                                            ball=0;
                                            out+=1;
                                            total_out1+=1;
                                            total_out2+=1;
                                            strike_outs+=1;
                                            total_strike_outs+=1;
                                            tv2.setText("三振！");
                                            strokes+=1;
                                            bats+=1;
                                        }
                                        if(out==3){
                                            strike=0;
                                            ball=0;
                                            out=0;
                                            tv2.setText("3アウト、チェンジ！");
                                        }
                                        tv.setText("B:" + ball + "\nS:" + strike + "\nO:" + out);
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
        });
        findViewById(R.id.ball_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ball+=1;
                miss_ball+=1;
                total_miss_ball+=1;
                if(ball==4) {
                    strike=0;
                    ball=0;
                    tv2.setText("フォアボール");
                    fourball+=1;
                    total_fourball+=1;
                    bats+=1;
                }
                else{
                    tv2.setText("ボール");
                }
                tv.setText("B:" + ball + "\nS:" + strike + "\nO:" + out);
            }
        });

        findViewById(R.id.bant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button("アウトです");
                out+=1;
                total_out1+=1;
                total_out2+=1;
            }
        });

        findViewById(R.id.out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button("ゲッツーです");
                out+=2;
                total_out1+=2;
                total_out2+=2;
            }
        });

        findViewById(R.id.hit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] result = {"単打", "二塁打", "三塁打","本塁打"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.dlg1_title)
                        .setItems(
                                result,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                single_hit+=1;
                                                total_singlehit+=1;
                                                tv2.setText("単打");
                                                break;
                                            case 1:
                                                twobase_hit+=1;
                                                total_twobasehit+=1;
                                                tv2.setText("二塁打");
                                                break;
                                            case 2:
                                                threebase_hit+=1;
                                                total_threebasehit+=1;
                                                tv2.setText("三塁打");
                                                break;
                                            case 3:
                                                homerun=+1;
                                                total_homerun+=1;
                                                tv2.setText("本塁打");
                                                break;
                                        }
                                        button("");
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
        });

        findViewById(R.id.plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points+=1;
                total_points+=1;
                points_tv.setText("自責点:"+ String.format("%.0f",points));
            }
        });

        findViewById(R.id.minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points>0) {
                    points--;
                    total_points--;
                }
                points_tv.setText("自責点:"+ String.format("%.0f",points));
            }
        });

        findViewById(R.id.henshu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] runner = {"ランナー1塁", "ランナー2塁", "ランナー3塁"};
                final boolean[] checkedItems = {false, false, false};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.dlg1_title)
                        .setMultiChoiceItems(
                                runner,
                                checkedItems,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        checkedItems[which] = isChecked;
                                    }
                                }
                        )
                        .setPositiveButton(
                                R.string.dlg_pst_btn,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (checkedItems[0]) {
                                            tv4.setText("ランナー\n1B｜〇");
                                        } else {
                                            tv4.setText("ランナー\n1B｜×");
                                        }
                                        if (checkedItems[1]) {
                                            tv5.setText("2B｜〇");
                                        } else {
                                            tv5.setText("2B｜×");
                                        }
                                        if (checkedItems[2]) {
                                            tv6.setText("3B｜〇");
                                        } else {
                                            tv6.setText("3B｜×");
                                        }
                                    }
                                }
                        )
                        .show();
            }
        });

        findViewById(R.id.record_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] result = {"今回の投球成績","通算成績"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.dlg1_title)
                        .setItems(
                                result,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        switch(which){
                                            case 0:
                                                tv8.setText("今回登板の成績\n" +
                                                        "投球回:"+total_out1/3+"回と"+total_out1%3+"/3イニング\n" +
                                                        String.format("自責点:%.0f",points)+
                                                        String.format("奪三振数:%.0f個\n",strike_outs)+
                                                        String.format("被安打数:%.0f本\n",single_hit+twobase_hit+threebase_hit+homerun)+
                                                        String.format("単打:%.0f本|二塁打:%.0f本|三塁打:%.0f本|HR:%.0f本\n",single_hit,twobase_hit,threebase_hit,homerun)+
                                                        String.format("与四球:%.0f個\n",fourball)+
                                                        "1."+String.format("ゴロ率:%.2f",goro*100/(goro+liner+fly))+"%\n"+
                                                        "2."+String.format("ライナー率:%.2f",liner*100/(goro+liner+fly))+"%\n"+
                                                        "3."+String.format("フライ率:%.2f",fly*100/(goro+liner+fly))+"%\n"+
                                                        "ストライク:"+String.format("%.0f",foul+swing+miss_strike)+ "球\n"+
                                                        "ボール:"+String.format("%.0f",miss_ball)+ "球\n"+
                                                        "1.見逃し率:"+String.format("%.2f",miss_strike*100/(foul+swing+miss_strike))+
                                                        "%\n2.空振り率:"+String.format("%.2f",swing*100/(foul+swing+miss_strike))+
                                                        "%\n3.ファウル率:"+String.format("%.2f",foul*100/(foul+swing+miss_strike))+"%"
                                                );
                                                break;
                                            case 1:
                                                tv8.setText("通算成績\n" +
                                                        String.format("投球回:%.0f回と%.0f/3イニング\n",(total_out2/3),(total_out2%3)) +
                                                        String.format("自責点:%.0f\n",total_points)+
                                                        String.format("防御率:%.0f\n",total_points*27/total_out2)+
                                                        String.format("奪三振数:%.0f個\n",total_strike_outs)+
                                                        String.format("被安打数:%.0f本\n",total_singlehit+total_twobasehit+total_threebasehit+total_homerun)+
                                                        String.format("単打:%.0f本|二塁打:%.0f本|三塁打:%.0f本|HR:%.0f本\n",total_singlehit,total_twobasehit,total_threebasehit,total_homerun)+
                                                        String.format("与四球:%.0f個\n",total_fourball)+
                                                        String.format("被打率:%.4f\n",(total_singlehit+total_twobasehit+total_threebasehit+total_homerun)/strokes)+
                                                        String.format("被出塁率:%.4f\n",(total_singlehit+total_twobasehit+total_threebasehit+total_homerun+total_fourball)/bats)+
                                                        String.format("被OPS:%.4f\n",((total_singlehit+total_twobasehit*2+total_threebasehit*3+total_homerun*4)/strokes)
                                                                +((total_singlehit+total_twobasehit+total_threebasehit+total_homerun+total_fourball)/bats))+
                                                        "1."+String.format("ゴロ率:%.2f",total_goro*100/(total_goro+total_liner+total_fly))+"%\n"+
                                                        "2."+String.format("ライナー率:%.2f",total_liner*100/(total_goro+total_liner+total_fly))+"%\n"+
                                                        "3."+String.format("フライ率:%.2f",total_fly*100/(total_goro+total_liner+total_fly))+"%\n"+
                                                        "ストライク:"+String.format("%.0f",total_foul+total_swing+total_miss_strike)+ "球\n"+
                                                        "ボール:"+String.format("%.0f",total_miss_ball)+ "球\n"+
                                                        "1.見逃し率:"+String.format("%.2f",total_miss_strike*100/(total_foul+total_swing+total_miss_strike))+
                                                        "%\n2.空振り率:"+String.format("%.2f",total_swing*100/(total_foul+total_swing+total_miss_strike))+
                                                        "%\n3.ファウル率:"+String.format("%.2f",total_foul*100/(total_foul+total_swing+total_miss_strike))+"%"
                                                );
                                                break;
                                        }
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
        });

        findViewById(R.id.Readbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] decide = {"はい","いいえ"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.Read)
                        .setItems(
                                decide,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                readData(Plname);
                                                //tv8.setText("読み込みました");
                                                break;
                                            case 1:
                                                break;
                                        }
                                    }
                                }
                        ).show();
            }
        });

        findViewById(R.id.Save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] decide = {"はい","いいえ"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.save)
                        .setItems(
                                decide,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                insertData(Plname);
                                                break;
                                            case 1:
                                                break;
                                        }
                                    }
                                }
                        ).show();
            }
        });

        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] decide = {"はい","いいえ"};
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.delete)
                        .setItems(
                                decide,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:

                                                break;
                                            case 1:
                                                break;
                                        }
                                    }
                                }
                        ).show();
            }
        });
    }
    public void button(final String message){
        final TextView tv = (TextView) findViewById(R.id.textView);
        final TextView tv2 = (TextView) findViewById(R.id.textView2);
        final CharSequence[] result = {"ゴロ", "ライナー", "フライ"};
        new AlertDialog.Builder(SecondActivity.this)
                .setTitle(R.string.dlg1_title)
                .setItems(
                        result,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                switch(which){
                                    case 0:
                                        goro+=1;
                                        total_goro+=1;
                                        break;
                                    case 1:
                                        liner+=1;
                                        total_liner+=1;
                                        break;
                                    case 2:
                                        fly+=1;
                                        total_fly+=1;
                                        break;
                                }
                                tv2.setText(message);
                                strokes+=1;
                                bats+=1;
                                strike=0;
                                ball=0;
                                if(out>=3){
                                    out=0;
                                    tv2.setText("3アウト、チェンジ！");
                                }
                                tv.setText("B:" + ball + "\nS:" + strike + "\nO:" + out);
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
            String sql = "select NAME,total_out2,total_points,total_strike_outs,total_foul,"+
            "total_swing,"+
            "total_miss_strike,"+
            "total_miss_ball,"+
            "total_goro,"+
            "total_liner,"+
            "total_fly,"+
            "total_fourball,"+
            "bats,"+
            "strokes,"+
            "total_singlehit,"+
            "total_twobasehit,"+
            "total_threebasehit,"+
            "total_homerun from Pitchers_table where NAME = ?;";
            Cursor cursor = db.rawQuery(sql, new String[]{name});
            cursor.moveToFirst();


            for (int i = 0; i < cursor.getCount(); i++) {
                total_out2 = cursor.getFloat(1);
                total_points = cursor.getFloat(2);
                total_strike_outs = cursor.getFloat(3);
                total_foul = cursor.getFloat(4);
                total_swing = cursor.getFloat(5);
                total_miss_strike = cursor.getFloat(6);
                total_miss_ball = cursor.getFloat(7);
                total_goro = cursor.getFloat(8);
                total_liner = cursor.getFloat(9);
                total_fly = cursor.getFloat(10);
                total_fourball = cursor.getFloat(11);
                bats = cursor.getFloat(12);
                strokes = cursor.getFloat(13);
                total_singlehit = cursor.getFloat(14);
                total_twobasehit = cursor.getFloat(15);
                total_threebasehit = cursor.getFloat(16);
                total_homerun = cursor.getFloat(17);
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
            cv.put("NAME",com);
            cv.put("total_out2",total_out2);
            cv.put("total_points",total_points);
            cv.put("total_strike_outs",total_strike_outs);
            cv.put("total_foul",total_foul);
            cv.put("total_swing",total_swing);
            cv.put("total_miss_strike",total_miss_strike);
            cv.put("total_miss_ball",total_miss_ball);
            cv.put("total_goro",total_goro);
            cv.put("total_liner",total_liner);
            cv.put("total_fly",total_fly);
            cv.put("total_fourball",total_fourball);
            cv.put("bats",bats);
            cv.put("strokes",strokes);
            cv.put("total_singlehit",total_singlehit);
            cv.put("total_twobasehit",total_twobasehit);
            cv.put("total_threebasehit",total_threebasehit);
            cv.put("total_homerun",total_homerun);
            try{
                db.update("Pitchers_table", cv, "NAME=?", new String[]{com});
                Toast.makeText(context,"データの上書きに成功しました",Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                db.insert("Pitchers_table", null, cv);
                Toast.makeText(context,"データの書き込みに成功しました",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context,"データ書き込みに失敗しました",Toast.LENGTH_SHORT).show();
        }
    }

}
