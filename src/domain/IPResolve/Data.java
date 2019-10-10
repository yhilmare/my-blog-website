package domain.IPResolve;

public class Data {
	
	private Mso mso;
	private String ip;
	private Position position;
	public Mso getMso() {
		return mso;
	}
	public void setMso(Mso mso) {
		this.mso = mso;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
}
