package my.real.addressbook;
/**
 * 오로지 클래스간의 데이터를 이동하는데만 사용이 되는 클래스입니다.
 * 이 클래스의 인스턴스를 생성하여 거기에다가 각각의 정보를 저장하여 넘깁니다.
 *
 */
public class MyData {

	private static final MyData instance = new MyData();
	private int temp;
	private String tempName;
	private String tempTel;
	private String tempEmail;
	private String tempSerchEditText;
	
	private MyData(){
		
	}
	
	public static MyData getInstance(){
		return instance;
	}
	
	
	int getTemp() {
		return temp;
	}
	
	void setTemp(int temp) {
		this.temp = temp;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTempTel() {
		return tempTel;
	}

	public void setTempTel(String tempTel) {
		this.tempTel = tempTel;
	}

	public String getTempEmail() {
		return tempEmail;
	}

	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}
	
	public String getTempSerchEditText(){
		return tempSerchEditText;
	}
	
	public void setTempSerchEditText(String tempSerchEditText){
		this.tempSerchEditText = tempSerchEditText;
	}
	
	
	
	
}
