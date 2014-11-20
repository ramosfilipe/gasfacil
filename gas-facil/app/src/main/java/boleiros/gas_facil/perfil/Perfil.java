package boleiros.gas_facil.perfil;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boleiros.gas_facil.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText nome, email, telefone, rua, numero, cep, complemento, bairro, referencia;
    TextView nomePerfil;
    Button botaoSalvarPessoais, botaoSalvarEndereco;

    private OnFragmentInteractionListener mListener;

    public Perfil() {
        // Required empty public constructor
    }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void setInformacoes(String flag) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        final String flagAux = flag;
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, com.parse.ParseException e) {
                ParseObject user = parseUsers.get(0);
                if(flagAux.equals("pessoais")){
                    nome.setText(user.getString("nome"));
                    nomePerfil.setText(user.getString("nome"));
                    email.setText(user.getString("email"));
                    telefone.setText(user.getString("telefone"));
                }else if (flagAux.equals("endereco")){
                    rua.setText(user.getString("rua"));
                    numero.setText(user.getString("numero"));
                    cep.setText(user.getString("cep"));
                    complemento.setText(user.getString("complemento"));
                    bairro.setText(user.getString("bairro"));
                    referencia.setText(user.getString("referencia"));
                }
                pDialog.dismiss();
            }
        });
    }

    public static boolean emailValido(String emailId) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*" + "\\@"
                + "\\w+([-.]\\w+)*" + "\\." + "\\w+([-.]\\w+)*");

        Matcher matcher = pattern.matcher(emailId);
        if (matcher.matches())
            return true;
        else
            return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView txt = (TextView)v.findViewById(R.id.textViewNomePerfil);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "roboto.ttf");
        txt.setTextSize(30);
        txt.setTypeface(tf);
        nome = (EditText)v.findViewById(R.id.editTextNomePerfil);
        nomePerfil = (TextView)v.findViewById(R.id.textViewNomePerfil);
        email = (EditText)v.findViewById(R.id.editTextEmailInformacoesPerfil);
        telefone = (EditText)v.findViewById(R.id.editTextTelefoneInformacoesPessoaisPerfil);
        rua = (EditText)v.findViewById(R.id.editTextRuaEnderecoPerfil);
        numero = (EditText)v.findViewById(R.id.editTextNumeroEnderecoPerfil);
        cep = (EditText)v.findViewById(R.id.editTextCepEnderecoPerfil);
        complemento = (EditText)v.findViewById(R.id.editTextComplementoEnderecoPerfil);
        bairro = (EditText)v.findViewById(R.id.editTextBairroEnderecoPerfil);
        referencia = (EditText)v.findViewById(R.id.editTextPontoReferenciaEnderecoPerfil);
        botaoSalvarPessoais = (Button)v.findViewById(R.id.buttonSalvarInformacoesPessoaisPerfil);
        botaoSalvarEndereco = (Button)v.findViewById(R.id.buttonSalvarEnderecoPerfil);

        setInformacoes("pessoais");
        setInformacoes("endereco");

        botaoSalvarPessoais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nometxt = nome.getText().toString();
                final String emailtxt = email.getText().toString();
                final String telefonetxt = telefone.getText().toString();
                if (!nometxt.equals("") && !emailtxt.equals("") && !telefonetxt.equals("") && emailValido(emailtxt)){
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                                "Carregando");
                        query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseUser>() {
                            @Override
                            public void done(ParseUser parseUser, com.parse.ParseException e) {
                                ParseUser user = ParseUser.getCurrentUser();
                                user.put("email", emailtxt);
                                user.put("nome", nometxt);
                                user.put("telefone", telefonetxt);
                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        setInformacoes("pessoais");
                                        pDialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Informações editadas com sucesso.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        botaoSalvarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ruatxt = rua.getText().toString();
                final String numerotxt = numero.getText().toString();
                final String ceptxt = cep.getText().toString();
                final String complementotxt = complemento.getText().toString();
                final String bairrotxt = bairro.getText().toString();
                final String referenciatxt = referencia.getText().toString();
                if(!ruatxt.equals("") && !numerotxt.equals("")  && !ceptxt.equals("")  && !complementotxt.equals("")  && !bairrotxt.equals("")  && !referenciatxt.equals("") ){
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                            "Carregando");
                    query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, com.parse.ParseException e) {
                            ParseUser user = ParseUser.getCurrentUser();
                            user.put("rua", ruatxt);
                            user.put("numero", numerotxt);
                            user.put("cep", ceptxt);
                            user.put("complemento", complementotxt);
                            user.put("bairro", bairrotxt);
                            user.put("referencia", referenciatxt);
                            user.put("endereco", ruatxt + " " + numerotxt + " " + complementotxt + " " + ceptxt + " " + bairrotxt + " " + referenciatxt);
                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
                                    setInformacoes("endereco");
                                    pDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Informações editadas com sucesso.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
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
