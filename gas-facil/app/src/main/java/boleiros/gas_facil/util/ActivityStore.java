package boleiros.gas_facil.util;
import android.content.Context;

import com.parse.ParseUser;

import java.util.ArrayList;

import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 11/11/14.
 */



public class ActivityStore {
    private static ActivityStore sActivityStore;
    private Context mContext;
    private ParseUser user;
    private Produto produto;
    private int quantidadeDeProdutoDesejadaPeloUser;


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidadeDeProdutoDesejadaPeloUser() {
        return quantidadeDeProdutoDesejadaPeloUser;
    }

    public void setQuantidadeDeProdutoDesejadaPeloUser(int quantidadeDeProdutoDesejadaPeloUser) {
        this.quantidadeDeProdutoDesejadaPeloUser = quantidadeDeProdutoDesejadaPeloUser;
    }

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }




    private ActivityStore(Context ctx) {
        mContext = ctx;
    }

    public static ActivityStore getInstance(Context ctx) {
        if (sActivityStore == null) {
            sActivityStore = new ActivityStore(ctx.getApplicationContext());
        }
        return sActivityStore;
    }



}