package boleiros.gas_facil.historico;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.HistoricoAdapter;
import boleiros.gas_facil.modelo.Pedido;


public class Historico extends Fragment implements AdapterView.OnItemSelectedListener {

    final private long UM_DIA_EM_MILLISEGUNDOS = 86400000;
    TextView historicoVazio, historicoVazioBaixo;
    private RecyclerView mRecyclerView;
    private HistoricoAdapter mAdapter;


    public Historico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Historico.
     */
    // TODO: Rename and change types and number of parameters
    public static Historico newInstance(String param1, String param2) {
        Historico fragment = new Historico();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void consultaAoParse(String range) {
        ParseQuery<Pedido> query = ParseQuery.getQuery("Pedido");
        query.include("comprador");
        query.include("produto");
        query.whereEqualTo("comprador", ParseUser.getCurrentUser());
        query.orderByDescending("createdAt");
        if (range.equals("semana")) {
            Calendar calendar = Calendar.getInstance();
            long aux = calendar.getTimeInMillis() - (7 * UM_DIA_EM_MILLISEGUNDOS);
            calendar.setTimeInMillis(aux);
            Date date = calendar.getTime();
            query.whereGreaterThanOrEqualTo("createdAt", date);
        }

        if (range.equals("mes")) {
            Calendar calendar = Calendar.getInstance();
            long aux = calendar.getTimeInMillis() - (30 * UM_DIA_EM_MILLISEGUNDOS);
            calendar.setTimeInMillis(aux);
            Date date = calendar.getTime();
            query.whereGreaterThanOrEqualTo("createdAt", date);
        }
        //query.setLimit(10);
        final ProgressDialog pDialog = ProgressDialog.show(Historico.this.getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<Pedido>() {
            @Override
            public void done(List<Pedido> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    mAdapter = new HistoricoAdapter(parseObjects, R.layout.elemento_listview);
                    if (parseObjects.size() > 0) {
                        historicoVazio.setText("");
                        historicoVazioBaixo.setText("");
                    } else {
                        historicoVazio.setText("Você ainda não comprou");
                        historicoVazioBaixo.setText("nenhum produto.");
                    }

                    mRecyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "Ops... Verifique sua internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historico, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listViewHistorico);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        historicoVazio = (TextView) rootView.findViewById(R.id.textViewHistoricoVazio);
        historicoVazioBaixo = (TextView) rootView.findViewById(R.id.textViewHistoricoVazioBaixo);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "roboto.ttf");
        historicoVazio.setTypeface(tf);
        historicoVazioBaixo.setTypeface(tf);
        Spinner spinnerEstados = (Spinner) rootView.findViewById(R.id.spinner);
        spinnerEstados.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_opcoes_data, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapter);
        consultaAoParse("tudo");


        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("7 dias atrás")) {
            consultaAoParse("semana");
        }
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("30 dias atrás")) {
            consultaAoParse("mes");
        }
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("tudo")) {
            consultaAoParse("tudo");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
