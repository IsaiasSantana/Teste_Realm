package isaias.santana.realmteste.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import isaias.santana.realmteste.R;
import isaias.santana.realmteste.adapters.AnimaisAdapter;
import isaias.santana.realmteste.adapters.viewHolders.AnimaisViewHolder;
import isaias.santana.realmteste.model.Animal;
import isaias.santana.realmteste.model.Pessoa;
import isaias.santana.realmteste.util.MyDividerItemDecoration;

public class ListaAnimaisActivity extends AppCompatActivity
{

    private final Realm realm = Realm.getDefaultInstance();
    private String idUsuario;
    private OrderedRealmCollection<Animal> orderedRealmCollection;

    private RealmAsyncTask transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        idUsuario = getIntent().getExtras().getString("idUsuario");
        if(idUsuario == null)
            throw new NullPointerException("Id nulo");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);

        orderedRealmCollection = realm.where(Pessoa.class).equalTo("nome",idUsuario).findFirst().getAnimais();
        final AnimaisAdapter animaisAdapter = new AnimaisAdapter(orderedRealmCollection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(animaisAdapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        findViewById(R.id.btnSair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lista_animais,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.add)
        {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint("InflateParams")
            final View view = inflater.inflate(R.layout.layout_dialog_novo_animal,null);

            new AlertDialog.Builder(this)
                    .setView(view)
                    .setTitle("Novo animal de estimação")
                    .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            final String nomeAnimal = ((EditText) view.findViewById(R.id.etNomeAnimal)).getText().toString();
                            final String sexoAnimal = ((EditText) view.findViewById(R.id.etSexoAnimal)).getText().toString();
                            final int idadeAnimal = Integer.parseInt(((EditText) view.findViewById(R.id.etIdadeAnimal)).getText().toString());

                            final Animal animal = new Animal();
                            final String id = nomeAnimal+String.valueOf(System.currentTimeMillis());

                            animal.setId(id);
                            animal.setNome(nomeAnimal);
                            animal.setEspecie(sexoAnimal);
                            animal.setIdade(idadeAnimal);

                            transaction =  realm.executeTransactionAsync(new Realm.Transaction()
                            {
                                @Override
                                public void execute(Realm realm)
                                {
                                    realm.copyToRealm(animal);
                                    final Pessoa pessoa = realm.where(Pessoa.class).equalTo("nome",idUsuario).findFirst();
                                    pessoa.getAnimais().add(animal);
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(ListaAnimaisActivity.this,"Animal adicionado com sucesso!",Toast.LENGTH_SHORT).show();
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    Toast.makeText(ListaAnimaisActivity.this,"Erro ao adicionar o animal!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop ()
    {
        super.onStop();
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction)
        {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                  if(viewHolder instanceof AnimaisViewHolder)
                  {
                      Animal animal = realm.where(Animal.class).equalTo("id",((AnimaisViewHolder)viewHolder).getIdAnimal()).findFirst();
                      animal.deleteFromRealm();
                  }
                }
            });
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }
}