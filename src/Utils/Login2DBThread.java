package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogLogin2DBService;
import domain.blog_login;
import domain.IPResolve.IPResolveData;

public class Login2DBThread implements Runnable{
	
	private String IPAddress = null;
	private String visitor_id = null;
	private String visitor_nickname = null;
	private Thread t = null;
	
	public Login2DBThread(String IPAddress, String visitor_id, String visitor_nickname) {
		this.visitor_id = visitor_id;
		this.visitor_nickname = visitor_nickname;
		this.IPAddress = IPAddress;
		this.t = new Thread(this);
		this.t.start();
	}
	
	public void customerJoin() {
		try {
			this.t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			if (this.IPAddress == null) {
				return;
			}
			boolean flag = Pattern.matches("^([0-9]{1,3}\\.){3,3}[0-9]{1,3}$", this.IPAddress);
			if (!flag) {
				return;
			}
			URL url = new URL("https://m.so.com/position?ip=" + IPAddress);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			InputStream input = con.getInputStream();
			byte[] buffer = new byte[input.available()];
			int len = input.read(buffer);
			while(len != -1) {
				len = input.read(buffer, 0, len);
			}
			input.close();
			String returnStr = new String(buffer, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			IPResolveData data = mapper.readValue(returnStr, IPResolveData.class);
			blog_login login = new blog_login();
			login.setVisitor_id(this.visitor_id);
			login.setLogin_nickname(this.visitor_nickname);
			login.setLogin_ip(data.getData().getIp());
			login.setLogin_province(data.getData().getPosition().getProvince());
			login.setLogin_isp(data.getData().getPosition().getIsp());
			login.setLogin_area(data.getData().getPosition().getArea());
			login.setLogin_address(data.getData().getPosition().getAddress());
			login.setLogin_city(data.getData().getPosition().getCity());
			login.setLogin_country(data.getData().getPosition().getCountry());
			login.setLogin_street(data.getData().getPosition().getStreet());
			BlogLogin2DBService service = new BlogLogin2DBService();
			service.insertVisit(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
