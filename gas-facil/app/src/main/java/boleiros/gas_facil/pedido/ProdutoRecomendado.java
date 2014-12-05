package boleiros.gas_facil.pedido;

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

import java.util.ArrayList;
import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.ProdutoAdapter;
import boleiros.gas_facil.adapter.RecyclerItemClickListener;
import boleiros.gas_facil.dialogos.QuantidadeDeProdutoDialogo;
import boleiros.gas_facil.modelo.*;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.util.ActivityStore;


public class ProdutoRecomendado extends Fragment {
    private RecyclerView mRecyclerView;
    private ProdutoAdapter mAdapter;
    private List<boleiros.gas_facil.modelo.Produto> produtos;

    public void consultaAoParse() {
        final ArrayList<Produto> produtos = new ArrayList<Produto>();
        ParseQuery<Estatistica> query = ParseQuery.getQuery("Estatistica");
        query.include("cliente");
        query.include("produto");
        query.whereEqualTo("cliente", ParseUser.getCurrentUser());
        query.orderByDescending("quantidadeComprada");
        query.setLimit(3);
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<Estatistica>() {
            @Override
            public void done(List<Estatistica> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    System.out.println(parseObjects.size()+"");

                    if(parseObjects.size()==1){
                        System.out.println("pos1");
                        produtos.add(0,(Produto) parseObjects.get(0).getProduto());
                    }
                    if(parseObjects.size()==2){
                        System.out.println("pos2");

                        produtos.add(0,(Produto) parseObjects.get(0).getProduto());
                        produtos.add(1,(Produto) parseObjects.get(1).getProduto());
                    }
                    if(parseObjects.size()==3){
                        System.out.println("pos3");

                        produtos.add(0,(Produto) parseObjects.get(2).getProduto());
                        produtos.add(1,(Produto) parseObjects.get(1).getProduto());
                        produtos.add(2,(Produto) parseObjects.get(0).getProduto());
                    }


                    ProdutoManager.getInstance().setProdutos(produtos);
                    mAdapter = new ProdutoAdapter(produtos, R.layout.card_layout,getActivity());
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
        View rootView = inflater.inflate(R.layout.fragment_produto_recomendado, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listRecomendado);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mAdapter = new ProdutoAdapter(ProdutoManager.getInstance().getprodutos(), R.layout.card_layout, this.getActivity());
//        mRecyclerView.setAdapter(mAdapter);
        consultaAoParse();
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
}
