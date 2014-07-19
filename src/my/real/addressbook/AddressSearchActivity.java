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
 * 연락처화면(검색하는 창이 있는)을 구성하는 Activity입니다.
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);    //타이틀을 없애는 코드입니다.

		setContentView(R.layout.address_search_activity);		//add_search라는 레이아웃을 보여줍니다.
		list = (ListView) findViewById(R.id.addSearchList);		//위의 레이아웃에 있는 리스트뷰를 받아옵니다.
		
		selectDB();	//리스트뷰에 각각의 데이터들을 담습니다.(자세한건 selectDB 메소드 보기)
		
		final ImageView imageAddButton = (ImageView) findViewById(R.id.imageButton1);  //추가하는 기능을 하는 이미지 버튼
		final EditText searchEditText = (EditText)findViewById(R.id.searchEditText);	//검색하는 기능을 담당
		if(tempData.getTempSerchEditText()==null){
			searchEditText.setText("");
		}
		else{
			searchEditText.setText(tempData.getTempSerchEditText());
		}
		//검색창에서, 만약 해당 검색을 했다면, 그 검색한 것들이 검색창에 표시되게 하는 코드 
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
		//EditText의 리스너이다. 이 리스너는 키를 입력했을때의 리스너로 다 임포트하면 된다.
		
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
				Bundle nameBundle = new Bundle();//번들이란 일종의 데이터 전송을 위해 담는 상자같은 역할을 한다.
				nameBundle.putString("name",
						cursor.getString(cursor.getColumnIndex("name"))); 
				tempData.setTempName(cursor.getString(cursor.getColumnIndex("name")));
				tempData.setTempTel(cursor.getString(cursor.getColumnIndex("age")));
				tempData.setTempEmail(cursor.getString(cursor.getColumnIndex("email")));
				//이렇게 싱글턴 클래스를 이용하여 클래스간의 데이터전송을 하고있다.
				showDialog(1, nameBundle);

				return false;//폴스가 되어야지 롱클릭이 끝났다는 것을 의미해주니까 라고 추측중
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
				//인텐트를 이용하여 다른 액티비티를 여는 방법이다.
			}
		});
		
		

	}

	private void selectDB() {

		final String sql = "SELECT * FROM test order by name asc;"; // sql문구를 String에 저장합니다.
		db = DBHELPER.getWritableDatabase(); //db라는 SQLiteDatabase객체에 DBHelper클래스(의 인스턴스)를 이용하여 데이터베이스관리하는 객체인스턴스를 불러옵니다.
		cursor = db.rawQuery(sql, null); //휴대폰 데이터베이스에서 각각의 정보들을 열람하기위한 커서(마우스커서와 비슷한개념임) 
										//객체에다 해당 sql을 적용시킵니다. 이경우에는 한줄단위로 데이터를 커서에 저장하는 역할을 합니다.
		
		if (cursor.getCount() > 0) { //커서에 담긴 정보, 즉 데이터베이스에 , 즉 주소록에 주소가 하나이상 저장이 되어있다면
			startManagingCursor(cursor);
			DBAdapter dbAdapter = new DBAdapter(this, cursor); // DBAdapter라는 Adapter를 이용하여 리스트에다가 주소들을 저장합니다.
			list.setAdapter(dbAdapter);
		}

	}
	

	

	@Override
	protected Dialog onCreateDialog(int id, final Bundle name) { //알림창을 설정하는 함수입니다. 이 함수의 경우, Activity클래를 상속받는 추상클래스입니다.)

		switch (id) {
		case 1: //알림창 1번을 여는 경우
			return new AlertDialog.Builder(this)
					.setTitle(name.getString("name")) //알림창 이름설정
					.setItems(R.array.dialog_menu_array, //알림창의 종류 설정 ==>res폴더에 values폴더에 dialog_array참조
							new DialogInterface.OnClickListener() { // 각각의 메뉴에 대한 클릭이벤트 적용

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									switch (which) {
									case 0:// 해당 주소를 편집하는 화면으로 넘어갑니다.
										Intent i = new Intent(
												AddressSearchActivity.this,
												EditFormActivity.class); //화면에 대한 Intent를 만듭니다.
										startActivity(i);//인텐트를 실행시킵니다.
										finish(); //현재 화면을 종료합니다.
										break;
									case 1://삭제하는 경우입니다.
										
										String sql = "DELETE FROM test WHERE name = '"+name.getString("name")+"' AND age = '"+
										tempData.getTempTel()+"';";
										db = DBHELPER.getWritableDatabase(); 
										
										
										
										db.execSQL(sql);//주소록의 AddressSearchActivity에 있는(연락처화면)에 있는 테이블의 주소를 지웁니다.
										sql = "DELETE FROM test2 WHERE name = '"+name.getString("name")+"' AND age = '"+
										tempData.getTempTel()+"';";
										db.execSQL(sql);//주소록의 BookMarkActivity에 있는(즐겨찾기화면)에 있는 테이블의 주소를 지웁니다.										
										Intent tempIntent = new Intent(
												AddressSearchActivity.this,
												MainActivity.class);
										tempData.setTemp(1); //메인화면에서 곧바로 즐겨찾기 메뉴를 켜줄지, 아니면 검색메뉴를 켜줄지를 결정해준다.
										startActivity(tempIntent);
										finish();
										break;
									case 2: //즢겨찾기에 추가하는 경우입니다.
										final String sql2 = String.format(
												"INSERT INTO TEST2 VALUES(NULL, '%s', '%s', '%s');",
												tempData.getTempName(), tempData.getTempTel(), tempData.getTempEmail());
										db.execSQL(sql2); //즐겨찾기용 데이터베이스 테이블에다가 추가를 해줍니다.
										Intent tempIntent2 = new Intent(
												AddressSearchActivity.this,
												MainActivity.class);
										tempData.setTemp(2); //메인화면에서 즐겨찾기 메뉴를 켜줄지, 아니면 검색메뉴를 켜줄지를 결정해준다. 
															//이 경우에는 2라는 숫자를 tempData의 Temp인트형변수에다 집어넣고, 나중에 즐겨찾기 화면이 나옵니다.
										startActivity(tempIntent2);
										finish();
										break;
									case 3: //전화걸기를 시작합니다.
										Intent tempIntent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tempData.getTempTel()+""));
										 startActivity(tempIntent3);
										break;
									case 4://문자메시지 보여주기를 시작합니다.
										Intent tempIntent4 = new Intent(Intent.ACTION_VIEW);
										String smsBody = "Type in your message.";
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
