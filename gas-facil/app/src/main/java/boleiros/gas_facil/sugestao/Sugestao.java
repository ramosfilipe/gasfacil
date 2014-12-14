package boleiros.gas_facil.sugestao;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.SugestaoModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sugestao extends Fragment {


    public Sugestao() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sugestao, container, false);
        final EditText campoSugestao = (EditText) v.findViewById(R.id.editTextSugestao);
        final Button  buttonEnviarSugestao = (Button) v.findViewById(R.id.buttonEnviarSugestao);
        buttonEnviarSugestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campoSugestao.length() > 5) {
                    final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                            "Carregando");
                    SugestaoModel sugestao = new SugestaoModel();
                    sugestao.setUser(ParseUser.getCurrentUser());
                    sugestao.setStatus(campoSugestao.getText().toString());
                    sugestao.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(),
                                        "Ops... Tente comprar novamente",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                pDialog.dismiss();

                                Toast.makeText(getActivity(), "Sugestao Enviada",
                                        Toast.LENGTH_LONG).show();
                                campoSugestao.setText("");
                            }
                        }


                    });


                } else {
                    Toast.makeText(getActivity(), "Verifique se sua sugestão contém mais de 5 letras",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        return v;
    }


}
