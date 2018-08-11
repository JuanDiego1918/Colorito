package com.example.juandiego.colorito;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AllFragments, puente {

    Fragment miFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conexion conn = new Conexion(this, "bd_Colorito", null, 1);

        miFragment = new PaginaPrincipal();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, miFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void puente(int numeroPantalla) {
        switch (numeroPantalla) {
            case 1:
                miFragment = new JuegoPorDefecto();
                break;
            case 2:
                miFragment = new PaginaPrincipal();
                break;
            case 3:
                miFragment = new ListaPuntajes();
                break;
            case 4:
                miFragment=new Ajuste();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, miFragment).commit();
    }

    @Override
    public void enviarObjeto(int tipo) {
        Bundle miBundle=new Bundle();
        miBundle.putInt("tipo",tipo);
        miFragment=new JuegoAjuste();
        miFragment.setArguments(miBundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, miFragment).commit();
    }
}
