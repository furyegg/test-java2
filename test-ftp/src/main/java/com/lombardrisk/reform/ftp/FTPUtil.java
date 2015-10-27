package com.lombardrisk.reform.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

public class FTPUtil {

	public static FileObject getFileObject(StandardFileSystemManager manager, String connStr, String dir) throws FileSystemException {
		manager.init();

		FileSystemOptions opts = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);

		StringBuilder fileStr = new StringBuilder(connStr).append(dir == null ? "" : dir);
		return manager.resolveFile(fileStr.toString(), opts);
	}

	public static String buildConnectionString() throws IOException {
		Properties p = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("ftp.properties");
		p.load(in);

		String user = p.getProperty("sftp.user");
		String pwd = p.getProperty("sftp.password");
		String host = p.getProperty("sftp.host");
		String port = p.getProperty("sftp.port");
		return buildConnectionString(user, pwd, host, port);
	}

	public static String buildConnectionString(String user, String pwd, String host, String port) {
		// sftp://user:password@host:port/
		StringBuilder conn = new StringBuilder("sftp://");
		conn.append(user);
		conn.append(":");
		conn.append(pwd);
		conn.append("@");
		conn.append(host);
		conn.append(":");
		conn.append(port);
		conn.append("/");
		return conn.toString();
	}

}
