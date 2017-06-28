package com.svn.impl.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svn.conf.ErrorVal;
import com.svn.inf.service.ISvnCommon;
import com.svn.tools.ConsoleLog;

/**
 * {@link ISvnCommon}
 * 
 * @author Allen
 * @date 2016年8月12日
 *
 */
public class SvnCommonImpl extends ConsoleLog implements ISvnCommon {

	@Override
	public List<List<File>> bindFile(File[] files) {
		List<List<File>> fileList = new ArrayList<List<File>>(1);
		fileList.add(new ArrayList<File>());
		fileList.add(new ArrayList<File>());
		for (File f : files) {
			if (f.isDirectory())
				// 0是文件目录,先提交文件目录让再提交文件
				fileList.get(0).add(f);
			else
				fileList.get(1).add(f);
		}
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).size() == 0)
				fileList.remove(i);
		}
		return fileList;
	}

	@Override
	public File[] sortF_S(File[] files) {
		Arrays.sort(files);
		return files;
	}

	@Override
	public File[] sortS_F(File[] files) {
		files = sortF_S(files);
		File[] f = new File[files.length];
		for (int i = 0; i < files.length;) {
			f[i] = files[files.length - (++i)];
		}
		return f;
	}

	@Override
	public File[] checkFilePaths(String[] paths) throws Exception {
		if (paths == null | paths.length == 0)
			throw new Exception(ErrorVal.Path_no_having);
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			if (!(files[i] = new File(paths[i])).exists())
				throw new Exception(ErrorVal.Path_no_having);
		}
		return files;
	}

}
