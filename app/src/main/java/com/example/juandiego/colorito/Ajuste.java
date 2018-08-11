package com.example.juandiego.colorito;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ajuste.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ajuste#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ajuste extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Ajuste() {
        // Required empty public constructor
    }

    String[] tipo = {"Seleccione", "Tiempo", "Intentos"};
    View view;
    EditText tiempoPalabra, tiempoTotal, intentos;
    int tiempoP, tiempoT, intentoInt, tipoInt;
    Activity miActivity;
    puente miPuente;
    ImageButton volver;
    Spinner miSpinner;

    TableRow uno, dos;
    Button jugar;
    SharedPreferences preferences;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ajuste.
     */
    // TODO: Rename and change types and number of parameters
    public static Ajuste newInstance(String param1, String param2) {
        Ajuste fragment = new Ajuste();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ajuste, container, false);

        preferences = this.getActivity().getSharedPreferences("Credenciales", getContext().MODE_PRIVATE);

        miSpinner = view.findViewById(R.id.tipo);
        uno = view.findViewById(R.id.primero);
        dos = view.findViewById(R.id.dos);
        volver = view.findViewById(R.id.btnvolver);
        tiempoPalabra = view.findViewById(R.id.tiempoPalabra);
        intentos = view.findViewById(R.id.intentosNumero);
        tiempoTotal = view.findViewById(R.id.tiempoTotal);
        jugar = view.findViewById(R.id.jugar);

        tiempoPalabra.setText(""+preferences.getInt("tiempoPalabra",0));
        intentos.setText(""+preferences.getInt("intentos",0));
        tiempoTotal.setText(""+preferences.getInt("tiempoTotal",0));

        miSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tipo));

        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {
                switch (posicion) {
                    case 1:
                        uno.setVisibility(View.INVISIBLE);
                        dos.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        uno.setVisibility(View.VISIBLE);
                        dos.setVisibility(View.INVISIBLE);
                        break;
                }
                tipoInt = posicion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miPuente.puente(2);
            }
        });

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empezarJuego();
            }
        });


        return view;
    }

    private void empezarJuego() {
        SharedPreferences.Editor editor = preferences.edit();

        tiempoP = Integer.parseInt(tiempoPalabra.getText().toString());
        editor.putInt("tiempoPalabra", tiempoP);
        switch (tipoInt) {
            case 1:
                tiempoT = Integer.parseInt(tiempoTotal.getText().toString());
                editor.putInt("tiempoTotal", tiempoT);
                break;
            case 2:
                intentoInt = Integer.parseInt(intentos.getText().toString());
                editor.putInt("intentos", intentoInt);
        }
        editor.commit();
        if (tipoInt!=0){
            miPuente.enviarObjeto(tipoInt);
        }else{
            Toast.makeText(getContext(),"Seleccione Tipo de juego",Toast.LENGTH_SHORT).show();
        }
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
}
