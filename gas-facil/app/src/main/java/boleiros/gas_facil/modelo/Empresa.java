package boleiros.gas_facil.modelo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by filipe on 01/02/15.
 */
@ParseClassName("Empresa")

public class Empresa extends ParseObject {


    public String getNome() {
        return getString("nome");
    }

    public void setNome(String nome) {
        put("nome", nome);
    }

    public void setTelefone(String telefone){
        put("telefone",telefone);
    }

    public String getTelefone(){
        return getString("telefone");
    }

    public String getEndereco() {
        return getString("endereco");
    }

   }
