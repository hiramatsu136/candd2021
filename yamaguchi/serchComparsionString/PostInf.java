package serchComparsionString;

public class PostInf {
	private int postNo; //郵便番号
	private String addr;
	
	
	PostInf( int post, String address) {
		postNo = post;
		addr = address;
	}
	
	public String getAddr() {
		return addr;
	}

	public int getPostNo() {
		return postNo;
	}
}
