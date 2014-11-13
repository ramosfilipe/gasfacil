package boleiros.gas_facil.login;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;

public class LoginSignup extends Activity implements InformacoesNovoUsuario.OnFragmentInteractionListener{
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_login_signup);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.campoUsername);
        password = (EditText) findViewById(R.id.campoSenha);

        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString().replaceAll(" ","");
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginSignup.this,
                                            Inicio.class);
                                    startActivity(intent);

                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Usuário ou  senha inválidos.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Fragment fragment = new InformacoesNovoUsuario();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.relativeInfos,fragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (ParseUser.getCurrentUser().getObjectId() == null) {
            if (findViewById(R.id.txtNome).getVisibility() == View.INVISIBLE) {
                Intent intent = new Intent(LoginSignup.this, Login.class);
                startActivity(intent);

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}