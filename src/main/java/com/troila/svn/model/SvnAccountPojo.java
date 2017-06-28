package com.troila.svn.model;

/**
 * Svn账号信息
 * 
 * @author Allen 
 * @date 2016年8月8日
 */
public class SvnAccountPojo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String svnAccount;// svn账号
	private String svnPassword;// svn密码

	protected SvnAccountPojo() {
		// TODO Auto-generated constructor stub
	}

	public SvnAccountPojo(String svnAccount, String svnPassword) {
		super();
		this.svnAccount = svnAccount;
		this.svnPassword = svnPassword;
	}

	public String getSvnAccount() {
		return svnAccount;
	}

	public void setSvnAccount(String svnAccount) {
		this.svnAccount = svnAccount;
	}

	public String getSvnPassword() {
		return svnPassword;
	}

	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}

}
