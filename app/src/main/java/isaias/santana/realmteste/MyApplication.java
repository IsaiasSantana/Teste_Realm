package isaias.santana.realmteste;

import android.app.Application;

import io.realm.Realm;

/**
 * @author Isaías Santana on 07/08/17.
 *         email: isds.santana@gmail.com
 */

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
    }
}
