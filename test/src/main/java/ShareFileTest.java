import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ShareFileTest {

	public static void main(String[] args) throws IOException {
		String fileName = "\\\\172.20.20.141\\DGB1622_INT_v4_FED150\\ecr.xbrl";
		File file = new File(fileName);
		System.out.println(file.getCanonicalPath());
		
		if (file.exists()) {
			file.createNewFile();
		}
		
		PrintWriter out = new PrintWriter(file);
		out.write("123123123213");
		
		out.close();
		
		// System.out.println(list);
	}

}