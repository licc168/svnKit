package com.troila.svn.impl.service;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.troila.svn.conf.ErrorVar;
import com.troila.svn.inf.service.ISvnService;

/**
 * {@link ISvnService}
 * 
 * @author Allen
 * @date 2016年8月8日
 */
public class SvnServiceImpl extends SvnCommonImpl implements ISvnService {

	protected String svnAccount; // svn账号
	protected String svnPassword;// svn密码
	protected String svnRepoPath;// svn版本库根目录
	protected boolean logStatus = false;// 日志状态
	protected SVNRepository repository = null;// 版本库服务
	protected ISVNAuthenticationManager authManager;// 身份验证器
	protected SVNClientManager clientManager;// svn客户操作服务

	/**
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param logStatus
	 *            是否开启日志状态(默认false)
	 * @param repoPath
	 *            svn库根目录
	 * 
	 */
	public SvnServiceImpl(String account, String password, boolean logStatus, String repoPath) {
		this.svnAccount = account;
		this.svnPassword = password;
		this.svnRepoPath = repoPath;
		super.log = logStatus;
	}

	@Override
	public SVNRepository createSVNRepository() {

		try {
			if (repository != null)
				throw new Exception(ErrorVar.SVNRepository_is_having);
			// 创建库连接
			SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.svnRepoPath));
			super.log("创建版本库连接");
			// 身份验证
			this.authManager = SVNWCUtil.createDefaultAuthenticationManager(this.svnAccount, this.svnPassword.toCharArray());
			super.log("创建身份验证");
			// 创建身份验证管理器
			repository.setAuthenticationManager(authManager);

			this.repository = repository;

			super.log("设置版本库身份验证");
		} catch (SVNException e) {
			super.log(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  repository;
	}

	@Override
	public void closeRepo() {
		if (repository == null)
			try {
				throw new NullPointerException(ErrorVar.SVNRepository_is_null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		else {
			repository.closeSession();
			repository = null;
			super.log("关闭版本库");
		}
	}

	@Override
	public SVNClientManager createSVNClientManager() {
		if (authManager == null)
			try {
				throw new NullPointerException(ErrorVar.ISVNAuthenticationManager_is_null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true), authManager);
		super.log("创建svn客户操作服务");
return clientManager;

	}

}
