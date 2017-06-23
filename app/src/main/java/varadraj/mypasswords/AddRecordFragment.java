package varadraj.mypasswords;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmResults;
import varadraj.model.Record;

/**
 * Created by varad on 5/6/17.
 */

public class AddRecordFragment extends DialogFragment implements View.OnClickListener {

    EditText username,password,description;
    Button saveRecord;
    String editUsername = null, editPassword = null, editDescription = null;
    private boolean edit = false;

    public AddRecordFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addrecord,container,true);
        username = (EditText)view.findViewById(R.id.et_user);
        password = (EditText)view.findViewById(R.id.et_pwd);
        description = (EditText)view.findViewById(R.id.et_desc);
        saveRecord = (Button)view.findViewById(R.id.bt_saverecord);
        saveRecord.setOnClickListener(this);
        Bundle bundle = getArguments();
        //for edit record
        editUsername = bundle.getString("editUsername");
        editPassword = bundle.getString("editPassword");
        editDescription = bundle.getString("editDescription");

        if (editPassword != null){
            username.setText(editUsername);
            password.setText(editPassword);
            description.setText(editDescription);
            edit = true;
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        //TODO scroll to added/edited record
        switch (view.getId()){
            case R.id.bt_saverecord:{
                if(editTextBlank()){
                    Toast.makeText(getActivity(),"Fields cannot be blank",Toast.LENGTH_SHORT).show();
                }
                else if(isDuplicate()){
                    Toast.makeText(getActivity(),"Data already exists",Toast.LENGTH_SHORT).show();
                }
                else if(edit){
                    editRecordInDatabase();
                    ((HomeActivity)getActivity()).onDataChanged();
                    getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                }
                else{
                    saveRecordInDatabase();
                    ((HomeActivity)getActivity()).onDataChanged();
                    getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                }
            }
        }
    }

    private void editRecordInDatabase() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Record record = realm.where(Record.class)
                        .equalTo("username",editUsername)
                        .equalTo("password",editPassword)
                        .equalTo("description",editDescription)
                        .findFirst();
                setRecordValues(record);
            }
        });
    }

    private void saveRecordInDatabase(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Record record = realm.createObject(Record.class);
                setRecordValues(record);

            }
        });
        realm.close();
    }

    private void setRecordValues(Record record){
        record.setUsername(username.getText().toString().trim());
        record.setPassword(password.getText().toString().trim());
        record.setDescription(description.getText().toString().trim());

    }

    private boolean editTextBlank() {
        if (username.getText().toString().equals("")
                || password.getText().toString().equals("")
                || description.getText().toString().equals("")){
            return true;
        }
        else
            return false;
    }

    private boolean isDuplicate(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Record> result = realm.where(Record.class)
                .equalTo("username",username.getText().toString())
                .equalTo("password",password.getText().toString())
                .equalTo("description",description.getText().toString())
                .findAll();
        realm.close();
        if (result.size() == 0)
            return false;
        else
            return true;

    }

}
