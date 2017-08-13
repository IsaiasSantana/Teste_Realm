package isaias.santana.realmteste.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Isaías Santana on 07/08/17.
 *         email: isds.santana@gmail.com
 */

public class Pessoa extends RealmObject
{
    @PrimaryKey
    @Required
    private String nome;

    @Required
    private String senha;

    private RealmList<Animal> animais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome)
    {

        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public RealmList<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(RealmList<Animal> animais) {
        this.animais = animais;
    }
}
