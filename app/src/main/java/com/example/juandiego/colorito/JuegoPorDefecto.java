package com.example.juandiego.colorito;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JuegoPorDefecto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JuegoPorDefecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JuegoPorDefecto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JuegoPorDefecto() {
        // Required empty public constructor
    }

    View view;
    puente miPuente;
    Activity miActivity;
    /////////////////////////////////////////////////////

    TextView correcta, incorrecta, palabraColor, desplegadas, faltantes, txtporcentaje, txtTime;
    Button btn1, btn2, btn3, btn4;
    ImageButton btnvolver;
    ArrayList<String> listaColores;
    ArrayList<String> listaPalabras;
    int n;//numeros aleatorios
    int k;  //auxiliar;
    int[] numeros;
    int[] resultado;
    Random rnd;
    int numero;
    int numeroColor;
    int correctasInt = 0, incorrectasInt = 0;
    int palabras = 0, falta = 3;
    double porciento;

    CountDownTimer contador;
    int tiempoPalabra = 4000;//3 Segundos

    Conexion conn;

    ///////////////////////////////////////////////7777

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JuegoPorDefecto.
     */
    // TODO: Rename and change types and number of parameters
    public static JuegoPorDefecto newInstance(String param1, String param2) {
        JuegoPorDefecto fragment = new JuegoPorDefecto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_juego_por_defecto, container, false);

        conn=new Conexion(getContext(),"bd_puntajes",null,1);

        listaColores = new ArrayList<>();
        listaPalabras = new ArrayList<>();

        btnvolver = view.findViewById(R.id.btnvolver);
        correcta = view.findViewById(R.id.correcta);
        incorrecta = view.findViewById(R.id.incorrecta);
        palabraColor = view.findViewById(R.id.palabraColor);
        faltantes = view.findViewById(R.id.palabrasFal);
        desplegadas = view.findViewById(R.id.palabrasDes);
        txtTime = view.findViewById(R.id.tiempo);
        txtporcentaje = view.findViewById(R.id.porcentaje);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);

        desplegadas.setText("Desplegadas: " + palabras);
        faltantes.setText("Intentos: " + falta);

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miPuente.puente(2);
            }
        });

        contador = new CountDownTimer(tiempoPalabra, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int segundosRestantes = (int) millisUntilFinished / 1000;
                txtTime.setText("Tiempo: " + Integer.toString(segundosRestantes));
            }

            @Override
            public void onFinish() {
                if (falta != 0) {
                    comprobar(5);
                    this.start();
                } else {
                    this.cancel();
                }
            }
        };
        contador.start();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (falta != 0) {
                    comprobar(1);
                    contador.start();
                } else {
                    cuadroDialogo(view);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (falta != 0) {
                    comprobar(2);
                    contador.start();
                } else {
                    cuadroDialogo(view);
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (falta != 0) {
                    comprobar(3);
                    contador.start();
                } else {
                    cuadroDialogo(view);
                }

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (falta != 0) {
                    comprobar(4);
                    contador.start();
                } else {
                    cuadroDialogo(view);
                }
            }
        });
        llenar();

        return view;
    }

    private void llenar() {
        listaColores.add("#FF3F51B5");
        listaColores.add("#FFFF4081");
        listaColores.add("#FF449F30");
        listaColores.add("#FF6D309F");

        listaPalabras.add("Azul");
        listaPalabras.add("Rosado");
        listaPalabras.add("Verde");
        listaPalabras.add("Morado");

        generarNumero();
    }

    private void generarNumero() {
        numero = (int) (Math.random() * 4);
        numeroColor = (int) (Math.random() * 4);
        n = 4;
        k = n;
        numeros = new int[n];
        resultado = new int[n];
        rnd = new Random();
        int res;
        for (int i = 0; i < n; i++) {
            numeros[i] = i + 1;
        }
        for (int i = 0; i < n; i++) {
            res = rnd.nextInt(k);
            resultado[i] = numeros[res];
            numeros[res] = numeros[k - 1];
            k--;

        }
        btn1.setText(listaPalabras.get(resultado[0] - 1));
        btn2.setText(listaPalabras.get(resultado[1] - 1));
        btn3.setText(listaPalabras.get(resultado[2] - 1));
        btn4.setText(listaPalabras.get(resultado[3] - 1));
        btn1.setBackgroundColor(Color.parseColor(listaColores.get(resultado[0] - 1)));
        btn2.setBackgroundColor(Color.parseColor(listaColores.get(resultado[1] - 1)));
        btn3.setBackgroundColor(Color.parseColor(listaColores.get(resultado[2] - 1)));
        btn4.setBackgroundColor(Color.parseColor(listaColores.get(resultado[3] - 1)));


        palabraColor.setText(listaPalabras.get(numero));
        palabraColor.setTextColor(Color.parseColor(listaColores.get(numeroColor)));

    }

    public void comprobar(int numeroBtn) {
        palabras++;
        porciento = correctasInt * 100 / palabras;
        switch (numeroBtn) {
            case 1:
                if (listaColores.get(resultado[0] - 1) == listaColores.get(numeroColor)) {
                    correctasInt++;
                } else {
                    incorrectasInt++;
                    falta--;
                }
                break;
            case 2:
                if (listaColores.get(resultado[1] - 1) == listaColores.get(numeroColor)) {
                    correctasInt++;
                } else {
                    incorrectasInt++;
                    falta--;
                }
                break;
            case 3:
                if (listaColores.get(resultado[2] - 1) == listaColores.get(numeroColor)) {
                    correctasInt++;
                } else {
                    incorrectasInt++;
                    falta--;
                }
                break;
            case 4:
                if (listaColores.get(resultado[3] - 1) == listaColores.get(numeroColor)) {
                    correctasInt++;
                } else {
                    incorrectasInt++;
                    falta--;
                }
                break;
            case 5:
                incorrectasInt++;
                falta--;
                break;
        }
        contador.cancel();
        desplegadas.setText("Desplegadas: " + palabras);
        faltantes.setText("Intentos: " + falta);
        correcta.setText("Correctas: " + correctasInt);
        incorrecta.setText("Incorrectas: " + incorrectasInt);
        txtporcentaje.setText("Porcentaje: " + porciento);
        generarNumero();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.miActivity = (Activity) context;
            miPuente = (puente) this.miActivity;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void cuadroDialogo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Puntuación");
        String mensaje = "Correctas: " + correctasInt +"\n"+
                "Incorrectas: " + incorrectasInt +"\n"+
                "Porcentaje De Reacción: " + porciento;
        builder.setMessage(mensaje);
        //builder.setIcon(R.drawable.logo);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrar();
                miPuente.puente(2);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void registrar() {
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.DESPLEGADAS,Integer.toString(palabras));
        values.put(Utilidades.CORRECTAS,Integer.toString(correctasInt));
        values.put(Utilidades.INCORRECTAS,Integer.toString(incorrectasInt));
        values.put(Utilidades.INTENTOS,"3");

        Long resultado=db.insert(Utilidades.NOMBRE_TABLA,Utilidades.DESPLEGADAS,values);
        
        db.close();
    }
}
