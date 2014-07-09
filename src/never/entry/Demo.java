package never.entry;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Demo {

	public static void main(String[] args) {
		
		try {
			Document doc = Jsoup.connect("http://www.cas.cn/xw/zyxw/yw/201203/t20120327_3518706.shtml").get();
			Elements bodyElements = doc.select("div.TRS_Editor");
			System.out.println(bodyElements.size());
			Elements bodyElements2 = doc.select("div.cas_content");
			System.out.println(bodyElements2.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
