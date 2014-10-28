package boleiros.gas_facil.modelo;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by filipe on 28/10/14.
 */

@ParseClassName("Pedido")
public class Pedido extends ParseObject {


    public Pedido() {
    }

    public String getPrice() {
        return getString("price");
    }

    public void setPrice(String price) {
        put("price", price);
    }

    public ParseUser getComprador() {
        return getParseUser("comprador");
    }

    public void setComprador(ParseUser user) {
        put("comprador", user);
    }

    public void setCompradorStr(String authorStr) {
        put("compradorStr", authorStr);
    }

    public Boolean getProblemaVenda() {
        return getBoolean("problemaEmVenda");
    }

    public void setProblemaVenda(Boolean bool) {
        put("problemaEmVenda", bool);
    }


}