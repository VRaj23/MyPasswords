package varadraj.mypasswords;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by varad on 1/6/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
        //TODO encryption
        //TODO backup

    }
}
