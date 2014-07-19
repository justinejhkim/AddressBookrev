package my.real.addressbook;


import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * �ּҷ� ����
 * �� MainActivity�� �ٷ� ���� ó�� �����ϸ� ������ ��Ƽ��Ƽ�Դϴ�. �� ���ξ�Ƽ��Ƽ�� 2���� ������ �����Ǿ� ������, ���� BookMarkActivity�� AddressSearchActivity�Դϴ�. 
 * AddressSearchActivity�� EditFormActivity(��������� ���)�� FormActivity(�ּ� �߰������ ���)�� ����Ǿ��ֽ��ϴ�. 
 * �׸��� DBHelper�� DBAdapter�� ���� �����ͺ��̽� Ŀ���� �̿��� ����Ʈ�䱸���� �����ͺ��̽� �̿뿡 �ʿ��� Ŭ�����Դϴ�.
 * MyData�� �̱���Ŭ�����ν�, ������ Ŭ��������(�� ��Ƽ��Ƽ����)�������� ������ ���Ͽ�, �� Ŭ������ ��ü�� �ϳ� �����Ͽ���, �� �����͸� �����ϴ� �Ű�ü�� ����.
 * 
 * 
 * 
 *
 */
@SuppressWarnings({ "deprecation", "unused" })
public class MainActivity extends TabActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);    
	   // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
	   //                               WindowManager.LayoutParams.FLAG_FULLSCREEN); 


		setContentView(R.layout.main_activity);
		//�ش� R Class(xml�� ������� �������� ������� �̸��� �������ش�.) ���̾ƿ��� �����ͼ� ȭ�鿡 �����ش�.
		
		TabHost tabHost = getTabHost(); //�ش� ���̾ƿ��� �ִ� ��ȣ��Ʈ�� �����´�.
		TabHost.TabSpec spec; //�� ȣ��Ʈ�� �� ���������� ���������� �����Ѵ�.
		Intent intent;  //����Ʈ�̴�. ����Ʈ�� ��Ƽ��Ƽ���� �޽����� �����ϱ� ���� ������ �����ν�, ��Ƽ��Ƽ���� Ŀ�´����̼��� ���� ���δ�.
		
		intent = new Intent(this, BookMarkActivity.class);//this �� �� MainActivity�� context�����̴� context�� ���ø����̼��� �������� ������ ��� �ִ�.
		spec = tabHost.newTabSpec("tab1").setIndicator("Favorites").setContent(intent);//tab1�̶�� �� spec�� �±�(������ ����)�� �ް�, 
		tabHost.addTab(spec);				//�� ����ִ� �̸��� ���ã���� �̸��� �ְ�, �׳����� ����Ʈ�� �����Ѵ�.
		
		
		intent = new Intent(this, AddressSearchActivity.class);
		spec = tabHost.newTabSpec("tab2").setIndicator("Contacts").setContent(intent);
		tabHost.addTab(spec);
		
		MyData tempData = MyData.getInstance();  //�̱�������, Ŭ�������� �����͸� �����ϱ� ���� ��ü�� �޾ƿɴϴ�. MyData�� ������ Ŭ�������� ������ �̵��� ���ؼ��� ���Ǿ����ϴ�.
		if(tempData.getTemp()==1){		//���� �޾ƿ� getTemp()��� ��Ʈ���� 1�̶�� ����óȭ����, 2��� ���ã�� ȭ���� �����ݴϴ�.
			tabHost.setCurrentTab(1);
			tempData.setTemp(0);
		}
		else if(tempData.getTemp()==2){
			tabHost.setCurrentTab(0);
			tempData.setTemp(0);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

}
