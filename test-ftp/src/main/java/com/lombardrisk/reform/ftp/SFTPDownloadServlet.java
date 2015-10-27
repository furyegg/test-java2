package com.lombardrisk.reform.ftp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.impl.StandardFileSystemManager;

public class SFTPDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1992252786503375405L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getParameter("url");
		StringBuilder fileConnStr = new StringBuilder(FTPUtil.buildConnectionString()).append(url);

		StandardFileSystemManager manager = new StandardFileSystemManager();
		FileObject file = FTPUtil.getFileObject(manager, fileConnStr.toString(), null);

		int index = url.lastIndexOf("/");
		String fileName = url.substring(index < 0 ? 0 : index + 1, url.length());
		resp.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		IOUtils.copy(file.getContent().getInputStream(), resp.getOutputStream());

		manager.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
