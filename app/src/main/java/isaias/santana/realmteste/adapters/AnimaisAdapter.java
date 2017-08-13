package isaias.santana.realmteste.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import isaias.santana.realmteste.R;
import isaias.santana.realmteste.adapters.viewHolders.AnimaisViewHolder;
import isaias.santana.realmteste.model.Animal;

/**
 * @author Isaías Santana on 07/08/17.
 *         email: isds.santana@gmail.com
 */

public class AnimaisAdapter extends RealmRecyclerViewAdapter<Animal,AnimaisViewHolder>
{


    public AnimaisAdapter(@Nullable OrderedRealmCollection<Animal> data) { super(data, true); }

    @Override
    public AnimaisViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.raw_animais,parent,false);

        return new AnimaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimaisViewHolder holder, int position)
    {
        final Animal animal = getItem(position);
        holder.getTvNomeAnimal().setText(animal.getNome());
        holder.getTvIdadeAnimal().setText(String.valueOf("Idade: "+animal.getIdade()));
        holder.getTvSexoAniaml().setText("Sexo: "+animal.getEspecie());
        holder.setIdAnimal(animal.getId());
    }

}
