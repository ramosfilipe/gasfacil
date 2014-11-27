package boleiros.gas_facil.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;

public class LoginSignup extends Activity implements InformacoesNovoUsuario.OnFragmentInteractionListener, FragmentEnderecoNovoUsuario.OnFragmentInteractionListener, FragmentInformacoesPessoais.OnFragmentInteractionListener {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    TextView esqueceu;
    EditText username;
    EditText email;


    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_login_signup);
        // Locate EditTexts in main.xml
        getActionBar().hide();
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        esqueceu = (TextView) findViewById(R.id.textViewInformacoesPessoaisTagPerfil);
        username = (EditText) findViewById(R.id.campoUsername);
        password = (EditText) findViewById(R.id.campoSenha);
        username.setTextColor(Color.parseColor("#F5F5F5"));
        password.setTextColor(Color.parseColor("#F5F5F5"));
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (password.getText().toString().equals("Senha")) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    password.setTextColor(Color.WHITE);
                    password.setText("");
                }
                return false;
            }
        });
        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (username.getText().toString().equals("Nome de usuário")) {
                    username.setTextColor(Color.WHITE);
                    username.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    username.setText("");
                }
                return false;
            }
        });
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    if (password.getText().toString().equals("Senha")) {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                        password.setTextColor(Color.WHITE);
                        password.setText("");
                    }
                }
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    loginbutton.performClick();
                }
                return false;
            }
        });

        findViewById(R.id.relativeInfos).setBackgroundColor(Color.parseColor("#BBDEFB"));
        //   findViewById(R.id.relativeInfoLogin).setBackgroundColor(Color.WHITE);
        //   findViewById(R.id.frameLogin).setBackgroundColor(Color.WHITE);
        // Locate Buttons in main.xml
        esqueceu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSignup.this);
                dialog.setTitle("Esqueceu a senha?");
                dialog.setMessage("Digite seu endereço de e-mail");
                email = new EditText(LoginSignup.this);
                email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                email.setLayoutParams(lp);
                dialog.setView(email);
                dialog.setNegativeButton("Enviar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {
                                System.out.println("Email - " + email.getText().toString());
                                if (!email.getText().toString().equals(""))
                                    ParseUser.requestPasswordResetInBackground(email.getText().toString(),
                                            new RequestPasswordResetCallback() {
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast.makeText(
                                                                getApplicationContext(),
                                                                "Instruções para redefinição de senha foram enviadas para seu email",
                                                                Toast.LENGTH_LONG).show();
                                                        dialog.cancel();
                                                    } else {
                                                        Toast.makeText(
                                                                getApplicationContext(),
                                                                "E-mail inválido, tente novamente",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                            }
                        });
                dialog.show();

            }
        });
        loginbutton.setBackgroundColor(Color.parseColor("#90CAF9"));
        signup.setBackgroundColor(Color.parseColor("#64B5F6"));
        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();
                final ProgressDialog pDialog = ProgressDialog.show(LoginSignup.this, null,
                        "Carregando");
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
                                    pDialog.dismiss();
                                    finish();
                                } else {
                                    pDialog.dismiss();
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
                Fragment fragment = new FragmentInformacoesPessoais();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.relativeInfos, fragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (ParseUser.getCurrentUser().getObjectId() == null) {
            if (findViewById(R.id.campoUsername).getVisibility() == View.INVISIBLE) {
                Intent intent = new Intent(LoginSignup.this, Login.class);
                startActivity(intent);

            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}