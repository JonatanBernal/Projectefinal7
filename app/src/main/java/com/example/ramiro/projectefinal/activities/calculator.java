package com.example.ramiro.projectefinal.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.database.login;
import com.example.ramiro.projectefinal.database.signin;

import static android.view.View.FOCUS_RIGHT;

public class calculator extends MainActivity implements View.OnClickListener {


    boolean decimal = true,sum = false,rest = false,mult = false,div = false;
    boolean toast = true;
    Double resultado = 0.0;
    HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scroll);



        Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bac,bdel,bdiv,bmult,brest,bsum,bpoint,bequal;


        b0 = (Button) findViewById(R.id.button0);
        b0.setOnClickListener(this);
        b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(this);
        b5 = (Button) findViewById(R.id.button5);
        b5.setOnClickListener(this);
        b6 = (Button) findViewById(R.id.button6);
        b6.setOnClickListener(this);
        b7 = (Button) findViewById(R.id.button7);
        b7.setOnClickListener(this);
        b8 = (Button) findViewById(R.id.button8);
        b8.setOnClickListener(this);
        b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(this);
        bac = (Button) findViewById(R.id.buttonreset);
        bac.setOnClickListener(this);
        bdel = (Button) findViewById(R.id.buttondel);
        bdel.setOnClickListener(this);
        bdiv = (Button) findViewById(R.id.buttondiv);
        bdiv.setOnClickListener(this);
        bmult = (Button) findViewById(R.id.buttonmult);
        bmult.setOnClickListener(this);
        brest = (Button) findViewById(R.id.buttonrest);
        brest.setOnClickListener(this);
        bsum = (Button) findViewById(R.id.buttonsum);
        bsum.setOnClickListener(this);
        bpoint = (Button) findViewById(R.id.buttonpoint);
        bpoint.setOnClickListener(this);
        bequal = (Button) findViewById(R.id.buttonequal);
        bequal.setOnClickListener(this);

        toolbar.setTitle("CALCULADORA");
        super.setItemChecked();
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_calc;
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) findViewById(R.id.text_view);
        String a = tv.getText().toString();

        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

        switch (v.getId()) {
            case R.id.button0:
                tv.setText(a+"0");
                break;
            case R.id.button1:
                tv.setText(a+"1");
                break;
            case R.id.button2:
                tv.setText(a+"2");
                break;
            case R.id.button3:
                tv.setText(a+"3");
                break;
            case R.id.button4:
                tv.setText(a+"4");
                break;
            case R.id.button5:
                tv.setText(a+"5");
                break;
            case R.id.button6:
                tv.setText(a+"6");
                break;
            case R.id.button7:
                tv.setText(a+"7");
                break;
            case R.id.button8:
                tv.setText(a+"8");
                break;
            case R.id.button9:
                tv.setText(a+"9");
                break;
            case R.id.buttonreset:
                decimal = true;
                tv.setText("");
                break;
            case R.id.buttondel:
                if (a.equals("")) {
                    return;
                }
                else {
                    tv.setText(tv.getText().toString().substring(0,tv.getText().toString().length()-1));
                }
                break;
            case R.id.buttonsum:
                if (a.equals("")) {
                    return;
                }
                else {
                    if (resultado == 0) {
                        resultado = Double.parseDouble(a);
                    }
                    else {
                        resultado = resultado + Double.parseDouble(a);
                    }
                    tv.setText("");
                    decimal = true;
                    sum = true;
                    rest = false;
                    mult = false;
                    div = false;
                }
                break;
            case R.id.buttonrest:
                if (a.equals("")) {
                    tv.setText("-");
                }
                else {
                    if (resultado == 0) {
                        resultado = Double.parseDouble(a);
                    }
                    else {
                        resultado = resultado - Double.parseDouble(a);
                    }
                    tv.setText("");
                    decimal = true;
                    sum = false;
                    rest = true;
                    mult = false;
                    div = false;
                }
                break;
            case R.id.buttonpoint:
                if (a.equals("")) {
                    return;
                }
                else if(decimal) {
                    decimal = false;
                    tv.setText(a + ".");
                }
                break;
            case R.id.buttonmult:
                if (a.equals("")) {
                    return;
                }
                else {
                    if (resultado == 0) {
                        resultado = Double.parseDouble(a);
                    }
                    else {
                        resultado = resultado * Double.parseDouble(a);
                    }
                    tv.setText("");
                    decimal = true;
                    sum = false;
                    rest = false;
                    mult = true;
                    div = false;
                }
                break;
            case R.id.buttondiv:
                if (a.equals("")) {
                    return;
                }
                else {
                    if (resultado == 0) {
                        resultado = Double.parseDouble(a);
                    }
                    else {
                        resultado = resultado / Double.parseDouble(a);
                    }
                    tv.setText("");
                    decimal = true;
                    sum = false;
                    rest = false;
                    mult = false;
                    div = true;
                }
                break;
            case R.id.buttonequal:
                if (a.equals(""))  {
                    return;
                }
                else if (a.equals("Infinity") || (div && a.equals("0"))) {
                    if (toast) {
                        Toast.makeText(getApplicationContext(),"ERROR EN EL CÁLCULO",Toast.LENGTH_SHORT).show();
                        tv.setText("ERROR");
                    }
                    else {
                        int mId = 1;
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(this)
                                        .setSmallIcon(R.drawable.ic_turn_notifications_on_button)
                                        .setContentTitle("CUIDADO")
                                        .setContentText("ERROR EN EL CÁLCULO");

                        Intent resultIntent = new Intent(getApplicationContext(), calculator.class);

                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addParentStack(calculator.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );

                        mBuilder.setContentIntent(resultPendingIntent);

                        mNotificationManager.notify(mId,mBuilder.build());
                        tv.setText("ERROR");

                    }
                }
                else {
                    if (sum) {
                        resultado = Double.parseDouble(a) + resultado;
                        resultado = redondearDecimales(resultado, 6);
                        tv.setText(resultado.toString());
                        sum = false;
                    } else if (mult) {
                        resultado = Double.parseDouble(a) * resultado;
                        resultado = redondearDecimales(resultado, 6);
                        tv.setText(resultado.toString());
                        mult = false;
                    } else if (div) {
                        resultado = resultado / Double.parseDouble(a);
                        resultado = redondearDecimales(resultado, 6);
                        tv.setText(resultado.toString());
                        div = false;
                    } else if (rest) {
                        resultado = resultado - Double.parseDouble(a);
                        resultado = redondearDecimales(resultado, 6);
                        tv.setText(resultado.toString());
                        rest = false;
                    }

                }
                resultado =0.0;
                break;
        }

    }

    private static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        setTitle("CALCULATOR");
        return true;
    }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
       MenuItem to = menu.findItem(R.id.menu_item_toast);
       MenuItem est = menu.findItem(R.id.menu_item_estado);
       if (toast) to.setChecked(true);
       else est.setChecked(true);
       return true;
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_phone) {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
            startActivity(i);
        }
        else if (item.getItemId() == R.id.menu_item_crome) {
            Intent j = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            startActivity(j);
        }
        else if (item.getItemId() == R.id.menu_item_logout) {

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("myBoolean", false);
            editor.apply();
            Intent t = new Intent(this,login.class);
            startActivity(t);
            finish();
        }
        else if (item.getItemId() == R.id.menu_item_toast) {
            item.setChecked(true);
            toast = true;
        }
        else if (item.getItemId() == R.id.menu_item_estado) {
            item.setChecked(true);
            toast = false;;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView t = (TextView) findViewById(R.id.text_view);
        outState.putString("texto", t.getText().toString());
        outState.putBoolean("decimal", decimal);
        outState.putBoolean("suma", sum);
        outState.putBoolean("resta", rest);
        outState.putBoolean("multipli", mult);
        outState.putBoolean("divi", div);
        outState.putBoolean("notify", toast);
        outState.putDouble("result", resultado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView t = (TextView) findViewById(R.id.text_view);
        t.setText(savedInstanceState.getString("texto"));
        decimal = savedInstanceState.getBoolean("decimal");
        sum = savedInstanceState.getBoolean("suma");
        rest = savedInstanceState.getBoolean("resta");
        mult = savedInstanceState.getBoolean("multipli");
        div = savedInstanceState.getBoolean("divi");
        toast = savedInstanceState.getBoolean("notify");
        resultado = savedInstanceState.getDouble("result");;
    }
}
