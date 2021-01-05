package org.lhq.service;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author hades
 */
public interface FileService {


	Configuration getHDFSConfig();

	FileSystem getFileSystem() throws Exception;

	/**
	 * ls 列出子目录和文件
	 *
	 * @return
	 */
	List<Integer> ls(String dir) throws Exception;

	/**
	 * 列出所有的文件
	 *
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	FileStatus[] tempFile(String dir) throws Exception;


	/**
	 * 校验文件MD5
	 *
	 * @param dir 文件路径
	 * @return
	 * @throws Exception
	 */

	String chechMD5(String dir) throws Exception;

	/**
	 * 合并文件
	 *
	 * @param fileStatuses
	 * @param fileName
	 * @throws Exception
	 */
	void mergeFile(FileStatus[] fileStatuses, String fileName) throws Exception;

	/**
	 * 创建目录
	 *
	 * @return
	 */
	void mkdir(String dir) throws Exception;

	Boolean exitFile(String dir) throws Exception;

	/**
	 * 删除文件或目录
	 * 返回父亲目录
	 */
	String rm(String path) throws Exception;

	/**
	 * 上传
	 */
	void upload(InputStream is, String dstHDFSFile) throws Exception;

	/**
	 * 下载
	 */
	void download(String file, OutputStream os) throws Exception;

	/**
	 * 移动到
	 */
	void mv();

	/**
	 * 重命名
	 */
	String[] rename(String path, String dirName) throws Exception;

	/**
	 * 获取文件命
	 *
	 * @param path
	 * @return
	 */
	String getFileName(String path);

	//查询出当前fileIndexId可转移的目录集合，当前用户下该目录以及该目录的子目录不可选
	List getOptionTranPath(String path);


	List getStaticNums();

	void multipleDownload(List<Directory> directories, List<UserFile> userFiles, ZipOutputStream zipOutputStream, String path) throws IOException, URISyntaxException, InterruptedException;
}
