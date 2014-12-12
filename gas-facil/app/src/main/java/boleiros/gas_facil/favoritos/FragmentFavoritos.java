package boleiros.gas_facil.favoritos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import boleiros.gas_facil.dialogos.SelecionarFavoritoDialogo;
import boleiros.gas_facil.modelo.Favorito;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.modelo.ProdutoManager;
import boleiros.gas_facil.util.ActivityStore;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFavoritos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFavoritos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFavoritos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private ImageView botaoAdicionar;
    private TextView vazioCima, vazioBaixo;
    private ProdutoAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFavoritos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFavoritos newInstance(String param1, String param2) {
        FragmentFavoritos fragment = new FragmentFavoritos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentFavoritos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void consultaAoParse() {
        final ArrayList<Produto> produtos = new ArrayList<Produto>();
        ParseQuery<Favorito> query = ParseQuery.getQuery("Favorito");
        query.include("usuario");
        query.include("produto");
        query.orderByAscending("createdAt");
        query.whereEqualTo("usuario", ParseUser.getCurrentUser());
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<Favorito>() {
            @Override
            public void done(List<Favorito> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    int numeroProdutos = parseObjects.size();
                    if(numeroProdutos > 0 ){
                        vazioCima.setText("");
                        vazioBaixo.setText("");
                    } else {
                        vazioCima.setText("Você ainda não adicionou");
                        vazioBaixo.setText("produtos favoritos.");
                    }
                    for (int i = 0; i < parseObjects.size() ; i++) {
                        produtos.add(i,(Produto) parseObjects.get(i).getProduto());
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment_favoritos, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listFavoritos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        vazioCima = (TextView)rootView.findViewById(R.id.textViewFavoritoVazioCima);
        vazioBaixo = (TextView)rootView.findViewById(R.id.textViewFavoritoVazioBaixo);
        botaoAdicionar = (ImageView)rootView.findViewById(R.id.imageViewAdicionarFavorito);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "roboto.ttf");
        vazioBaixo.setTypeface(tf);
        vazioCima.setTypeface(tf);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarFavoritoDialogo frag = new SelecionarFavoritoDialogo();
                frag.show(getFragmentManager(),"favoritos");
            }
        });
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
