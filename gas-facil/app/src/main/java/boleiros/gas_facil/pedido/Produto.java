package boleiros.gas_facil.pedido;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.ProdutoAdapter;
import boleiros.gas_facil.adapter.RecyclerItemClickListener;
import boleiros.gas_facil.dialogos.QuantidadeDeProdutoDialogo;
import boleiros.gas_facil.modelo.ProdutoManager;
import boleiros.gas_facil.util.ActivityStore;

/**
 * A placeholder fragment containing a simple view.
 */
public class Produto extends Fragment implements AdapterView.OnItemSelectedListener  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private RecyclerView mRecyclerView;
    private ProdutoAdapter mAdapter;
    private List<boleiros.gas_facil.modelo.Produto> produtos;

    public void consultaAoParse(String filtro) {
        ParseQuery<boleiros.gas_facil.modelo.Produto> query = ParseQuery.getQuery("Produto");
        if(filtro.equals("precoAlto")){
            query.orderByDescending("price_int");

        }   if(filtro.equals("precoBaixo")){
            query.orderByAscending("price_int");

        }   if(filtro.equals("data")){
            query.orderByDescending("createdAt");

        }
        //query.setLimit(10);
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<boleiros.gas_facil.modelo.Produto>() {
            @Override
            public void done(List<boleiros.gas_facil.modelo.Produto> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    ProdutoManager.getInstance().setProdutos(parseObjects);
                    ActivityStore.getInstance(getActivity()).setProdutos(parseObjects);
                    mAdapter = new ProdutoAdapter(parseObjects, R.layout.card_layout,getActivity());
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
        View rootView = inflater.inflate(R.layout.fragment_produto, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mAdapter = new ProdutoAdapter(ProdutoManager.getInstance().getprodutos(), R.layout.card_layout, this.getActivity());
//        mRecyclerView.setAdapter(mAdapter);
        //consultaAoParse();
        Spinner spinnerEstados = (Spinner) rootView.findViewById(R.id.spinner2);
        spinnerEstados.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_opcoes_ordenacao_produtos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapter);
        consultaAoParse("data");
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        QuantidadeDeProdutoDialogo newFragment = new QuantidadeDeProdutoDialogo();
                        ActivityStore.getInstance(getActivity()).setProduto(ProdutoManager.getInstance().getprodutos().get(position));
                        newFragment.show(getFragmentManager(), "quantidade");
                    }
                })
        );

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("data")) {
            consultaAoParse("data");
        }
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Preço mais alto")) {
            consultaAoParse("precoAlto");
        }
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Preço mais baixo")) {
            consultaAoParse("precoBaixo");
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
