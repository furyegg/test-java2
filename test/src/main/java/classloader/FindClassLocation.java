package classloader;

import java.net.URL;

public class FindClassLocation {
    public static void main(String[] args) {
		URL url = FindClassLocation.class.getClassLoader().getResource("org/apache");
	}
}