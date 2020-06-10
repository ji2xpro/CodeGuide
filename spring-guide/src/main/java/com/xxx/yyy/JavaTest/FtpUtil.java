package com.xxx.yyy.JavaTest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class FtpUtil {

	/**
	 * 向FTP服务器上传文件
	 * 
	 * @param remoteIP
	 *            FTP服务器remoteIP
	 * @param remotePort
	 *            FTP服务器端口
	 * @param remoteUser
	 *            FTP登陆账号
	 * @param remotePwd
	 *            FTP登陆密码
	 * @param remotePath
	 *            FTP服务器保存目录
	 * @param fileName
	 *            上传到FTP服务器上的文件名
	 * @param inputStream
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String remoteIP, int remotePort, String remoteUser, String remotePwd, String remotePath,
                                     String fileName, InputStream inputStream) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setBufferSize(1024);
		ftpClient.setControlEncoding("GBK");
		try {
			// 连接FTP服务器
			ftpClient.connect(remoteIP, remotePort);
			// 登录FTP服务器
			ftpClient.login(remoteUser, remotePwd);
			// 是否成功登录FTP服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				return flag;
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// ftpClient.makeDirectory(remotePath);
			ftpClient.changeWorkingDirectory(remotePath);
			ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 将本地文件上传到FTP服务器上
	 * 
	 * @param remoteIP
	 *            FTP服务器remoteIP
	 * @param remotePort
	 *            FTP服务器端口
	 * @param remoteUser
	 *            FTP登陆账号
	 * @param remotePwd
	 *            FTP登陆密码
	 * @param remotePath
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器后的文件名称 传空时服务器保留待上传文件名称
	 * @param orginfilename
	 *            待上传文件的名称（绝对地址）
	 * @return 成功返回true，否则返回false
	 */
	public static boolean upLoadFromProduction(String remoteIP, int remotePort, String remoteUser, String remotePwd, String remotePath,
                                               String fileName, String localFile) {
		boolean flag = false;
		try {
			if ("".equals(fileName) && fileName.length() <= 0) {
				fileName = new File(localFile).getName();
			}
			FileInputStream fileInputStream = new FileInputStream(new File(localFile));
			flag = uploadFile(remoteIP, remotePort, remoteUser, remotePwd, remotePath, fileName, fileInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag; 
	}

	/**
	 * 删除FTP服务器上的文件
	 * 
	 * @param remoteIP
	 *            FTP服务器remoteIP
	 * @param remotePort
	 *            FTP服务器端口
	 * @param remoteUser
	 *            FTP登陆账号
	 * @param remotePwd
	 *            FTP登陆密码
	 * @param remotePath
	 *            FTP服务器目录
	 * @param filename
	 *            删除FTP服务器上文件名称
	 * @return 成功返回true，否则返回false
	 */
	public static boolean deleteFile(String remoteIP, int remotePort, String remoteUser, String remotePwd, String remotePath,
                                     String fileName) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setBufferSize(1024);
		ftpClient.setControlEncoding("GBK");
		try {
			// 连接FTP服务器
			ftpClient.connect(remoteIP, remotePort);
			// 登录FTP服务器
			ftpClient.login(remoteUser, remotePwd);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(remotePath);
			ftpClient.dele(fileName);
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 下载FTP上的文件到本地localPath处
	 * 
	 * @param remoteIP
	 *            FTP服务器remoteIP
	 * @param remotePort
	 *            FTP服务器端口
	 * @param remoteUser
	 *            FTP登陆账号
	 * @param remotePwd
	 *            FTP登陆密码
	 * @param remotePath
	 *            FTP服务器目录
	 * @param fileName
	 *            下载FTP服务器上文件名称
	 * @param localPath
	 *            下载文件到本地路径（绝对地址）
	 * @return 成功返回true，否则返回false
	 */
	public static boolean downloadFile(String remoteIP, int remotePort, String remoteUser, String remotePwd, String remotePath,
                                       String fileName, String localPath) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setBufferSize(1024);
		ftpClient.setControlEncoding("GBK");
		try {
			// 连接FTP服务器
			ftpClient.connect(remoteIP, remotePort);
			// 登录FTP服务器
			ftpClient.login(remoteUser, remotePwd);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (fileName.equals(file.getName())) {
					File localFile = new File(localPath + "/" + file.getName());
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		String remoteIP = "10.199.103.226";
		int remotePort = 21;
		String remoteUser = "administrator";
		String remotePwd = "XLW987987xlw";
		String remotePath = "/mytestplan";
		String fileName = "测试.txt";
		String localFile = "/Users/jimmy/Desktop/测试.txt";
		String localPath = "/Users/jimmy";
		upLoadFromProduction(remoteIP, remotePort, remoteUser, remotePwd, remotePath, fileName, localFile);
		deleteFile(remoteIP, remotePort, remoteUser, remotePwd, remotePath, fileName);
		downloadFile(remoteIP, remotePort, remoteUser, remotePwd, remotePath, fileName, localPath);
	}
}