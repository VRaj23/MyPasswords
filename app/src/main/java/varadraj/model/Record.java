package varadraj.model;

import io.realm.RealmObject;

/**
 * Created by varad on 1/6/17.
 */

public class Record extends RealmObject {

    private String username;
    //@Required
    private String password;
    private String description;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        //return super.toString();
        return this.getDescription()+" "+this.getUsername()+" "+this.getPassword()+"\n";
    }
}
