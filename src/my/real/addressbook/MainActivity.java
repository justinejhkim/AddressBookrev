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
 * 주소록 설명
 * 이 MainActivity가 바로 제일 처음 실행하면 나오는 액티비티입니다. 이 메인액티비티는 2개의 탭으로 구성되어 있으며, 각각 BookMarkActivity와 AddressSearchActivity입니다. 
 * AddressSearchActivity는 EditFormActivity(편집기능을 담당)와 FormActivity(주소 추가기능을 담당)와 연결되어있습니다. 
 * 그리고 DBHelper와 DBAdapter는 각각 데이터베이스 커서를 이용한 리스트뷰구성과 데이터베이스 이용에 필요한 클래스입니다.
 * MyData는 싱글턴클래스로써, 각각의 클래스간의(즉 액티비티간의)데이터의 공유를 위하여, 이 클래스에 객체를 하나 생성하여서, 그 데이터를 저장하는 매개체로 쓴다.
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
		//해당 R Class(xml로 만들어진 위젯들의 상수들의 이름을 지정해준다.) 레이아웃을 가져와서 화면에 보여준다.
		
		TabHost tabHost = getTabHost(); //해당 레이아웃에 있는 텝호스트를 가져온다.
		TabHost.TabSpec spec; //텝 호스트의 텝 구성물들의 참조변수를 선언한다.
		Intent intent;  //인텐트이다. 인텐트는 액티비티간의 메시지를 전달하기 위한 일종의 엽서로써, 액티비티간의 커뮤니케이션을 위해 쓰인다.
		
		intent = new Intent(this, BookMarkActivity.class);//this 는 이 MainActivity의 context정보이다 context는 어플리케이션의 전반적인 정보를 담고 있다.
		spec = tabHost.newTabSpec("tab1").setIndicator("Favorites").setContent(intent);//tab1이라는 텝 spec의 태그(일종의 제목)을 달고, 
		tabHost.addTab(spec);				//그 담고있는 이름을 즐겨찾기라느 이름을 주고, 그내용은 인텐트를 전달한다.
		
		
		intent = new Intent(this, AddressSearchActivity.class);
		spec = tabHost.newTabSpec("tab2").setIndicator("Contacts").setContent(intent);
		tabHost.addTab(spec);
		
		MyData tempData = MyData.getInstance();  //싱글턴패턴, 클래스간의 데이터를 전달하기 위한 객체를 받아옵니다. MyData는 오로지 클래스간의 데이터 이동을 위해서만 사용되었습니다.
		if(tempData.getTemp()==1){		//만약 받아온 getTemp()라는 인트값이 1이라면 연락처화면을, 2라면 즐겨찾기 화면을 보여줍니다.
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
