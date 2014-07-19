package my.real.addressbook;
import com.mopub.*;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * 편집을 하는 Activity입니다.
 *
 */
public class EditFormActivity extends Activity {
	private MoPubView moPubView;
	final String dbName = "person.db";
	final int dbVersion = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_form);
		
		// moPub Integration
		moPubView = (MoPubView) findViewById(R.id.adview);
		moPubView.setAdUnitId("e26a0ad24ad84231bfe6ab73189ea628");
		moPubView.loadAd();
//		moPubView.setBannerAdListener((BannerAdListener) this);		
		
		
		
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_form);
		final MyData tempData = MyData.getInstance();
		final EditText f_name = (EditText) findViewById(R.id.f_name);
		final EditText f_tel = (EditText) findViewById(R.id.f_tel);
		final EditText f_email = (EditText) findViewById(R.id.f_email);
		
		f_name.setText(tempData.getTempName());
		f_tel.setText(tempData.getTempTel());
		f_email.setText(tempData.getTempEmail());
		
		
		final DBHelper dbHelper = new DBHelper(this, dbName, null, dbVersion);

		Button bt = (Button) findViewById(R.id.submit);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SQLiteDatabase db;
				String name = f_name.getText().toString();
				String tel = f_tel.getText().toString();
				String email = f_email.getText().toString();
				Cursor cursor;
				
				db = dbHelper.getWritableDatabase();
				String sql = "SELECT * FROM test2 WHERE name = '"+
				tempData.getTempName()+"' AND age = '"+tempData.getTempTel()+"';";
				cursor = db.rawQuery(sql, null);
				
				if (cursor.getCount() > 0) {
					
					sql = "DELETE FROM test WHERE name = '"+
					tempData.getTempName()+"' AND age = '"+tempData.getTempTel()+"';";
					db.execSQL(sql);
					sql = "DELETE FROM test2 WHERE name = '"+
							tempData.getTempName()+"' AND age = '"+tempData.getTempTel()+"';";
							db.execSQL(sql);
					sql = String.format(
							"INSERT INTO TEST VALUES(NULL, '%s', '%s', '%s');",
							name, tel, email);				
					db.execSQL(sql);
					sql = String.format(
							"INSERT INTO TEST2 VALUES(NULL, '%s', '%s', '%s');",
							name, tel, email);				
					db.execSQL(sql);
				}
				else{
				
					sql = "DELETE FROM test WHERE name = '"+
					tempData.getTempName()+"' AND age = '"+tempData.getTempTel()+"';";
					db.execSQL(sql);
					
					sql = String.format(
							"INSERT INTO TEST VALUES(NULL, '%s', '%s', '%s');",
							name, tel, email);				
					db.execSQL(sql);
				}
				Intent i = new Intent(EditFormActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});

	}
	
    protected void onDestroy() {
        moPubView.destroy();
        super.onDestroy(); 
    }
    
    
}
