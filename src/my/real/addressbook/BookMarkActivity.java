package my.real.addressbook;

import android.app.Activity; 
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 즐겨찾기 Activity입니다.
 *
 */
public class BookMarkActivity extends Activity {
    
	final String DBNAME = "person.db";
	final int DBVERSION = 2;
	final private DBHelper DBHELPER = new DBHelper(this, DBNAME, null, DBVERSION);
	final private MyData tempData = MyData.getInstance();
	private ListView list;
	private SQLiteDatabase db;
	private Cursor cursor;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_mark_activity);
        list = (ListView)findViewById(R.id.bookMarkList);
        selectDB();
        list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int position, long id) {

				cursor.moveToPosition(position);
				Bundle nameBundle = new Bundle();
				nameBundle.putString("name",
						cursor.getString(cursor.getColumnIndex("name")));
				tempData.setTempName(cursor.getString(cursor.getColumnIndex("name")));
				tempData.setTempTel(cursor.getString(cursor.getColumnIndex("age")));
				tempData.setTempEmail(cursor.getString(cursor.getColumnIndex("email")));
				
				showDialog(1, nameBundle);

				return false;
			}
		});
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				cursor.moveToPosition(position);
				tempData.setTempName(cursor.getString(cursor.getColumnIndex("name")));
				tempData.setTempTel(cursor.getString(cursor.getColumnIndex("age")));
				tempData.setTempEmail(cursor.getString(cursor.getColumnIndex("email")));
				Intent i = new Intent(
						BookMarkActivity.this,
						AddressInfoActivity.class);
				startActivity(i);
				
			}
		});
        
        
        
    }
    
    
    
	private void selectDB() {
		
		final String sql = "SELECT * FROM test2 order by name asc;";
		db = DBHELPER.getWritableDatabase();
		cursor = db.rawQuery(sql, null);
		if (cursor.getCount() > 0) {
			startManagingCursor(cursor);
			DBAdapter dbAdapter = new DBAdapter(this, cursor);
			list.setAdapter(dbAdapter);

		}
		
		
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id, final Bundle name) {

		switch (id) {
		case 1:
			return new AlertDialog.Builder(this)
					.setTitle(name.getString("name"))
					.setItems(R.array.bookMark_menu_array,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									switch (which) {
									case 0:
										Intent i = new Intent(
												BookMarkActivity.this,
												EditFormActivity.class);
										startActivity(i);
										finish();
										break;
									case 1:
										
										final String sql = "DELETE FROM test2 WHERE name = '"+name.getString("name")+"';";
										db = DBHELPER.getWritableDatabase();
										db.execSQL(sql);
										Intent tempIntent = new Intent(
												BookMarkActivity.this,
												MainActivity.class);
										tempData.setTemp(2); //메인화면에서 즐겨찾기 메뉴를 켜줄지, 아니면 검색메뉴를 켜줄지를 결정해준다.
										startActivity(tempIntent);
										finish();
										break;
									case 2:
										Intent tempIntent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tempData.getTempTel()+""));
										 startActivity(tempIntent3);
										break;
									case 3:
										Intent tempIntent4 = new Intent(Intent.ACTION_VIEW);
										String smsBody = "보내실 문자를 입력하세요.";
										tempIntent4.putExtra("sms_body", smsBody);
										tempIntent4.putExtra("address",tempData.getTempTel()); // 받는사람 번호
										tempIntent4.setType("vnd.android-dir/mms-sms");
										startActivity(tempIntent4);
										break;
									
									}
									

								}
							}).create();

		}

		return null;
	}
	

}


