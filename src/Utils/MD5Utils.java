package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;


public class MD5Utils {
	
	public static String getToken(String info){
		
		try {
			MessageDigest diggest = MessageDigest.getInstance("md5");
			byte[] buffer = diggest.digest(info.getBytes());
			Base64 encoder = new Base64();
			return encoder.encodeToString(buffer);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
