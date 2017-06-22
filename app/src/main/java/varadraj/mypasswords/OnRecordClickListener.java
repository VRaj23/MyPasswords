package varadraj.mypasswords;

import android.os.Parcelable;

import varadraj.model.Record;

/**
 * Created by varad on 4/6/17.
 */

public interface OnRecordClickListener extends Parcelable {
    void onRecordClick(Record r);
}
