package my.real.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * 주소 추가를 하는 Activity입니다.
 *
 */
public class FormActivity extends Activity { ///주소를 추가하는 경우 입니다.

	final String dbName = "person.db";
	final int dbVersion = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_form);
		final EditText f_name = (EditText) findViewById(R.id.f_name);
		final EditText f_tel = (EditText) findViewById(R.id.f_tel);
		final EditText f_email = (EditText) findViewById(R.id.f_email);

		final DBHelper dbHelper = new DBHelper(this, dbName, null, dbVersion);

		Button bt = (Button) findViewById(R.id.submit);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String sql;
				SQLiteDatabase db;
				String name = f_name.getText().toString();
				String tel = f_tel.getText().toString();
				String email = f_email.getText().toString();

				db = dbHelper.getWritableDatabase();
				sql = String.format(
						"INSERT INTO TEST VALUES(NULL, '%s', '%s', '%s');",
						name, tel, email);
				db.execSQL(sql);
				Intent i = new Intent(FormActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});

	}
}
