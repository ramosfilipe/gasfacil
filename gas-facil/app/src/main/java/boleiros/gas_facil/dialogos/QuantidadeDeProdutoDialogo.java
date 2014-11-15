package boleiros.gas_facil.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.pedido.EfetuarPedido;
import boleiros.gas_facil.util.ActivityStore;

/**
 * Created by filipe on 03/11/14.
 */
public class QuantidadeDeProdutoDialogo extends DialogFragment {
    private String[] quantidade = {"Um","Dois","Três","Quatro","Cinco"};


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ActivityStore.getInstance(getActivity()).setQuantidadeDeProdutoDesejadaPeloUser(1);

// Set the dialog title
        builder.setTitle("Quantidade?")
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(quantidade, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityStore.getInstance(getActivity()).setQuantidadeDeProdutoDesejadaPeloUser(which+1);
                        System.out.println(which);

                        //dialog.dismiss();
                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), EfetuarPedido.class);
                        startActivity(intent);
                        dialog.dismiss();


// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}