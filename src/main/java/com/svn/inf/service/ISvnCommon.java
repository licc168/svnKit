package com.svn.inf.service;

import java.io.File;
import java.util.List;

/**
 * Svn功能组件
 * 
 * @author Allen
 * @date 2016年8月12日
 *
 */
public interface ISvnCommon {
	/**
	 * 组装file[]分离文件与文件夹
	 * 
	 * @param files
	 *            待重组的文件列表
	 * @return 文件容器组</br>index:0 文件夹</br> index:1 文件
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public List<List<File>> bindFile(File[] files);

	/**
	 * 排序</br> 父级 ->子级
	 * 
	 * @param files
	 *            待排序文件数组
	 * @return 排序后文件数组
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public File[] sortF_S(File[] files);

	/**
	 * 排序</br> 子级 ->父级
	 * 
	 * @param files
	 *            待排序文件数组
	 * @return 排序后文件数组
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public File[] sortS_F(File[] files);

	/**
	 * 检查文件路径信息并组装到文件容器
	 * 
	 * @param paths
	 *            待组装文件路径
	 * @return 文件数组
	 * @throws Exception
	 *             文件路径中有寻找不到的地址
	 * @author Allen
	 * @date 2016年8月11日
	 */
	public File[] checkFilePaths(String[] paths) throws Exception;
}
