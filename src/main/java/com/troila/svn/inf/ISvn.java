package com.troila.svn.inf;

import java.io.File;
import java.util.List;

import com.troila.svn.inf.service.ISvnDbLog;
import com.troila.svn.inf.service.ISvnService;
import com.troila.svn.model.SvnRepoPojo;

/**
 * svn操作大全
 * 
 * @author Allen
 * @date 2016年8月8日
 */
public interface ISvn extends ISvnService {

	/**
	 * 获取目标路径下版本库数据信息
	 * 
	 * @param openPath
	 *            需要查看的版本库路径
	 * @return 版本库列表 {@link SvnRepoPojo}
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public List<SvnRepoPojo> getRepoCatalog(String openPath);

	/**
	 * 检出到目录
	 * 
	 * @param checkUrl
	 *            检出目标URL
	 * @param savePath
	 *            检出到本地路径
	 * @return true|false
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public boolean checkOut(String checkUrl, String savePath);

	/**
	 * 添加到版本库
	 * @see 先添加文件夹再添加文件
	 * @param paths
	 *            提交文件路径
	 * @param message
	 *            提交信息
	 * @param uLocks
	 *            是否解锁
	 * @param isvnLog
	 *            数据持久化接口 {@link ISvnDbLog}
	 * @return trun|false
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public <T> boolean add(String[] paths, String message, boolean uLocks, ISvnDbLog<? extends T> isvnLog);

	/**
	 * 提交到版本库(所有写操作已在内部调用过COMMIT,自行调用则需要手动同步到DbLog)
	 * 
	 * @param files
	 *            提交的文件路径
	 * @param message
	 *            提交信息
	 * @param uLocks
	 *            是否解锁
	 * @return 返回提交后版本号-1为提交失败
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public Long commit(File[] files, String message, boolean uLocks);

	/**
	 * 删除到版本库
	 * 
	 * @see 先删除文件再删除文件夹
	 * @param paths
	 *            提交文件路径
	 * @param localDelete
	 *            <ul>
	 *            <li>如果是true则在本地也删除此文件,false则只删除版本库中的此文件</li>
	 *            <li>删除实体文件时要注意</li>
	 *            <li>删除文件夹时其目录下所有内容都要提交到<b>参数paths</b>中,否则无法删除实体文件</li>
	 *            </ul>
	 * @param message
	 *            提交内容解释
	 * @param uLock
	 *            是否解锁
	 * @param isvnLog
	 *            数据持久化接口 {@link ISvnDbLog}
	 * @return true|false
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public <T> boolean delete(String[] paths, boolean localDelete, String message, boolean uLock, ISvnDbLog<? extends T> isvnLog);

	/**
	 * 更新到版本库
	 * 
	 * @param path
	 *            要更新的文件目录
	 * @param message
	 *            提交内容解释
	 * @param uLock
	 *            是否解锁
	 * @param isvnLog
	 *            数据持久化接口 {@link ISvnDbLog}
	 * @return true|false
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public <T> boolean update(String path, String message, boolean uLock, ISvnDbLog<? super T> isvnLog);

	/**
	 * 比对目录下内容信息
	 * 
	 * @see 返回delete,update文件列表
	 * @param file
	 *            待比对的目标文件路径
	 * @return 返回有差异的文件路径否则为null
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public List<String> diffPath(File file);

	public boolean doLock();

	public boolean unLock();
}
