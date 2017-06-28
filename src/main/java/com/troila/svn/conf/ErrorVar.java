package com.troila.svn.conf;

/**
 * 错误信息库
 * @author Allen 
 * @date 2016年8月8日
 *
 */
public final class ErrorVar {

	public final static String SVNRepository_is_null = "版本库未被创建";
	public final static String SVNClientManager = "";
	public final static String SVNRepository_is_having = "版本库已存在 ,可以调用closeRepo进行[库服务清理]";
	public final static String ISVNAuthenticationManager_is_null = "身份验证器还未被创建";
	public final static String Path_no_having = "目标地址路径不存在";
	public final static String Url_no_having = "目标库路径 不存在";
	public final static String File_not_exist_repo = "文件所在目录没有被svn版本控制";
	public final static String SvnConfig_is_null = "错误的配置";
	public final static String Commit_error = "提交错误[==请确认文件是否已有其他版本操作,请检查文件是否不存在==]";
	public final static String AddDbLog_error = "添加到数据日志错误";
	public final static String File_Repo_no_having = "文件不存在版本库中";
	public final static String Repo_Status_error = "版本状态错误";
	public final static String Update_no_change = "没有变化的更新";
}
