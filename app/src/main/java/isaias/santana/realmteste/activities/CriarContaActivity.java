package isaias.santana.realmteste.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import isaias.santana.realmteste.R;
import isaias.santana.realmteste.model.Pessoa;

public class CriarContaActivity extends AppCompatActivity {

    private final Realm realm = Realm.getDefaultInstance();
    private RealmAsyncTask transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText etNovoUsuario      = (EditText) findViewById(R.id.etNovoUsuario);
        final EditText etNovaSenhaUsuario = (EditText) findViewById(R.id.etNovaSenhaUsuario);


        findViewById(R.id.btnVoltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        findViewById(R.id.btnCriarConta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String newUser = etNovoUsuario.getText().toString();
                final String password = etNovaSenhaUsuario.getText().toString();

                if(newUser.trim().length() == 0 || password.trim().length() == 0)
                    return;

                final Pessoa pessoa = new Pessoa();
                pessoa.setNome(newUser);
                pessoa.setSenha(password);
                pessoa.setAnimais(null);

                transaction = realm.executeTransactionAsync(
                    new Realm.Transaction()
                    {
                          @Override
                          public void execute(Realm realm)
                          {
                                realm.copyToRealm(pessoa);
                          }
                      },
                    new Realm.Transaction.OnSuccess()
                    {
                      @Override
                      public void onSuccess()
                      {
                          Toast.makeText(CriarContaActivity.this,"Conta criada com sucesso",Toast.LENGTH_SHORT).show();
                          etNovaSenhaUsuario.setText("");
                          etNovaSenhaUsuario.setText("");
                          finish();
                      }
                    },
                    new Realm.Transaction.OnError()
                    {
                        @Override
                        public void onError(Throwable error)
                        {

                            Toast.makeText(CriarContaActivity.this,"Não foi possível criar a conta, tente novamente.",Toast.LENGTH_SHORT).show();
                        }
                    }
                );

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
