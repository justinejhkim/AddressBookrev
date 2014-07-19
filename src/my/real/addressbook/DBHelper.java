package my.real.addressbook;
import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 데이터베이스의 테이블을 생성해주고, 데이터베이스의 DBHelper라는 객체생성의 역할과, 데이터베이스관리하는 클래스입니다.
 *
 */
public class DBHelper extends SQLiteOpenHelper { 

	String sql;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		sql = "CREATE TABLE test ( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " name TEXT, age TEXT, email TEXT);";
		db.execSQL(sql);
		sql = "CREATE TABLE test2 ( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " name TEXT, age TEXT, email TEXT);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 버전이 업그레이드 됐을 경우 작업할 내용을 작성합니다.
	}

}
