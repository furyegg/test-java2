package com.lombardrisk.reform.ftp.model;

public class Counterparty {

	private long cpid;
	private String cpname;
	private String cpemail;

	public long getCpid() {
		return cpid;
	}

	public void setCpid(long cpid) {
		this.cpid = cpid;
	}

	public String getCpname() {
		return cpname;
	}

	public void setCpname(String cpname) {
		this.cpname = cpname;
	}

	public String getCpemail() {
		return cpemail;
	}

	public void setCpemail(String cpemail) {
		this.cpemail = cpemail;
	}

}
