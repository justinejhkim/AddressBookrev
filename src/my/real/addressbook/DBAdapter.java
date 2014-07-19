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
 * ����Ʈ�信 �����͸� �ֱ� ���Ͽ� �����ֱ⸸�� �ϴ� Ŭ�����Դϴ�. ȭ���� �����ϴ� ActivityŬ������ ��ӹ������� �ƴϰ�, �����ͺ��̽��� ���õ� ����Ʈ�並
 * �ٷ�� CursorAdapterŬ������ ��ӵǾ����ϴ�.
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
