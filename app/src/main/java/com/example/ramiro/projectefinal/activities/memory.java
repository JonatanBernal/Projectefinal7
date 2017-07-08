package com.example.ramiro.projectefinal.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.database.login;
import com.example.ramiro.projectefinal.flipper.Coolimagefliper;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Exchanger;

import static com.example.ramiro.projectefinal.R.id.imageView;
import static com.example.ramiro.projectefinal.R.id.imageView1;

public class memory extends MainActivity {

    private String PREFS_NAME = "principal";

    ImageView v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16;

    Coolimagefliper flipper;
    Drawable postcard;
    Drawable goku;
    Drawable krilin;
    Drawable vegeta;
    Drawable bulma;
    Drawable gohan;
    Drawable mutenroshi;
    Drawable frezza;
    Drawable picolo;
    int cont = 0,moves = 0,id1 = -1,id2 = -1,total = 0,image1 = -1,image2 = -1;
    TextView tv;

    int [] imagenes = {R.drawable.goku,R.drawable.krilin,R.drawable.vegeta,
            R.drawable.bulma,R.drawable.gohan,R.drawable.mutenroshi,R.drawable.freezer,R.drawable.picolo};


    int [] imagenes_assignadas = new int[16];



    void initBoard(){
        tv = (TextView) findViewById(R.id.movs);
        flipper = new Coolimagefliper(this);
        postcard = getResources().getDrawable(R.drawable.postcard);
        goku = getResources().getDrawable(R.drawable.goku);
        krilin = getResources().getDrawable(R.drawable.krilin);
        vegeta = getResources().getDrawable(R.drawable.vegeta);
        bulma = getResources().getDrawable(R.drawable.bulma);
        gohan = getResources().getDrawable(R.drawable.gohan);
        mutenroshi = getResources().getDrawable(R.drawable.mutenroshi);
        frezza = getResources().getDrawable(R.drawable.freezer);
        picolo = getResources().getDrawable(R.drawable.picolo);

        moves = 0;
        total = 0;
        tv.setText("MOVES: "+String.valueOf(moves));

        v1 = (ImageView) findViewById(R.id.imageView1);
        v2 = (ImageView) findViewById(R.id.imageView2);
        v3 = (ImageView) findViewById(R.id.imageView3);
        v4 = (ImageView) findViewById(R.id.imageView4);
        v5 = (ImageView) findViewById(R.id.imageView5);
        v6 = (ImageView) findViewById(R.id.imageView6);
        v7 = (ImageView) findViewById(R.id.imageView7);
        v8 = (ImageView) findViewById(R.id.imageView8);
        v9 = (ImageView) findViewById(R.id.imageView9);
        v10 = (ImageView) findViewById(R.id.imageView10);
        v11 = (ImageView) findViewById(R.id.imageView11);
        v12 = (ImageView) findViewById(R.id.imageView12);
        v13 = (ImageView) findViewById(R.id.imageView13);
        v14 = (ImageView) findViewById(R.id.imageView14);
        v15 = (ImageView) findViewById(R.id.imageView15);
        v16 = (ImageView) findViewById(R.id.imageView16);

        v1.setVisibility(v1.VISIBLE);
        v2.setVisibility(v2.VISIBLE);
        v3.setVisibility(v3.VISIBLE);
        v4.setVisibility(v4.VISIBLE);
        v5.setVisibility(v5.VISIBLE);
        v6.setVisibility(v6.VISIBLE);
        v7.setVisibility(v7.VISIBLE);
        v8.setVisibility(v8.VISIBLE);
        v9.setVisibility(v9.VISIBLE);
        v10.setVisibility(v10.VISIBLE);
        v11.setVisibility(v11.VISIBLE);
        v12.setVisibility(v12.VISIBLE);
        v13.setVisibility(v13.VISIBLE);
        v14.setVisibility(v14.VISIBLE);
        v15.setVisibility(v15.VISIBLE);
        v16.setVisibility(v16.VISIBLE);


        flipper.flipImage(postcard, v1);
        flipper.flipImage(postcard, v2);
        flipper.flipImage(postcard, v3);
        flipper.flipImage(postcard, v4);
        flipper.flipImage(postcard, v5);
        flipper.flipImage(postcard, v6);
        flipper.flipImage(postcard, v7);
        flipper.flipImage(postcard, v8);
        flipper.flipImage(postcard, v9);
        flipper.flipImage(postcard, v10);
        flipper.flipImage(postcard, v11);
        flipper.flipImage(postcard, v12);
        flipper.flipImage(postcard, v13);
        flipper.flipImage(postcard, v14);
        flipper.flipImage(postcard, v15);
        flipper.flipImage(postcard, v16);







        Random ran = new Random();
        boolean [] usadas = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
        int rand;

        for (int i=0; i <16; ++i){

            rand = Math.abs(ran.nextInt() % 8);

            if(!usadas[rand]){
                imagenes_assignadas[i] = imagenes[rand];
                usadas[rand] = true;
            }
            else if (!usadas[rand+8]){
                imagenes_assignadas[i] = imagenes[rand];
                usadas[rand+8] = true;

            }
            else --i;


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        super.setItemChecked();
        toolbar.setTitle("MEMORY");

        initBoard();

    }

    public void foodClicked(final View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imageView1:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[0]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[0];
                    else id2 = imagenes_assignadas[0];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView2:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[1]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[1];
                    else id2 = imagenes_assignadas[1];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView3:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[2]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[2];
                    else id2 = imagenes_assignadas[2];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView4:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[3]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[3];
                    else id2 = imagenes_assignadas[3];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView5:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[4]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[4];
                    else id2 = imagenes_assignadas[4];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView6:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[5]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[5];
                    else id2 = imagenes_assignadas[5];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView7:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[6]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[6];
                    else id2 = imagenes_assignadas[6];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView8:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[7]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[7];
                    else id2 = imagenes_assignadas[7];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView9:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[8]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[8];
                    else id2 = imagenes_assignadas[8];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView10:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[9]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[9];
                    else id2 = imagenes_assignadas[9];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView11:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[10]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[10];
                    else id2 = imagenes_assignadas[10];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView12:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[11]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[11];
                    else id2 = imagenes_assignadas[11];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView13:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[12]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[12];
                    else id2 = imagenes_assignadas[12];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView14:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[13]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[13];
                    else id2 = imagenes_assignadas[13];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView15:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[14]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[14];
                    else id2 = imagenes_assignadas[14];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
            case R.id.imageView16:
                if (cont < 2 && image1 != id) {
                    flipper.flipImage(getResources().getDrawable(imagenes_assignadas[15]), ((ImageView) view));
                    ++cont;
                    ++moves;
                    if (id1 == -1) id1 = imagenes_assignadas[15];
                    else id2 = imagenes_assignadas[15];
                    tv.setText("MOVES: "+String.valueOf(moves));
                    if (image1 == -1) image1 = id;
                    else image2 = id;
                }
                break;
        }
        Log.v("activity","valor de cont "+String.valueOf(cont));
        if (cont == 2) {
            final ImageView aux1 = (ImageView) findViewById(image1);
            final ImageView aux2 = (ImageView) findViewById(image2);
            if (id1 == id2) {
                Log.v("activity","ids iguales");
                ++total;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v("thread","abtes");

                            aux1.setVisibility(v1.INVISIBLE);
                            aux2.setVisibility(v2.INVISIBLE);
                            Log.v("thrread","despues");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }},2000);


            }
            else {
                Log.v("activity","ids no iguales");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            flipper.flipImage(postcard, aux1);
                            flipper.flipImage(postcard, aux2);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }},1500);
            }
            cont = 0;
            id1 = -1;
            id2 = -1;
            image1 = -1;
            image2 = -1;
        }
        if (total == 8) {
            final AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle("JUEGO COMPLETADO");
            d.setMessage("MOVES: "+String.valueOf(moves));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        d.show();
                        initBoard();

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }},2500);
        }

    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_memory;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mem_menu,menu);
        setTitle("MEMORY");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_logout3) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("myBoolean", false);
            editor.apply();
            Intent t = new Intent(this,login.class);
            startActivity(t);
            finish();
        }
        if (item.getItemId() == R.id.menu_item_restart) {
            initBoard();
        }

        return true;

    }
}
