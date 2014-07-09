package never.entry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.omg.CORBA.PRIVATE_MEMBER;

import never.utils.BaseUtil;
import never.utils.DbUtil;

public class Main {

	private Set<String> urlSet = new HashSet<String>();
	private DbUtil dbUtil = new DbUtil();
	private Connection conn = null;

	private String category = null;
	private int count = 0;

	public static void main(String[] args) {

		new Main().startCrawler();
	}

	public void startCrawler() {
		if (initialCrawler()) {
			String tempStr = getInitialUrlAndClass();
			
			if(tempStr.equals("NOURL")){
				System.out.println("Hello");
				return;
			}
			String sArray[] = tempStr.split("::");
			String url = sArray[0];
			category = sArray[1];

			while (url != null && category != null) {
				System.out.println(url);
				System.out.println(category);
				parseUrl(url);
				updateInitialUrl(url);
				String tempStr2 = getInitialUrlAndClass();
				
				if(tempStr2.equals("NOURL")){
					return;
				}
				String sArray2[] = tempStr2.split("::");
				url = sArray2[0];
				category = sArray2[1];
			}
			System.out.println("Crawler Finish Task!");
		}
	}

	public boolean initialCrawler() {
		conn = dbUtil.getConnection();
		try {
			PreparedStatement urlStmt = conn
					.prepareStatement("select url_md5 from news");
			ResultSet urlRS = urlStmt.executeQuery();
			while (urlRS.next()) {
				String md5Temp = BaseUtil.encryptMD5(urlRS.getString(1));
				if (!urlSet.add(md5Temp)) {
					System.out
							.println("An error occurs when add url md5 to urlSet");
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

	public String getInitialUrlAndClass() {
		String retUrl = null;
		String retClass = null;
		try {
			PreparedStatement urlStmt = conn
					.prepareStatement("select url, class from seed where status = 0 limit 1");
			ResultSet urlRS = urlStmt.executeQuery();

			
			while (urlRS.next()) {
				retUrl = urlRS.getString(1);
				retClass = urlRS.getString(2);
			}
			if(retUrl == null && retClass == null){
				return "NOURL";
			}
			
			urlStmt.close();
			urlRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retUrl + "::" + retClass;
	}

	public void updateInitialUrl(String url) {
		try {
			PreparedStatement urlStmt = conn
					.prepareStatement("update seed set status = 1 where url = ?");
			urlStmt.setString(1, url);
			urlStmt.executeUpdate();
			urlStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void parseUrl(String url) {

		int pageCount = 0;
		if (url.contains("/xw/zyxw/yw/")) {
			pageCount = 34;
		} else if (url.contains("/ky/kyjz/")) {
			pageCount = 50;
		} else if (url.contains("/xw/kjsm/")) {
			pageCount = 34;
		} else if (url.contains("/xw/yxdt/")) {
			pageCount = 34;
		} else if (url.contains("/xw/rcjy/")) {
			pageCount = 34;
		} else if (url.contains("/xw/zjsd/")) {
			pageCount = 34;
		} else if (url.contains("/hzjl/ydhz/hzdt/")) {
			pageCount = 25;
		} else if (url.contains("/hzjl/gjjl/hzdt/")) {
			pageCount = 25;
		} else if (url.contains("/hy/xshd/")) {
			pageCount = 25;
		} else if (url.contains("/hy/hyyg/")) {
			pageCount = 25;
		} else if (url.contains("/hy/cmsm/")) {
			pageCount = 34;
		} else if (url.contains("/kxcb/kpdt/")) {
			pageCount = 17;
		}

		try {
			for (int i = 0; i < pageCount; i++) {
				Document doc = null;
				if (i == 0) {
					doc = Jsoup.connect(url).get();
				} else {
					doc = Jsoup.connect(url + "index_" + i + ".shtml").get();
				}
				Elements aTags = doc.select("a.outh14z");
				try {

					for (int j = 0; j < aTags.size(); j++) {
						String detailUrl = aTags.get(j).absUrl("href");
						if (urlSet.add(BaseUtil.encryptMD5(detailUrl))) {
							extractInfor(detailUrl);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void extractInfor(String url) throws SQLException {

		String titleString = null;
		String sourceString = null;
		String publishString = null;
		String bodyString = null;
		System.out.println(url);

		try {
			Document doc = Jsoup.connect(url).get();

			Elements titleElements = doc.select("td.dt25l24");
			//System.out.println(titleElements.size());
			for (int i = 0; i < titleElements.size(); i++) {
				titleString = titleElements.get(i).text();
				// System.out.println(titleString);
			}

			Elements bodyElements = doc.select("div.TRS_Editor");
			if (bodyElements.size() == 1) {
				for (int i = 0; i < bodyElements.size(); i++) {
					bodyString = bodyElements.get(i).text();
					System.out.println(bodyString);
				}
			} else if (bodyElements.size() > 1) {
				for (int i = 0; i < bodyElements.size() - 1; i++) {
					bodyString = bodyElements.get(i).text();
					System.out.println(bodyString);
				}
			} else if (bodyElements.size() == 0) {
				Elements bodyElements2 = doc.select("div.cas_content");
				if (bodyElements2.size() == 0) {
					System.out.println("ERROR ERROR ERROR ERRORERROR ERRORERROR ERRORERROR ERROR");
				} else if (bodyElements2.size() >= 1) {
					for (int i = 0; i < bodyElements2.size(); i++) {
						bodyString += bodyElements2.get(i).text();
					}
					System.out.println(bodyString);
				}
			}

			Elements b12Elements = doc.select("td.b12");
			//System.out.println(b12Elements.size());
			for (int i = 0; i < b12Elements.size(); i++) {
				if (b12Elements.get(i).text().contains("文章来源")) {
					sourceString = b12Elements.get(i).text().substring(5);
					//System.out.println(sourceString);
				} else if (b12Elements.get(i).text().contains("发布时间")) {
					publishString = b12Elements.get(i).text().substring(5);
					//System.out.println(publishString);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		PreparedStatement urlStmt;
		try {
			urlStmt = conn
					.prepareStatement("insert into news(class,url,url_md5,title,source,publish_time,body,future) values (?,?,?,?,?,?,?,?)");
			urlStmt.setString(1, category);
			urlStmt.setString(2, url);
			urlStmt.setString(3, BaseUtil.encryptMD5(url));
			urlStmt.setString(4, titleString);
			urlStmt.setString(5, sourceString);
			urlStmt.setString(6, publishString);
			urlStmt.setString(7, bodyString);
			urlStmt.setString(8, null);
			urlStmt.executeUpdate();
			urlStmt.close();
			
			System.out.println("****    " + (++count) + "   ******");
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

}
