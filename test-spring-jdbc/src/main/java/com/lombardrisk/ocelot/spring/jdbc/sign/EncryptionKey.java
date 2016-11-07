package com.lombardrisk.ocelot.spring.jdbc.sign;

public final class EncryptionKey {
	private EncryptionKey() {
	}

	public static final int BUFFER = 2048;

	/**
	 * the default password for encrypt and decrypt configuration package
	 */
	public static final char[] DEFAULT_PASSWORD = {'L','O','m','b','a','r','D','r','I','s','K'};

	/**
	 * the default salt for encrypt and decrypt configuration package
	 */
	public static final byte[] DEFAULT_SALT = { 'o', 'C', 'e', 'L', 'o', 'T'};


	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAIPE6LrYLwbIGiiFSoJN2EndQo7UhapnAxT1h"
												+ "1mDafU+sdkXjYlpCLZfk6LtdkbXIGlSuvldYXmxI4m+cIeDmACjivF6+DW8GVuXIIyargi4EuQEB"
												+ "hvZstlUJpTzUSskS9gXpVvCVaAsZbdWYX6H8ojMbmA8bUkfj2rH0eu5jMwIDAQAB";


	public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIAg8ToutgvBsgaKIVKgk3YSd1Cj"
														+ "tSFqmcDFPWHWYNp9T6x2ReNiWkItl+Tou12RtcgaVK6+V1hebEjib5wh4OYAKOK8Xr4NbwZW5cgj"
														+ "JquCLgS5AQGG9my2VQmlPNRKyRL2BelW8JVoCxlt1ZhfofyiMxuYDxtSR+PasfR67mMzAgMBAAEC"
														+ "gYAa/NVSl/woRcglsHqMWN42hHU1OwYv37A69lCThwAnhTV+8fqOhmzUe53+zTaJ4uMTHtivZdAo"
														+ "ANt2DpvLmycvfTrdxAyx6EDCdiE5tN09bb6Y0fYh7iBOMQ8qbtrZ6XQC1ny5Z79kYPumjf1bbI3l"
														+ "+SEOZd1nCZpSnxDEBRU0AQJBANqY593k7kynaRkpRyqc1J0RJaqCZYVIhpc5rFXGkCWFrVrR3Rs8"
														+ "UhjKhQ4RZpiev8TgwO+Pc/YxyQ5VmKHClLMCQQCWDUp8XMUzoyQMolIfps1FLV2/gsP8S/k8KSvx"
														+ "OlA7Qm1ZVp5Joq35uHcdvx+3QvykOFNCk7179YjukdxL1TeBAkEAz35UmUu05w4BtOB5rPkw1+Zu"
														+ "2mrr8pflKaaU7taL/RHme065kiCvzNhvc+sQd844OakdekewywB22CRnvSP2AQJAC68HQL28gTPP"
														+ "x8gP2vUTALLynMni1s9Xnc6a5FKlEqgueYlcE+G96Dbr134dSxjFMUhNe16jn3ZT7jefTqxQgQJB"
														+ "AI7Kd157qPISE9AM/J+DaSSjCXC/waqMK6HgRoZDYPye4xmYQdGsTI3qLJQmQVqszGGH/PpL6DuB"
														+ "acVKILNJe7s=";

}