package com.troila.svn.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.apache.http.util.ByteArrayBuffer;

public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 创建文件，若文件夹不存在则自动创建文件夹，若文件存在则删除旧文件
     * 
     * @param path :待创建文件路径
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        return file;
    }

    /**
     * 将文件输入流写入文件
     */
    public static boolean writeFileFromInputStream(InputStream inStream, String path) {
        boolean result = true;
        try {
            File file = createNewFile(path);
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int num = 0;
            while ((num = inStream.read(data, 0, data.length)) != -1) {
                out.write(data, 0, num);
            }
            out.close();
            data = null;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 获取文件输入流
     */
    public static InputStream readFileToInputStream(String path) {
        InputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
        }
        return inputStream;
    }

    /**
     * 读取文件字节数组
     */
    public static byte[] readFileToBytes(String path) {
        InputStream inputStream = readFileToInputStream(path);
        if (inputStream != null) {
            byte[] data = new byte[1024];
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            int n = 0;
            try {
                while ((n = inputStream.read(data)) != -1) {
                    buffer.append(data, 0, n);
                }
                data = null;
                inputStream.close();
            } catch (IOException e) {
            }
            return buffer.toByteArray();
        }
        return null;
    }

    /**
     * 读取文件内容
     */
    public static String readFileToString(String path) {
        byte[] dataBytes = readFileToBytes(path);
        if (dataBytes != null) {
            return new String(dataBytes);
        }
        return null;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String tempString = null;
            while ((tempString = bufferedReader.readLine()) != null) {
                buffer.append(tempString).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 根据给出路径自动选择复制文件或整个文件夹
     * 
     * @param src :源文件或文件夹路径
     * @param dest :目标文件或文件夹路径
     */
    public static void copyFiles(String src, String dest) {
        File srcFile = new File(src);
        if (srcFile.exists()) {
            if (srcFile.isFile()) {
                writeFileFromInputStream(readFileToInputStream(src), dest);
            } else {
                File[] subFiles = srcFile.listFiles();
                if (subFiles.length == 0) {
                    File subDir = new File(dest);
                    subDir.mkdirs();
                } else {
                    for (File subFile : subFiles) {
                        String subDirPath = dest + System.getProperty("file.separator") + subFile.getName();
                        copyFiles(subFile.getAbsolutePath(), subDirPath);
                    }
                }
            }
        }
    }

    /**
     * 根据给出路径自动选择删除文件或整个文件夹
     * 
     * @param path :文件或文件夹路径
     */
    public static void deleteFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();// 删除文件
        } else {
            File[] subFiles = file.listFiles();
            for (File subfile : subFiles) {
                deleteFiles(subfile.getAbsolutePath());// 删除当前目录下的子目录
            }
            file.delete();// 删除当前目录
        }
    }

    /**
     * 转换文件大小
     */
    public static String FormetFileSize(long size) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String result = "";
        if (size < 1024) {
            result = df.format((double) size) + "B";
        } else if (size < 1048576) {
            result = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            result = df.format((double) size / 1048576) + "M";
        } else {
            result = df.format((double) size / 1073741824) + "G";
        }
        return result;
    }

    /**
     * 复制单个文件
     *
     * @param svnUrl svn服务器路径
     * @param destFileName 本地路径
     * @param overlay 如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(ByteArrayOutputStream outputStream, String destFileName, boolean overlay) throws IOException {
        OutputStream out = null;
        InputStream in = null;
        try {
            // 判断目标文件是否存在
            File destFile = new File(destFileName);
            if (destFile.exists()) {
                // 如果目标文件存在并允许覆盖
                if (overlay) {
                    // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                    new File(destFileName).delete();
                }
            } else {
                // 如果目标文件所在目录不存在，则创建目录
                if (!destFile.getParentFile().exists()) {
                    // 目标文件所在目录不存在
                    if (!destFile.getParentFile().mkdirs()) {
                        // 复制文件失败：创建目标文件所在目录失败
                        return false;
                    }
                }
            }
             out = new FileOutputStream(destFile);
            // 复制文件

            outputStream.writeTo(out);

            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}