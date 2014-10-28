package boleiros.gas_facil;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import android.app.Application;

/**
 * Created by filipe on 28/10/14.
 */
public class GasFacil extends Application {

    public void onCreate() {
        Parse.initialize(this, "rMJLW7qybdglATi4FowrRFwmVqZkLoxuo6vntg1c", "tGepZhs91zXpb0n36dAhRnlFTmki3Mjm10spl3Fi");
    }
}

