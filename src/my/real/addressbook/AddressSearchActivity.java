package my.real.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * ����óȭ��(�˻��ϴ� â�� �ִ�)�� �����ϴ� Activity�Դϴ�.
 *
 */
public class AddressSearchActivity extends Activity {

	final String DBNAME = "person.db";
	final int DBVERSION = 2;
	final private DBHelper DBHELPER = new DBHelper(this, DBNAME, null, DBVERSION);
	final private MyData tempData = MyData.getInstance();
	private ListView list;
	private SQLiteDatabase db;
	private Cursor cursor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);    //Ÿ��Ʋ�� ���ִ� �ڵ��Դϴ�.

		setContentView(R.layout.address_search_activity);		//add_search��� ���̾ƿ��� �����ݴϴ�.
		list = (ListView) findViewById(R.id.addSearchList);		//���� ���̾ƿ��� �ִ� ����Ʈ�並 �޾ƿɴϴ�.
		
		selectDB();	//����Ʈ�信 ������ �����͵��� ����ϴ�.(�ڼ��Ѱ� selectDB �޼ҵ� ����)
		
		final ImageView imageAddButton = (ImageView) findViewById(R.id.imageButton1);  //�߰��ϴ� ����� �ϴ� �̹��� ��ư
		final EditText searchEditText = (EditText)findViewById(R.id.searchEditText);	//�˻��ϴ� ����� ���
		if(tempData.getTempSerchEditText()==null){
			searchEditText.setText("");
		}
		else{
			searchEditText.setText(tempData.getTempSerchEditText());
		}
		//�˻�â����, ���� �ش� �˻��� �ߴٸ�, �� �˻��� �͵��� �˻�â�� ǥ�õǰ� �ϴ� �ڵ� 
		searchEditText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
					final String sql = "SELECT * FROM test WHERE name LIKE '"+
							searchEditText.getText().toString()+"%' order by name asc;";
					db = DBHELPER.getWritableDatabase();
					cursor = db.rawQuery(sql, null);
					
					if (cursor.getCount() > 0) {
						startManagingCursor(cursor);
						DBAdapter dbAdapter = new DBAdapter(AddressSearchActivity.this, cursor);
						list.setAdapter(dbAdapter);
						tempData.setTempSerchEditText(searchEditText.getText().toString());
						
					}
					
					if(keyCode == event.KEYCODE_ENTER){
						return true;
					}
							
				return false;
				
			}
		});
		//EditText�� �������̴�. �� �����ʴ� Ű�� �Է��������� �����ʷ� �� ����Ʈ�ϸ� �ȴ�.
		
		imageAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddressSearchActivity.this,
						FormActivity.class);
				startActivity(i);
				finish();

			}
		});
		
		

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int position, long id) {

				cursor.moveToPosition(position);
				Bundle nameBundle = new Bundle();//�����̶� ������ ������ ������ ���� ��� ���ڰ��� ������ �Ѵ�.
				nameBundle.putString("name",
						cursor.getString(cursor.getColumnIndex("name"))); 
				tempData.setTempName(cursor.getString(cursor.getColumnIndex("name")));
				tempData.setTempTel(cursor.getString(cursor.getColumnIndex("age")));
				tempData.setTempEmail(cursor.getString(cursor.getColumnIndex("email")));
				//�̷��� �̱��� Ŭ������ �̿��Ͽ� Ŭ�������� ������������ �ϰ��ִ�.
				showDialog(1, nameBundle);

				return false;//������ �Ǿ���� ��Ŭ���� �����ٴ� ���� �ǹ����ִϱ� ��� ������
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
						AddressSearchActivity.this,
						AddressInfoActivity.class);
				startActivity(i);
				//����Ʈ�� �̿��Ͽ� �ٸ� ��Ƽ��Ƽ�� ���� ����̴�.
			}
		});
		
		

	}

	private void selectDB() {

		final String sql = "SELECT * FROM test order by name asc;"; // sql������ String�� �����մϴ�.
		db = DBHELPER.getWritableDatabase(); //db��� SQLiteDatabase��ü�� DBHelperŬ����(�� �ν��Ͻ�)�� �̿��Ͽ� �����ͺ��̽������ϴ� ��ü�ν��Ͻ��� �ҷ��ɴϴ�.
		cursor = db.rawQuery(sql, null); //�޴��� �����ͺ��̽����� ������ �������� �����ϱ����� Ŀ��(���콺Ŀ���� ����Ѱ�����) 
										//��ü���� �ش� sql�� �����ŵ�ϴ�. �̰�쿡�� ���ٴ����� �����͸� Ŀ���� �����ϴ� ������ �մϴ�.
		
		if (cursor.getCount() > 0) { //Ŀ���� ��� ����, �� �����ͺ��̽��� , �� �ּҷϿ� �ּҰ� �ϳ��̻� ������ �Ǿ��ִٸ�
			startManagingCursor(cursor);
			DBAdapter dbAdapter = new DBAdapter(this, cursor); // DBAdapter��� Adapter�� �̿��Ͽ� ����Ʈ���ٰ� �ּҵ��� �����մϴ�.
			list.setAdapter(dbAdapter);
		}

	}
	

	

	@Override
	protected Dialog onCreateDialog(int id, final Bundle name) { //�˸�â�� �����ϴ� �Լ��Դϴ�. �� �Լ��� ���, ActivityŬ���� ��ӹ޴� �߻�Ŭ�����Դϴ�.)

		switch (id) {
		case 1: //�˸�â 1���� ���� ���
			return new AlertDialog.Builder(this)
					.setTitle(name.getString("name")) //�˸�â �̸�����
					.setItems(R.array.dialog_menu_array, //�˸�â�� ���� ���� ==>res������ values������ dialog_array����
							new DialogInterface.OnClickListener() { // ������ �޴��� ���� Ŭ���̺�Ʈ ����

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									switch (which) {
									case 0:// �ش� �ּҸ� �����ϴ� ȭ������ �Ѿ�ϴ�.
										Intent i = new Intent(
												AddressSearchActivity.this,
												EditFormActivity.class); //ȭ�鿡 ���� Intent�� ����ϴ�.
										startActivity(i);//����Ʈ�� �����ŵ�ϴ�.
										finish(); //���� ȭ���� �����մϴ�.
										break;
									case 1://�����ϴ� ����Դϴ�.
										
										String sql = "DELETE FROM test WHERE name = '"+name.getString("name")+"' AND age = '"+
										tempData.getTempTel()+"';";
										db = DBHELPER.getWritableDatabase(); 
										
										
										
										db.execSQL(sql);//�ּҷ��� AddressSearchActivity�� �ִ�(����óȭ��)�� �ִ� ���̺��� �ּҸ� ����ϴ�.
										sql = "DELETE FROM test2 WHERE name = '"+name.getString("name")+"' AND age = '"+
										tempData.getTempTel()+"';";
										db.execSQL(sql);//�ּҷ��� BookMarkActivity�� �ִ�(���ã��ȭ��)�� �ִ� ���̺��� �ּҸ� ����ϴ�.										
										Intent tempIntent = new Intent(
												AddressSearchActivity.this,
												MainActivity.class);
										tempData.setTemp(1); //����ȭ�鿡�� ��ٷ� ���ã�� �޴��� ������, �ƴϸ� �˻��޴��� �������� �������ش�.
										startActivity(tempIntent);
										finish();
										break;
									case 2: //�n��ã�⿡ �߰��ϴ� ����Դϴ�.
										final String sql2 = String.format(
												"INSERT INTO TEST2 VALUES(NULL, '%s', '%s', '%s');",
												tempData.getTempName(), tempData.getTempTel(), tempData.getTempEmail());
										db.execSQL(sql2); //���ã��� �����ͺ��̽� ���̺��ٰ� �߰��� ���ݴϴ�.
										Intent tempIntent2 = new Intent(
												AddressSearchActivity.this,
												MainActivity.class);
										tempData.setTemp(2); //����ȭ�鿡�� ���ã�� �޴��� ������, �ƴϸ� �˻��޴��� �������� �������ش�. 
															//�� ��쿡�� 2��� ���ڸ� tempData�� Temp��Ʈ���������� ����ְ�, ���߿� ���ã�� ȭ���� ���ɴϴ�.
										startActivity(tempIntent2);
										finish();
										break;
									case 3: //��ȭ�ɱ⸦ �����մϴ�.
										Intent tempIntent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tempData.getTempTel()+""));
										 startActivity(tempIntent3);
										break;
									case 4://���ڸ޽��� �����ֱ⸦ �����մϴ�.
										Intent tempIntent4 = new Intent(Intent.ACTION_VIEW);
										String smsBody = "Type in your message.";
										tempIntent4.putExtra("sms_body", smsBody);
										tempIntent4.putExtra("address",tempData.getTempTel()); // �޴»�� ��ȣ
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
