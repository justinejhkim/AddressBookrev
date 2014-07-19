package my.real.addressbook;

import android.content.Context; 
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 리스트뷰에 데이터를 넣기 위하여 도와주기만을 하는 클래스입니다. 화면을 구성하는 Activity클래스를 상속받은것이 아니고, 데이터베이스가 관련된 리스트뷰를
 * 다루는 CursorAdapter클래스가 상속되었습니다.
 *
 */
public class DBAdapter extends CursorAdapter {
 
	public DBAdapter(Context context, Cursor c) {
		super(context, c);

	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) { 

		final ImageView image = (ImageView) view.findViewById(R.id.profile);
		final TextView name = (TextView) view.findViewById(R.id.name);
		final TextView tel = (TextView) view.findViewById(R.id.tel);
		
		image.setImageResource(R.drawable.profile);
		name.setText("Name : " + cursor.getString(cursor.getColumnIndex("name")));
		tel.setText("Phone Number : " + cursor.getString(cursor.getColumnIndex("age")));
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.item_list, parent, false);
		return v;
	}

}
