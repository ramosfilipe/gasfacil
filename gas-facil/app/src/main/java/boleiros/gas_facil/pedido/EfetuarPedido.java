package boleiros.gas_facil.pedido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.NumberFormat;
import java.util.ArrayList;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;
import boleiros.gas_facil.adapter.ProdutoAdapterPedido;
import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;
import boleiros.gas_facil.util.ActivityStore;

public class EfetuarPedido extends Activity {
    private RecyclerView mRecyclerView;
    private ProdutoAdapterPedido mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_pedido);
        TextView descricao = (TextView) findViewById(R.id.textViewDescricaoProduto);
        descricao.setText("''" + ActivityStore.getInstance(this).getProduto().getDescricao() + "'''");
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "roboto.ttf");
        descricao.setTypeface(tf,Typeface.ITALIC);

        //  System.out.println(ActivityStore.getInstance(this).getProduto().getType());
        mRecyclerView = (RecyclerView) findViewById(R.id.listEfetuarPedido);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final ArrayList<Produto> aux = new ArrayList<Produto>();
        aux.add(ActivityStore.getInstance(this).getProduto());
        mAdapter = new ProdutoAdapterPedido(
                aux,
                ActivityStore.getInstance(this).getQuantidadeDeProdutoDesejadaPeloUser(),
                R.layout.card_layout, this);
        mRecyclerView.setAdapter(mAdapter);
        final int qtd = ActivityStore.getInstance(this).getQuantidadeDeProdutoDesejadaPeloUser();
        Button confirmar = (Button) findViewById(R.id.buttonConfirmar);
        EditText dinheiro = (EditText) findViewById(R.id.editTextDinheiro);
        dinheiro.addTextChangedListener(new TextWatcher() {
            EditText dinheiro = (EditText) findViewById(R.id.editTextDinheiro);
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            private String current = "";
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(current)){
                    dinheiro.removeTextChangedListener(this);
                    String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = charSequence.toString().replaceAll(replaceable, "");
                    if(!cleanString.equals("")){
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
                        current = formatted;
                        dinheiro.setText(formatted);
                        dinheiro.setSelection(formatted.length());

                    }


                    dinheiro.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText dinheiro = (EditText) findViewById(R.id.editTextDinheiro);
                String dinheiroFormatado = dinheiro.getText().toString();
                String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                String dinheiroNumeric = dinheiroFormatado.toString().replaceAll(replaceable, "");
                if (dinheiroNumeric.length() > 0) {
                    dinheiroNumeric = new StringBuilder(dinheiroNumeric).insert(dinheiroNumeric.length() - 2, ".").toString();
                    double dinheiroDbl = Double.parseDouble(dinheiroNumeric);
                    //double precoProduto = Double.parseDouble();
                    double valorDoPedido = qtd * aux.get(0).getPrice();
                    double troco = dinheiroDbl - valorDoPedido;
                    if (dinheiroDbl < valorDoPedido) {
                        Toast.makeText(getApplicationContext(),
                                "Ops... Verifique o campo Dinheiro",
                                Toast.LENGTH_LONG).show();
                    } else {
                        if ((dinheiroDbl - valorDoPedido) > 100) {
                            Toast.makeText(getApplicationContext(),
                                    "Ops... Favor não exceder 100 reais de troco",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            final ProgressDialog pDialog = ProgressDialog.show(EfetuarPedido.this, null,
                                    "Comprando...");


                            Pedido pedido = new Pedido();
                            pedido.setProduto(aux.get(0));
                            pedido.setComprador(ParseUser.getCurrentUser());
                            pedido.setPrice(dinheiroNumeric);
                            pedido.setQuantidade(qtd);
                            pedido.setStatus("Pendente");
                            pedido.setTroco("" + troco);
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
                    }
                } else {
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
