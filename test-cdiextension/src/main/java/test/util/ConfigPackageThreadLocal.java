package test.util;

public class ConfigPackageThreadLocal {

	private ThreadLocal<String> configPackageTL = new ThreadLocal<>();

	private static ConfigPackageThreadLocal instance = new ConfigPackageThreadLocal();

	private ConfigPackageThreadLocal() {
		System.out.println("ConfigPackageThreadLocal constructed");
	}

	public static ConfigPackageThreadLocal getInstance() {
		return instance;
	}

	public void set(String configPackage) {
		configPackageTL.set(configPackage);
	}

	public String get() {
		return configPackageTL.get();
	}

}