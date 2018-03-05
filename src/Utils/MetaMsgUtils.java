package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetaMsgUtils {
	
	public static String getMetaMessage(String src){
		Pattern p = Pattern.compile("[\u4E00-\u9FA5,¡££¡£¬!]{1,}");
		Matcher result = p.matcher(src);
		StringBuffer buffer = new StringBuffer();
		while(result.find()){
			buffer.append(result.group());
		}
		return buffer.toString().length() > 140?buffer.substring(0, 140):buffer.toString();
	}
}
