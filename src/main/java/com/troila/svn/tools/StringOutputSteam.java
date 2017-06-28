package com.troila.svn.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Output输出到字符串
 * 
 * @author Allen
 * @date 2016年8月11日
 *
 */
public final class StringOutputSteam extends OutputStream {

	public List<String> s;

	public StringOutputSteam(List<String> s) {
		this.s = s;
	}

	@Override
	public void write(int b) throws IOException {
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		String temp = new String(b);
		String[] temps = temp.split("\r\n");
		//System.out.println(temp);
		boolean flag = false;
		for (int i = 0; i < temps.length; i++) {
			// 根据svn命令返回Index: xxxx(path)获取所有包含delete update的path获取
			if (temps[i].split(" ")[0].equalsIgnoreCase("Index:")) {
				String p = temps[i].substring(7, temps[i].length());
				if (s.indexOf(p) == -1) {
					s.add(p);
					flag = true;
					continue;
				}
			}
			// 以Index：为开始点来判定svn diff返回的Log信息
			// LogFormat:
			// Index: path
			// =============================
			// xxxxxxx
			// Index: path
			// =============================
			// xxxxxxx
			if (flag) {
				String state = "";
				for (int j = i; j < temps.length; j++) {
					if (temps[j].split(" ")[0].equalsIgnoreCase("Index:"))
						break;
					if (temps[j].split(" ")[0].equalsIgnoreCase("---"))
						state = "冲突";
					else if (state.equals(""))
						state = "删除";
				} 
				s.add(state);
				flag = false;
			}
		}
	}
}
