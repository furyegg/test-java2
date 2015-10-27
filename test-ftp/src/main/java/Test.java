import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.vfs.FileSystemException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Test {

	protected String host = "127.0.0.1";// sftp服务器ip
	protected String username = "kyle";// 用户名
	protected String password = "123";// 密码
	protected String privateKey = "C:/Users/Kyle Li/Documents/reform.ppk";// 密钥文件路径
	protected String passphrase = "reform";// 密钥口令
	protected int port = 21;// 默认的sftp端口号是22
	
	public static void main(String[] args) throws FileSystemException {
		Test t = new Test();
		ChannelSftp sftp = t.connectSFTP();
		t.disconnected(sftp);
	}

	/**
	 * 获取连接
	 * 
	 * @return channel
	 */
	public ChannelSftp connectSFTP() {
		JSch jsch = new JSch();
		Channel channel = null;
		try {
			if (privateKey != null && !"".equals(privateKey)) {
				// 使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
				if (passphrase != null && "".equals(passphrase)) {
					jsch.addIdentity(privateKey, passphrase);
				} else {
					jsch.addIdentity(privateKey);
				}
			}
			Session session = jsch.getSession(username, host, port);
			if (password != null && !"".equals(password)) {
				session.setPassword(password);
			}
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");// do not verify host key
			session.setConfig(sshConfig);
			// session.setTimeout(timeout);
			session.setServerAliveInterval(92000);
			session.connect();
			// 参数sftp指明要打开的连接是sftp连接
			channel = session.openChannel("sftp");
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return (ChannelSftp) channel;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.get(downloadFile, saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnected(ChannelSftp sftp) {
		if (sftp != null) {
			try {
				sftp.getSession().disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
			sftp.disconnect();
		}
	}
}
