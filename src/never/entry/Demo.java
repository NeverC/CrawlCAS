package never.entry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {

	public static void main(String[] args) {
		String regEx = "共.页";
		String s = "呵呵， 共34页成都曾多次";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(s);
		boolean rs = mat.find();
		for(int i=1;i<=mat.groupCount();i++){
			System.out.println(mat.group(i));
		} 

	}
}
