package varadraj.mypasswords;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.realm.RealmResults;
import varadraj.model.Record;

/**
 * Created by varad on 3/6/17.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    private LayoutInflater inflater;
    private RealmResults<Record> realmResults;
    Context context;


    public RecordAdapter(Context context, RealmResults<Record> results){
        inflater = LayoutInflater.from(context);
        this.realmResults = results;
        this.context = context;
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recordlistitem,parent,false);
        RecordHolder recordholder = new RecordHolder(view,context);
        return recordholder;
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        Record r = realmResults.get(position);
        holder.setRecord(r);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Record record;
        private TextView recordItem;
        Context c;


        public void setRecord(Record r){
            this.record = r;
            recordItem.setText(record.getDescription());
        }


        public RecordHolder(View itemView, Context c) {
            super(itemView);
            itemView.setOnClickListener(this);
            recordItem = (TextView)itemView.findViewById(R.id.tv_recorditem);
            this.c  = c;

        }


        @Override
        public void onClick(View view) {
            Log.d("tag","click "+ record.getDescription());
            ((HomeActivity) context).onRecordClick(record);

        }
    }
}
