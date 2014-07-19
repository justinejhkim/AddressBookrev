package my.real.addressbook;
/**
 * ������ Ŭ�������� �����͸� �̵��ϴµ��� ����� �Ǵ� Ŭ�����Դϴ�.
 * �� Ŭ������ �ν��Ͻ��� �����Ͽ� �ű⿡�ٰ� ������ ������ �����Ͽ� �ѱ�ϴ�.
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
