package com.svn.model;

/**
 * Svn链接状态信息
 * 
 * @author Allen 
 * @date 2016年8月8日
 */
public class SvnLinkPojo extends SvnAccountPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String repoPath;// 库链接路径

	public SvnLinkPojo(String repoPath, String svnAccount, String svnPassword) {
		super(svnAccount, svnPassword);
		this.repoPath = repoPath;
	}

	public SvnLinkPojo(String svnAccount, String svnPassword) {
		super(svnAccount, svnPassword);
	}

	public String getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(String repoPath) {
		this.repoPath = repoPath;
	}

}
