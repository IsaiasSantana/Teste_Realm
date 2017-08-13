package isaias.santana.realmteste.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import isaias.santana.realmteste.R;

/**
 * @author Isaías Santana on 07/08/17.
 *         email: isds.santana@gmail.com
 */

public final class AnimaisViewHolder extends RecyclerView.ViewHolder
{
    private TextView tvNomeAnimal;
    private TextView tvIdadeAnimal;
    private TextView tvSexoAniaml;
    private String idAnimal;

    public AnimaisViewHolder(View itemView)
    {
        super(itemView);
        tvNomeAnimal = itemView.findViewById(R.id.tvNomeAnimal);
        tvIdadeAnimal = itemView.findViewById(R.id.tvIdadeAnimal);
        tvSexoAniaml = itemView.findViewById(R.id.tvSexoAnimal);
    }

    public TextView getTvIdadeAnimal() {
        return tvIdadeAnimal;
    }

    public TextView getTvNomeAnimal() {
        return tvNomeAnimal;
    }

    public TextView getTvSexoAniaml() {
        return tvSexoAniaml;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }
}
