package boleiros.gas_facil.pedido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.ProdutoAdapter;
import boleiros.gas_facil.adapter.ProdutoAdapterPedido;
import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.modelo.ProdutoManager;
import boleiros.gas_facil.util.ActivityStore;

public class EfetuarPedido extends Activity {
    private RecyclerView mRecyclerView;
    private ProdutoAdapterPedido mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_pedido);
        System.out.println(ActivityStore.getInstance(this).getProduto().getType());
        mRecyclerView = (RecyclerView)findViewById(R.id.listEfetuarPedido);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final ArrayList<Produto> aux= new ArrayList<Produto>();
        aux.add(ActivityStore.getInstance(this).getProduto());
        mAdapter = new ProdutoAdapterPedido(
                aux,
                ActivityStore.getInstance(this).getQuantidadeDeProdutoDesejadaPeloUser(),
                R.layout.card_layout, this);
        mRecyclerView.setAdapter(mAdapter);
        final int qtd = ActivityStore.getInstance(this).getQuantidadeDeProdutoDesejadaPeloUser();

        Button confirmar = (Button) findViewById(R.id.buttonConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText dinheiro = (EditText) findViewById(R.id.editTextDinheiro);

                if(dinheiro.getText().toString().length()>0){


                    double dinheiroDbl = Double.parseDouble(dinheiro.getText().toString());
                    double precoProduto = Double.parseDouble(aux.get(0).getPrice());
                    double valorDoPedido = qtd * precoProduto;

                    if (dinheiroDbl < valorDoPedido ) {
                        Toast.makeText(getApplicationContext(),
                                "Ops... Verifique o campo Dinheiro",
                                Toast.LENGTH_LONG).show();
                    }
                    else if((dinheiroDbl - valorDoPedido)>100){
                        Toast.makeText(getApplicationContext(),
                                "Ops... Favor não exceder 100 reais de troco",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        final ProgressDialog pDialog = ProgressDialog.show(EfetuarPedido.this, null,
                                "Comprando...");
                        Pedido pedido = new Pedido();
                        pedido.setProduto(aux.get(0));
                        pedido.setComprador(ParseUser.getCurrentUser());
                        pedido.setPrice(dinheiro.getText().toString());
                        pedido.setQuantidade(qtd);
                        pedido.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(com.parse.ParseException e) {
                                if (e != null) {
                                    pDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),
                                            "Ops... Tente comprar novamente",
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    pDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), "Produto Comprado!",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                                    startActivity(intent);
                                }
                            }


                        });
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Ops... Verifique o campo Dinheiro",
                            Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_efetuar_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
