package boleiros.gas_facil.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import boleiros.gas_facil.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformacoesNovoUsuario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformacoesNovoUsuario#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class InformacoesNovoUsuario extends Fragment {
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
         * @return A new instance of fragment InformacoesNovoUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static InformacoesNovoUsuario newInstance(String param1, String param2) {
        InformacoesNovoUsuario fragment = new InformacoesNovoUsuario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public InformacoesNovoUsuario() {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_informacoes_novo_usuario, container, false);
        getActivity().findViewById(R.id.txtNome).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.campoUsername).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.txtSenha).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.campoSenha).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.login).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.signup).setVisibility(View.INVISIBLE);





        // Retrieve the text entered from the EditText
        Button button = (Button)v.findViewById(R.id.botaoCriar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernametxt = ((EditText)getActivity().findViewById(R.id.campoUser)).getText().toString().replaceAll(" ","");
                String passwordtxt = ((EditText)getActivity().findViewById(R.id.campoPasswd)).getText().toString();
                String nome = ((EditText)getActivity().findViewById(R.id.campoName)).getText().toString();
                String endereco = ((EditText)getActivity().findViewById(R.id.campoEndereco)).getText().toString();
                String telefone = ((EditText)getActivity().findViewById(R.id.campoTelefone)).getText().toString();
                // Force user to fill up the form
                if (usernametxt.equals("") ||  passwordtxt.equals("") ||nome.equals("") || endereco.equals("") || telefone.equals("")) {
                    Toast.makeText(getActivity(),
                            "Por favor, complete todos os campos.",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.put("nome", nome);
                    user.put("endereco",endereco);
                    user.put("telefone",telefone);
                    user.put("fornecedor",false);
                    final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                            "Carregando");
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                pDialog.dismiss();
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getActivity(),
                                        "Usuário criado com sucesso.",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(),Login.class));
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(),
                                        "Usuário existente, tente outro.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });

        return v ;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
