package com.example.juandiego.colorito;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaPuntajes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaPuntajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPuntajes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaPuntajes() {
        // Required empty public constructor
    }

    Conexion conn;

    ImageButton volver;

    ArrayList<PuntajeVo> listaPuntajes;
    RecyclerView recyclerView;
    View view;
    Activity activity;
    puente miPuente;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPuntajes.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPuntajes newInstance(String param1, String param2) {
        ListaPuntajes fragment = new ListaPuntajes();
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
        view=inflater.inflate(R.layout.fragment_lista_puntajes, container, false);

        recyclerView=view.findViewById(R.id.mejorespuntajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        volver=view.findViewById(R.id.btnvolver);

        conn=new Conexion(getContext(),"bd_puntajes",null,1);

        consulta();

        AdapterPuntaje miAdapterPuntaje=new AdapterPuntaje(listaPuntajes);
        recyclerView.setAdapter(miAdapterPuntaje);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miPuente.puente(2);
            }
        });
        return view;
    }

    private void consulta() {
        SQLiteDatabase db=conn.getReadableDatabase();

        listaPuntajes=new ArrayList<>();
        PuntajeVo puntajeVo=null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.NOMBRE_TABLA +"ORDER BY "+Utilidades.CORRECTAS+"DESC",null);

        while (cursor.moveToNext()){
            puntajeVo=new PuntajeVo();
            puntajeVo.setDesplegas(cursor.getString(0));
            puntajeVo.setCorrectas(cursor.getString(1));
            puntajeVo.setIncorrectas(cursor.getString(2));
            puntajeVo.setFallidos(cursor.getString(3));

            listaPuntajes.add(puntajeVo);
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
            this.activity = (Activity) context;
            miPuente = (puente) this.activity;
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
