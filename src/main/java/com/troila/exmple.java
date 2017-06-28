package com.troila;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

import com.troila.svn.conf.SvnConfig;
import com.troila.svn.factory.TroilaSvn;
import com.troila.svn.inf.ISvn;
import com.troila.svn.inf.service.ISvnDbLog;
import com.troila.svn.model.SvnRepoPojo;

/**
 * 测试类
 * 
 * @author Allen
 * @date 2016年8月8日
 */
public class exmple {
	String account = "zhangxiaolei";
	String password = "123456";
	String path = "http://172.26.80.20/svn/pexip";
	String targetHead = "e:/测试";
	ISvn svn;

	/**
	 * 样例
	 * 
	 * @throws Exception
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testCore() throws Exception {
		// 初始化实例
		TroilaSvn ts = new TroilaSvn(account, password, path);
		// 获得操作对象
		this.svn = ts.execute(SvnConfig.log);
		// 得到版本库信息
		svn.createSVNRepository();
		// 得到基础操作对象
		svn.createSVNClientManager();
		/** 测试--Start-- **/

		testGetRepo();
		testCheckOut();
		testAdd();
		testDel();

		testUpdate();
		testDiff();
		/** 测试 --End-- **/
		// 关闭库容器
		svn.closeRepo();

	}

	/**
	 * 获得版本路径文件信息
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testGetRepo() {
		print(svn.getRepoCatalog(""));
	}

	/**
	 * 检出到本地路径
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testCheckOut() {
		svn.checkOut("http://172.26.80.20/svn/pexip/code/trunk/pexipmeeting/WebContent/page/skins/standard5/js/views/meeting/meetingDetail", targetHead);
	}

	/**
	 * 添加文件到svn
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testAdd() {
		String[] strs = new String[] { targetHead + "/4/a/", targetHead + "/4/", targetHead + "/4/a/b/", targetHead + "/4/a/b/b.txt" };
		svn.add(strs, "haha", false, new ISvnDbLog<String>() {
			@Override
			public boolean addLog(String name, SvnConfig dbType, long versionId, File[] files) {
				System.out.println("Add 到 DB 了");
				return true;
			}

			@Override
			public List<String> getLog(String name, Date startTime, Date endTime) {
				System.out.println("get 到 log 了");
				return null;
			}
		});
	}

	/**
	 * 删除文件到svn
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testDel() {
		String[] strs = new String[] { targetHead + "/4/a/", targetHead + "/4/", targetHead + "/4/a/b/", targetHead + "/4/a/b/b.txt" };
		svn.delete(strs, true, "haha", false, new ISvnDbLog<String>() {
			@Override
			public boolean addLog(String name, SvnConfig dbType, long versionId, File[] files) {
				System.out.println("del 到 DB 了");
				return true;
			}

			@Override
			public List<String> getLog(String name, Date startTime, Date endTime) {
				System.out.println("get 到 log 了");
				return null;
			}
		});
	}

	/**
	 * 更新文件到svn
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testUpdate() {
		String strs = targetHead + "/4/a/b/";
		svn.update(strs, "哈哈", false, new ISvnDbLog<String>() {
			@Override
			public boolean addLog(String name, SvnConfig dbType, long versionId, File[] files) {
				System.out.println("update 到 DB 了");
				return true;
			}

			@Override
			public List<String> getLog(String name, Date startTime, Date endTime) {
				System.out.println("get 到 log 了");
				return null;
			}
		});

	}

	/**
	 * 测试库比对
	 * 
	 * @author Allen
	 * @date 2016年8月12日
	 */
	private void testDiff() {
		String[] strs = new String[] { targetHead + "/4/a/b/" };
		List<String> s = svn.diffPath(new File(strs[0]));
		for (String t : s)
			System.out.println(t);
	}

	/**
	 * 打印当前版本库路径目录
	 */
	private void print(List<SvnRepoPojo> paramList) {
		System.out.print("commitMessage ");
		System.out.print("\t\t  date \t  ");
		System.out.print("\t   kind \t  ");
		System.out.print("\t name \t  ");
		System.out.print("\t repositoryRoot \t  ");
		System.out.print("\t revision \t  ");
		System.out.print("\t size \t  ");
		System.out.print("\t url \t  ");
		System.out.print("\t author \t  ");
		System.out.println("\t state \t  ");
		Collections.sort(paramList, new Comparator<SvnRepoPojo>() {
			@Override
			public int compare(SvnRepoPojo o1, SvnRepoPojo o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for (SvnRepoPojo pojo : paramList) {
			System.out.print("\t" + pojo.getCommitMessage() + "\t");
			System.out.print("\t" + pojo.getDate().getTime() + "\t");
			System.out.print("\t" + pojo.getKind() + "\t");
			System.out.print("\t" + pojo.getName() + "\t");
			System.out.print("\t" + pojo.getRepositoryRoot() + "\t");
			System.out.print("\t" + pojo.getRevision() + "\t");
			System.out.print("\t" + pojo.getSize() + "\t");
			System.out.print("\t" + pojo.getUrl() + "\t");
			System.out.print("\t" + pojo.getAuthor() + "\t");
			System.out.print("\t" + pojo.getState() + "\t");
			System.out.print("\r\n");
		}
	}

	public static void main(String[] args) throws Exception {
		new exmple().testCore();
	}
}
