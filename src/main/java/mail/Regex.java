package mail;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		String line = "Merhaba Hocam, \n"
	+ "SYT dersinin 2.proje grup üyeleri aşağıdaki gibidir:\n"
	+ "5110000000-Adı Soyadı\n" + "5110000001-Adı Soyadı\n" + "5110000002-Adı Soyadı\n";


		Pattern p = Pattern.compile("((\\d+)(-)(.*)((\r\n)|(\n)))");

		Matcher m = p.matcher(line);
	

		while (m.find()) {
			System.out.println("found: " + m.group());

//			System.out.println("found: " + m.group(11));

		}

		System.out.println("done");
	}
}