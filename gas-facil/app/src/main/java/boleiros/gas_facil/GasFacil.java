package boleiros.gas_facil;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import boleiros.gas_facil.modelo.Estatistica;
import boleiros.gas_facil.modelo.Favorito;
import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 28/10/14.
 */
public class GasFacil extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rMJLW7qybdglATi4FowrRFwmVqZkLoxuo6vntg1c", "tGepZhs91zXpb0n36dAhRnlFTmki3Mjm10spl3Fi");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseObject.registerSubclass(Produto.class);
        ParseObject.registerSubclass(Pedido.class);
        ParseObject.registerSubclass(Estatistica.class);
        ParseObject.registerSubclass(Favorito.class );


    }
}

