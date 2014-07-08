package never.entry;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {

	private Set<String> seedSet = new HashSet<String>();
	private Queue<String> queue = new LinkedList<String>();  

	public static void main(String[] args) {

		
	}

	public boolean initiaCrawler() {

		String sql = "select url_md5";
		
		return true;
	}

	public boolean addSeed(String seed) {

		return seedSet.add(seed);
	}

}
