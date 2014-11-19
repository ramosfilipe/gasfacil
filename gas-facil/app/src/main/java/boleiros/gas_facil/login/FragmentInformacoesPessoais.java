package boleiros.gas_facil.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boleiros.gas_facil.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInformacoesPessoais.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInformacoesPessoais#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInformacoesPessoais extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText nome, email, telefone;
    Button continuar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FragmentInformacoesPessoais() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInformacoesPessoais.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInformacoesPessoais newInstance(String param1, String param2) {
        FragmentInformacoesPessoais fragment = new FragmentInformacoesPessoais();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_fragment_informacoes_pessoais, container, false);
        v.findViewById(R.id.relativeInformacoesPessoais).setBackgroundColor(Color.WHITE);
        getActivity().findViewById(R.id.campoUsername).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.campoSenha).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.login).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.signup).setVisibility(View.INVISIBLE);

        continuar = (Button) v.findViewById(R.id.buttonContinuarInformacoesPessoais);

        nome = (EditText) v.findViewById(R.id.editTextNomeInfoPessoais);
        email = (EditText) v.findViewById(R.id.editTextEmail);
        telefone = (EditText) v.findViewById(R.id.editTextTelefoneInfoPessoais);

        nome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (nome.getText().toString().equals("Nome")) {
                    nome.setText("");
                    nome.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        nome.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    if (email.getText().toString().equals("E-mail")) {
                        email.setText("");
                        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        email.setTextColor(Color.BLACK);
                    }
                }
                return false;
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (email.getText().toString().equals("E-mail")) {
                    email.setText("");
                    email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    email.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    if (telefone.getText().toString().equals("Telefone")) {
                        telefone.setText("");
                        telefone.setInputType(InputType.TYPE_CLASS_PHONE);
                        telefone.setTextColor(Color.BLACK);
                    }
                }
                return false;
            }
        });
        telefone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (telefone.getText().toString().equals("Telefone")) {
                    telefone.setText("");
                    telefone.setInputType(InputType.TYPE_CLASS_PHONE);
                    telefone.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        telefone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    continuar.performClick();
                }
                return false;
            }
        });


        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = ((EditText) getActivity().findViewById(R.id.editTextNomeInfoPessoais)).getText().toString();
                String email = ((EditText) getActivity().findViewById(R.id.editTextEmail)).getText().toString();
                String telefone = ((EditText) getActivity().findViewById(R.id.editTextTelefoneInfoPessoais)).getText().toString();
                if (!nome.equals("Nome") && !nome.equals("") && !email.equals("E-mail") && !email.equals("") && !telefone.equals("Telefone") && !telefone.equals("") && emailValido(email)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nome", nome);
                    bundle.putString("email", email);
                    bundle.putString("telefone", telefone);
                    Fragment fragment = new FragmentEnderecoNovoUsuario();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.relativeInformacoesPessoais, fragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
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
