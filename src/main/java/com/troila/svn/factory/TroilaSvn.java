package com.troila.svn.factory;

import com.troila.svn.conf.ErrorVar;
import com.troila.svn.conf.SvnConfig;
import com.troila.svn.impl.SvnBaseImpl;
import com.troila.svn.inf.ISvn;
import com.troila.svn.model.SvnLinkPojo;

/**
 * TroilaSvn主体
 * 
 * @author Allen
 * @date 2016年8月8日
 */
public final class TroilaSvn {

	SvnLinkPojo svnLink;

	public SvnLinkPojo getSvnLink() {
		return svnLink;
	}

	/**
	 * 私有构造
	 */
	public TroilaSvn() {
	}

	public TroilaSvn(String svnAccount, String svnPassword, String repoPath) {
		this.svnLink = new SvnLinkPojo(repoPath, svnAccount, svnPassword);
	}

	/**
	 * 获取SVN操作
	 * 
	 * @param val
	 *            default 不设置日志状态 log 开启console日志状态
	 * @throws 没有操作匹配
	 * @return {@link ISvn}
	 */
	public ISvn execute(SvnConfig val) throws Exception {
		ISvn is = null;
		if (val == null)
			throw new Exception(ErrorVar.SvnConfig_is_null);
		switch (val.getVal()) {
		case "normal":
			is = new SvnBaseImpl(svnLink.getSvnAccount(), svnLink.getSvnPassword(), false, svnLink.getRepoPath());
			break;
		case "log":
			is = new SvnBaseImpl(svnLink.getSvnAccount(), svnLink.getSvnPassword(), true, svnLink.getRepoPath());
			break;
		default:
			throw new Exception(ErrorVar.SvnConfig_is_null);
		}
		return is;
	}
}
