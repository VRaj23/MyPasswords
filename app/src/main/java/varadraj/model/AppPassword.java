package varadraj.model;

import io.realm.RealmObject;

/**
 * Created by varad on 5/6/17.
 */

public class AppPassword extends RealmObject {

    private String appPassword;

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }
}
