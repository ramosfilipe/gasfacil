package boleiros.gas_facil.modelo;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Diego on 11/12/2014.
 */
@ParseClassName("Favorito")
public class Favorito extends ParseObject {
    public Favorito (){

    }

    public ParseUser getUsuario(){
        return getParseUser("usuario");
    }
    public void setUsuario(ParseUser user){
        put("usuario",user);

    }
    public ParseObject getProduto() {
        return getParseObject("produto");
    }

    public void setProduto(Produto produto) {
        put("produto", produto);
    }

}
