package boleiros.gas_facil.modelo;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filipe on 28/10/14.
 */
public class ProdutoManager {


    private static ProdutoManager mInstance;
    private List<Produto> produtos;

    public static ProdutoManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProdutoManager();
        }

        return mInstance;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getprodutos() {
        if (produtos == null) {
            produtos = new ArrayList<Produto>();
            consultaAoParse();
        }

        return produtos;
    }

    public void consultaAoParse() {
        ParseQuery<Produto> query = ParseQuery.getQuery("Produto");
        query.orderByDescending("createdAt");
        //query.setLimit(10);

        query.findInBackground(new FindCallback<Produto>() {
            @Override
            public void done(List<Produto> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    produtos = parseObjects;
                } else {
                }
            }
        });
    }
}