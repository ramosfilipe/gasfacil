package boleiros.gas_facil.sugestao;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SaveCallback;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.SugestaoModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sugestao extends Fragment  implements AdapterView.OnItemSelectedListener {


    public Sugestao() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sugestao, container, false);
        final EditText campoSugestao = (EditText) v.findViewById(R.id.TextViewEnderecoFornecedor);
        final Button  buttonEnviarSugestao = (Button) v.findViewById(R.id.buttonEnviarSugestao);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_opcoes_sugestao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        buttonEnviarSugestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campoSugestao.length() > 5) {
                    final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                            "Carregando");
                    SugestaoModel sugestao = new SugestaoModel();
                    sugestao.setUser(ParseUser.getCurrentUser());
                    sugestao.setTipoSugestao(spinner.getSelectedItem().toString());
                    sugestao.setStatus(campoSugestao.getText().toString());
                    sugestao.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(),
                                        "Ops... Tente enviar novamente",
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
