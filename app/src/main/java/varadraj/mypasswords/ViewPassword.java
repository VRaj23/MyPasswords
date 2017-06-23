package varadraj.mypasswords;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import varadraj.model.Record;

/**
 * Created by varad on 4/6/17.
 */

public class ViewPassword extends DialogFragment implements View.OnClickListener {

    TextView viewUserName, viewPassword;
    Button edit,delete;
    private String username,password,description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewpassword,container,true);
        viewPassword = (TextView)view.findViewById(R.id.tv_viewpassword);
        viewUserName = (TextView)view.findViewById(R.id.tv_viewusername);
        edit = (Button)view.findViewById(R.id.bt_editrecord);
        edit.setOnClickListener(this);
        delete = (Button)view.findViewById(R.id.bt_deleterecord);
        delete.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        username = bundle.getString("username");
        password = bundle.getString("password");
        description = bundle.getString("description");
        viewUserName.setText(username);
        viewPassword.setText(password);
        this.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog); //
        getDialog().setTitle(username); //TODO:set dialog title
        return  view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_editrecord:{
                FragmentManager fm = getActivity().getFragmentManager();
                fm.beginTransaction().remove(this).commit(); //remove ViewPassword fragment before starting edit fragment.
                AddRecordFragment addFM = new AddRecordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("editUsername",username);
                bundle.putString("editPassword",password);
                bundle.putString("editDescription",description);
                addFM.setArguments(bundle);
                addFM.show(fm,"Edit Record");

                break;
            }
            case R.id.bt_deleterecord:{
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Record> r = realm.where(Record.class)
                                .equalTo("username",username)
                                .equalTo("password",password)
                                .equalTo("description",description)
                                .findAll();
                        if(r.deleteAllFromRealm()) {
                             Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ((HomeActivity) getActivity()).onDataChanged();
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
            }
        }
    }
}
