package boleiros.gas_facil.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 11/11/14.
 */


public class ActivityStore {
    private static ActivityStore sActivityStore;
    private Context mContext;
    private ParseUser user;
    private Produto produto;
    private Bitmap imagemDePedido;
    private int quantidadeDeProdutoDesejadaPeloUser;
    private List<Produto> arrayProdutos;
    private ActivityStore(Context ctx) {
        mContext = ctx;
    }

    public static ActivityStore getInstance(Context ctx) {
        if (sActivityStore == null) {
            sActivityStore = new ActivityStore(ctx.getApplicationContext());
        }
        return sActivityStore;
    }

    public Bitmap getImagemDePedido() {
        return imagemDePedido;
    }

    public void setImagemDePedido(Bitmap imagemDePedido) {
        this.imagemDePedido = imagemDePedido;
    }

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

    public void setProdutos(List<Produto> arrayProdutos){
        this.arrayProdutos = arrayProdutos;
    }

    public List<Produto> getProdutos(){
        return arrayProdutos;
    }
}