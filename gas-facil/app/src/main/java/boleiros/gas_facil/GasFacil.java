package boleiros.gas_facil;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Application;

import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 28/10/14.
 */
public class GasFacil extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "rMJLW7qybdglATi4FowrRFwmVqZkLoxuo6vntg1c", "tGepZhs91zXpb0n36dAhRnlFTmki3Mjm10spl3Fi");

        Parse.enableLocalDatastore(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseObject.registerSubclass(Produto.class);
        ParseObject.registerSubclass(Pedido.class);

    }
}

