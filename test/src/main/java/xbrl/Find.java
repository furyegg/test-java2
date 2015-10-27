package xbrl;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Find {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("src/main/resources/duplicated.txt");
		Scanner scanner = new Scanner(fis);
		String line = "";
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			String[] split = line.split("\t");
			String tableCode = split[0];
			String dpId = split[1];

			Set<String> tcSet = map.get(dpId);
			if (tcSet == null) {
				tcSet = new HashSet<String>();
				map.put(dpId, tcSet);
			}
			tcSet.add(tableCode);
		}
		
		Set<String> set = new TreeSet<String>();
		for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
			if (entry.getValue().size() > 1) {
				set.addAll(entry.getValue());
				System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
			}
		}
		System.out.println(set);
	}

}
