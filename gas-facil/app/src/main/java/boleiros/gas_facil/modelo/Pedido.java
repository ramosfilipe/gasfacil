package boleiros.gas_facil.modelo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by filipe on 28/10/14.
 */

@ParseClassName("Pedido")
public class Pedido extends ParseObject {


    public Pedido() {
    }
    public ParseObject getProduto(){
        return getParseObject("produto");
    }
    public void setProduto(Produto produto) {
        put("produto", produto);
    }

    public void setThumbnail(ParseFile image){
        put("thumbnail",image);
    }
    public ParseFile getThumbnail(ParseFile image){
        return getParseFile("thumbnail");
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

    public int getQuantidade() {
        return getInt("quantidade");
    }

    public void setQuantidade(int quantidade) {
        put("quantidade", quantidade);
    }

    public String getStatus() {
        return getString("status");
    }

    public void setStatus(String status) {
        put("status", status);
    }

    public String getTroco() {
        return getString("troco");
    }

    public void setTroco(String troco) {
        put("troco", troco);
    }

}