package boleiros.gas_facil.contatoFornecedor;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.Empresa;
import boleiros.gas_facil.modelo.Produto;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFornecedor extends Fragment {
    TextView txtNome,endereco, telefone;

    public PerfilFornecedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_perfil_fornecedor, container, false);
        txtNome = (TextView) v.findViewById(R.id.textViewNomePerfilFornecedor);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "roboto.ttf");
        txtNome.setTextSize(30);
        txtNome.setTypeface(tf);
        telefone = (TextView) v.findViewById(R.id.textViewTelefoneFornecedor);
        endereco = (TextView) v.findViewById(R.id.TextViewEnderecoFornecedor);
        consultaDadosEmpresa();


        return v;
    }


    private void consultaDadosEmpresa(){
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), null,
                "Carregando");        ParseQuery<Empresa> query = ParseQuery.getQuery("Empresa");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Empresa>() {
            @Override
            public void done(List<Empresa> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    pDialog.dismiss();
                    Empresa emp = parseObjects.get(0);
                    telefone.setText(emp.getTelefone());
                    endereco.setText(emp.getEndereco());
                    txtNome.setText(emp.getNome());
                    pDialog.dismiss();

                    
                } else {
                    pDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "Ops... verifique sua internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



    }

}
