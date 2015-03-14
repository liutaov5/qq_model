/**
 * 用来存放用户id和密码的
 */
package Common;

public class User implements java.io.Serializable{

	private String userId;
	private String Passwd;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}
	
}
