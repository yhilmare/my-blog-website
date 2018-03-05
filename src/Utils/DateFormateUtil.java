package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormateUtil {
	public static String getDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
}
