package my.real.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 주소를 자세하게 보여주는 Activity입니다.
 *
 */
public class AddressInfoActivity extends Activity {

	final String dbName = "person.db";
	final int dbVersion = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_info);
		final MyData tempData = MyData.getInstance();
		TextView tx1 = (TextView)findViewById(R.id.textView11);
		TextView tx2 = (TextView)findViewById(R.id.textView22);
		TextView tx3 = (TextView)findViewById(R.id.textView33);

		
		tx1.setText("Name :"+tempData.getTempName());
		tx2.setText("Phone Number :"+tempData.getTempTel());
		tx3.setText("E-mail Address :"+tempData.getTempEmail());
	}
}
