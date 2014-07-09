package never.entry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import never.utils.BaseUtil;
import never.utils.DbUtil;

public class Main {

	private Set<String> urlSet = new HashSet<String>();
	private Queue<String> queue = new LinkedList<String>();
	private DbUtil dbUtil = new DbUtil();
	private Connection conn = null;

	public static void main(String[] args) {

		new Main().startCrawler();
	}

	public void startCrawler() {
		
		if (initialCrawler()) {
			
			String url = getInitialURL();
			int i = 0;
			while(url != null && i++ < 8){
				System.out.println(url);
				parseUrl(url);
				url = getInitialURL();
			}
			System.out.println("Crawler Finish Task!");
		}
	}

	public boolean initialCrawler() {

		conn = dbUtil.getConnection();
		System.out.println("Initialize url set starting.");
		try {
			PreparedStatement urlStmt = conn.prepareStatement("select url_md5 from news");
			ResultSet urlRS = urlStmt.executeQuery();
			while (urlRS.next()) {
				String md5Temp = BaseUtil.encryptMD5(urlRS.getString(1));
				if (!urlSet.add(md5Temp)) {
					System.out.println("An error occurs when add url md5 to urlSet");
					return false;
				}
			}
			urlStmt.close();
			urlRS.close();
			System.out.println("Initialize url set finished.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getInitialURL(){
		String retURL = null;
		try {
			PreparedStatement urlStmt = conn.prepareStatement("select url from seed where status = 0 limit 1");
			ResultSet urlRS = urlStmt.executeQuery();
			while (urlRS.next()) {
				retURL = urlRS.getString(1);
			}
			urlStmt.close();
			urlRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retURL;
	}
	
	public String updateURL(){
		String retURL = null;
		try {
			PreparedStatement urlStmt = conn.prepareStatement("select url from seed where status = 0 limit 1");
			ResultSet urlRS = urlStmt.executeQuery();
			while (urlRS.next()) {
				retURL = urlRS.getString(1);
			}
			urlStmt.close();
			urlRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retURL;
	}
	
	
	public void parseUrl(String url){
		try {
			Document doc = Jsoup.connect(url).get();
			doc
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
