package boleiros.gas_facil.perfil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import boleiros.gas_facil.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Perfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Perfil() {
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
    public void setInformacoes(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, com.parse.ParseException e) {
                ParseObject user = parseUsers.get(0);
                TextView txtViewEndereco = (TextView)getActivity().findViewById(R.id.textViewEnderecoPerfil);
                EditText editTextEndereco = (EditText)getActivity().findViewById(R.id.editTextEndereco);
                TextView txtViewNome = (TextView)getActivity().findViewById(R.id.textView2);
                EditText editTextNome = (EditText)getActivity().findViewById(R.id.editTextNome);
                TextView txtViewTelefone = (TextView)getActivity().findViewById(R.id.textViewTelefonePerfil);
                EditText editTextTelefone = (EditText)getActivity().findViewById(R.id.editTextTelefone);
                txtViewEndereco.setText(user.getString("endereco"));
                editTextEndereco.setText(user.getString("endereco"));
                txtViewNome.setText(user.getString("nome"));
                editTextNome.setText(user.getString("nome"));
                txtViewTelefone.setText(user.getString("telefone"));
                editTextTelefone.setText(user.getString("telefone"));
                pDialog.dismiss();
            }
        });
    }

    public void setVisibilidadeEditETxt(boolean bool){
        TextView txtViewEndereco = (TextView) getActivity().findViewById(R.id.textViewEnderecoPerfil);
        EditText editTextEndereco = (EditText) getActivity().findViewById(R.id.editTextEndereco);
        TextView txtViewNome = (TextView) getActivity().findViewById(R.id.textView2);
        EditText editTextNome = (EditText) getActivity().findViewById(R.id.editTextNome);
        TextView txtViewTelefone = (TextView) getActivity().findViewById(R.id.textViewTelefonePerfil);
        EditText editTextTelefone = (EditText) getActivity().findViewById(R.id.editTextTelefone);
        if(bool){
            txtViewEndereco.setVisibility(View.INVISIBLE);
            txtViewNome.setVisibility(View.INVISIBLE);
            txtViewTelefone.setVisibility(View.INVISIBLE);
            editTextEndereco.setVisibility(View.VISIBLE);
            editTextNome.setVisibility(View.VISIBLE);
            editTextTelefone.setVisibility(View.VISIBLE);
        }else{
            txtViewEndereco.setVisibility(View.VISIBLE);
            txtViewNome.setVisibility(View.VISIBLE);
            txtViewTelefone.setVisibility(View.VISIBLE);
            editTextEndereco.setVisibility(View.INVISIBLE);
            editTextNome.setVisibility(View.INVISIBLE);
            editTextTelefone.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        final Button editar = ((Button)v.findViewById(R.id.buttonEditarPerfil));
        setInformacoes();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextEndereco = (EditText) getActivity().findViewById(R.id.editTextEndereco);
                EditText editTextNome = (EditText) getActivity().findViewById(R.id.editTextNome);
                EditText editTextTelefone = (EditText) getActivity().findViewById(R.id.editTextTelefone);
                Button botao = (Button) getActivity().findViewById(R.id.buttonEditarPerfil);
                if (editar.getText().equals("EDITAR PERFIL")) {
                    botao.setText("SALVAR");
                    setVisibilidadeEditETxt(true);

                } else if (editar.getText().equals("SALVAR")) {
                    final String newNome = editTextNome.getText().toString();
                    final String newEndereco = editTextEndereco.getText().toString();
                    final String newTelefone = editTextTelefone.getText().toString();
                    if(!newNome.equals("") && !newEndereco.equals("") && !newTelefone.equals("")){
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                                "Carregando");
                    query.getInBackground(ParseUser.getCurrentUser().getObjectId(),new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, com.parse.ParseException e) {
                            ParseUser user = ParseUser.getCurrentUser();
                            user.put("endereco",newEndereco);
                            user.put("nome",newNome);
                            user.put("telefone",newTelefone);
                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
                                    Button botao = (Button) getActivity().findViewById(R.id.buttonEditarPerfil);
                                    botao.setText("EDITAR PERFIL");
                                    setInformacoes();
                                    setVisibilidadeEditETxt(false);
                                    pDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Informações editadas com sucesso.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Preencha todos os campos corretamente.",Toast.LENGTH_SHORT).show();
                    }
             }
            }
        });
        return v;
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
