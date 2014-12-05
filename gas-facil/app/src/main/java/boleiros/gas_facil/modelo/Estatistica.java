package boleiros.gas_facil.modelo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by filipe on 04/12/14.
 */

@ParseClassName("Estatistica")
public class Estatistica extends ParseObject {


    public Estatistica() {
    }

    public ParseObject getProduto() {
        return getParseObject("produto");
    }

    public void setProduto(Produto produto) {
        put("produto", produto);
    }



    public ParseUser getCliente() {
        return getParseUser("cliente");
    }

    public void setCliente(ParseUser user) {
        put("cliente", user);
    }



    public int getQuantidade() {
        return getInt("quantidadeComprada");
    }

    public void setQuantidade(int quantidade) {
        put("quantidadeComprada", quantidade);
    }



}