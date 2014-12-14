package boleiros.gas_facil.modelo;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by filipe on 13/12/14.
 */
@ParseClassName("Sugestao")
public class SugestaoModel extends ParseObject {

    public SugestaoModel() {
    }


    public ParseUser getUser() {
        return getParseUser("usuario");
    }

    public void setUser(ParseUser user) {
        put("usuario", user);
    }

    public String getSugestao() {
        return getString("sugestao");
    }

    public void setStatus(String sugestao) {
        put("sugestao", sugestao);
    }



}
