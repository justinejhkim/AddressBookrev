package my.real.addressbook;
import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * �����ͺ��̽��� ���̺��� �������ְ�, �����ͺ��̽��� DBHelper��� ��ü������ ���Ұ�, �����ͺ��̽������ϴ� Ŭ�����Դϴ�.
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
		// ������ ���׷��̵� ���� ��� �۾��� ������ �ۼ��մϴ�.
	}

}
