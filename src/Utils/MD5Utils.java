package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Utils {
	
	public static String getToken(String info){
		
		try {
			MessageDigest diggest = MessageDigest.getInstance("md5");
			byte[] buffer = diggest.digest(info.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(buffer);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
