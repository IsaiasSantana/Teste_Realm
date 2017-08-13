package isaias.santana.realmteste.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Isaías Santana on 07/08/17.
 *         email: isds.santana@gmail.com
 */

public class Animal extends RealmObject
{
    @PrimaryKey
    private String id;

    private int idade;
    private String nome;
    private String especie;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int getIdade() { return idade; }

    public void setIdade(int idade) { this.idade = idade; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }

    public void setEspecie(String especie) { this.especie = especie; }

}
