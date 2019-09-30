package domain;

public class QQApp {
	
	private String APPID;
	private String APPKey;
	private String redirectUri;
	
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public String getAPPKey() {
		return APPKey;
	}
	public void setAPPKey(String aPPKey) {
		APPKey = aPPKey;
	}

}
