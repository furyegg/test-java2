package com.lombardrisk.reform.ftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.impl.StandardFileSystemManager;

import com.lombardrisk.reform.ftp.model.File;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;

public class SFTPBrowseServlet extends HttpServlet {

	private static final long serialVersionUID = 3476336672141226567L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String counterpartyId = req.getParameter("counterpartyId");
		String path = buildPath(counterpartyId);

		StandardFileSystemManager manager = new StandardFileSystemManager();
		FileObject dir = FTPUtil.getFileObject(manager, FTPUtil.buildConnectionString(), path);
		List<File> files = buildFileList(dir);
		manager.close();

		SerializationContext context = new SerializationContext();
		Amf3Output out = new Amf3Output(context);
		out.setOutputStream(resp.getOutputStream());
		out.writeObject(files);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private String buildPath(String counterpartyId) {
		return counterpartyId;
	}

	private List<File> buildFileList(FileObject dir) throws FileSystemException {
		String dirName = dir.getName().getBaseName();

		List<File> files = new ArrayList<File>();
		for (FileObject file : dir.getChildren()) {
			String fileName = file.getName().getBaseName();
			StringBuilder url = new StringBuilder(dirName).append("/").append(fileName);
			files.add(new File(fileName, url.toString()));
		}
		return files;
	}

}
