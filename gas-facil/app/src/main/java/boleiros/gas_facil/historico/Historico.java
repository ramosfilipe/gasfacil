package boleiros.gas_facil.historico;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.HistoricoAdapter;
import boleiros.gas_facil.adapter.ProdutoAdapter;
import boleiros.gas_facil.adapter.RecyclerItemClickListener;
import boleiros.gas_facil.dialogos.QuantidadeDeProdutoDialogo;
import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.modelo.ProdutoManager;
import boleiros.gas_facil.util.ActivityStore;


public class Historico extends Fragment {

    private RecyclerView mRecyclerView;
    private HistoricoAdapter mAdapter;
    private List<boleiros.gas_facil.modelo.Produto> produtos;




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

    public Historico() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void consultaAoParse() {
        ParseQuery<Pedido> query = ParseQuery.getQuery("Pedido");
        query.include("comprador");
        query.include("produto");
        query.whereEqualTo("comprador", ParseUser.getCurrentUser());
        query.orderByDescending("createdAt");
        //query.setLimit(10);
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<Pedido>() {
            @Override
            public void done(List<Pedido> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    //ProdutoManager.getInstance().setProdutos(parseObjects);
                    mAdapter = new HistoricoAdapter(parseObjects, R.layout.elemento_listview);
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

//        mAdapter = new ProdutoAdapter(ProdutoManager.getInstance().getprodutos(), R.layout.card_layout, this.getActivity());
//        mRecyclerView.setAdapter(mAdapter);
        consultaAoParse();


        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }



}
