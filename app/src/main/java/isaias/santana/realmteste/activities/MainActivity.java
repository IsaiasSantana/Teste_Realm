package isaias.santana.realmteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import isaias.santana.realmteste.R;
import isaias.santana.realmteste.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private EditText tvUsuario;
    private EditText tvSenha;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        (findViewById(R.id.tvCriarConta)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               startActivity(new Intent(MainActivity.this,CriarContaActivity.class));
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String usuario;
                final String senha;

                usuario = ((EditText) findViewById(R.id.etUsuario)).getText().toString();
                senha = ((EditText) findViewById(R.id.etSenha)).getText().toString();

                Pessoa pessoaGerenciada = realm.where(Pessoa.class).equalTo("nome",usuario).equalTo("senha",senha).findFirst();
                if (pessoaGerenciada == null)
                    notificarCredenciaisInválidas();
                else
                    entrar(usuario);



            }
        });
    }

    private void notificarCredenciaisInválidas()
    {
        Toast.makeText(this,"Usuário ou senha incorretos",Toast.LENGTH_SHORT).show();
    }

    private void entrar(String usuario)
    {
        final Intent intent = new Intent(this,ListaAnimaisActivity.class);
        intent.putExtra("idUsuario",usuario);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }
}
