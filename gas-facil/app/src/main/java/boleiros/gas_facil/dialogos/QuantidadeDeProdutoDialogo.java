package boleiros.gas_facil.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import boleiros.gas_facil.R;

/**
 * Created by filipe on 03/11/14.
 */
public class QuantidadeDeProdutoDialogo extends DialogFragment {
     String[] quantidade = {"Um","Dois","Três"};
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quantidade?")
                .setItems(quantidade, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }
}