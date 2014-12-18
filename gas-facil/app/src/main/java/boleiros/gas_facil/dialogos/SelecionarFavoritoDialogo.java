package boleiros.gas_facil.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;
import boleiros.gas_facil.favoritos.FragmentFavoritos;
import boleiros.gas_facil.modelo.Favorito;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.pedido.EfetuarPedido;
import boleiros.gas_facil.util.ActivityStore;

/**
 * Created by Diego on 11/12/2014.
 */
public class SelecionarFavoritoDialogo extends DialogFragment {
    List<String> nomeProdutos = new ArrayList<String>();
    int choice = 0;
    List<Produto> produtos = ActivityStore.getInstance(getActivity()).getProdutos();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Inicio inicio =(Inicio) getActivity();

        for (Produto produto : produtos){
            nomeProdutos.add(produto.getType() + " - " + produto.getDescricao());
        }

// Set the dialog title
        builder.setTitle("Adicionar aos Favoritos")
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(nomeProdutos.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {
                        Favorito favorito = new Favorito();
                        favorito.setProduto(produtos.get(choice));
                        favorito.setUsuario(ParseUser.getCurrentUser());
                        favorito.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                inicio.carregaFragmentFavorito();
                                //dialog.dismiss();
                            }
                        });
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        inicio.carregaFragmentFavorito();
                        //dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
