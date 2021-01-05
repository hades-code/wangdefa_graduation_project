package org.lhq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.lhq.dao.DirectoryDao;
import org.lhq.dao.UserFileDao;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author hades
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
	@Resource
	private DirectoryDao directoryDao;
	@Resource
	private UserFileDao userFileDao;

	@Value("${hdfs.path}")
	private String path;
	@Value("${hdfs.user}")
	private String user;

	private Configuration config;
	private FileSystem fss;

	@Override
	public Configuration getHDFSConfig() {
		UserGroupInformation remoteUser = UserGroupInformation.createRemoteUser(user);
		Configuration configuration = new Configuration();
		/*remoteUser.doAs((PrivilegedExceptionAction)()->{
			config = new Configuration();
			config.set("","");
			config.set("","");
			configuration.set("","");
			fss = FileSystem.get(config);
		});*/
		return configuration;
	}

	@Override
	public FileSystem getFileSystem() throws URISyntaxException, IOException, InterruptedException {
		URI uri = new URI(path);
		FileSystem fileSystem = FileSystem.get(uri, getHDFSConfig(), user);
		return fileSystem;
	}

	@Override
	public List<Integer> ls(String dir) throws InterruptedException, IOException, URISyntaxException {
		log.info("*********HDFS列出目录和文件***********");
		FileSystem fileSystem = getFileSystem();
		Path path = new Path(dir);
		if (!fileSystem.exists(path)) {
			log.error("目录{}不存在", dir);
		}
		List<Integer> list = new ArrayList<>();
		FileStatus[] filesStatus = fileSystem.listStatus(path);
		for (FileStatus f : filesStatus) {
			String num = f.getPath().toUri().getPath().split("/")[3].split("_")[1];
			Integer chunkNum = Integer.parseInt(num);
			list.add(chunkNum);
		}
		//不需要再操作FileSystem了，关闭
		fileSystem.close();

		return list;
	}

	/**
	 * 列出所有的文件
	 *
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	@Override
	public FileStatus[] tempFile(String dir) throws Exception {
		log.info("-------------->HDFS tempFile start");
		FileSystem fs = getFileSystem();
		Path path = new Path(dir);
		//如果不存在，返回
		if (!fs.exists(path)) {
			log.error("dir:" + dir + " not exists!");
			throw new RuntimeException("dir:" + dir + " not exists!");
		}
		FileStatus[] filesStatus = fs.listStatus(path);
		return filesStatus;
	}

	/**
	 * 校验文件MD5
	 *
	 * @param dir 文件路径
	 * @return
	 * @throws Exception
	 */
	@Override
	public String chechMD5(String dir) throws Exception {
		log.info("-------------->HDFS chechMD5 start");
		FileSystem fs = getFileSystem();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		FSDataInputStream in = null;
		Path dstPath = new Path(dir);
		in = fs.open(dstPath);
		byte[] buffer = new byte[2097152];
		int c;
		while ((c = in.read(buffer)) != -1) {
			md5.update(buffer, 0, c);
		}
		BigInteger bi = new BigInteger(1, md5.digest());
		String value = bi.toString(16);
		in.close();
		log.info("-------------->HDFS chechMD5 end");
		return value;
	}

	/**
	 * 合并文件
	 *
	 * @param fileStatuses
	 * @param fileName
	 * @throws Exception
	 */
	@Override
	public void mergeFile(FileStatus[] fileStatuses, String fileName) throws Exception {
		log.info("-------------->HDFS mergeFile start");

		FSDataOutputStream out = null;
		FSDataInputStream in = null;
		FileSystem fs = getFileSystem();
		FileSystem local = getFileSystem();
		Path dstPath = new Path(fileName);
		// 打开输出流
		out = fs.create(dstPath);
		for (FileStatus fileStatus : fileStatuses) {
			Path filePath = fileStatus.getPath();
			in = local.open(filePath);
			IOUtils.copyBytes(in, out, 2097152, false);
			// 复制数据
			in.close();
		}
		if (out != null) {
			// 关闭输出流
			out.close();
		}
		log.info("-------------->HDFS mergeFile end");
	}

	/**
	 * 创建目录
	 *
	 * @param dir
	 * @return
	 */
	@Override
	public void mkdir(String dir) throws Exception {
		log.info("-------------->HDFS mkdir start");
		FileSystem fs = getFileSystem();
		// 目录不存在则创建
		if (!fs.exists(new Path(dir))) {
			fs.mkdirs(new Path(dir));
		}
		//不需要再操作FileSystem了，关闭client
		fs.close();
		log.info("-------------->HDFS mkdir " + dir + " successful");
	}

	@Override
	public Boolean exitFile(String dir) throws Exception {
		log.info("-------------->HDFS exitFile start");
		FileSystem fs = getFileSystem();
		if (fs.exists(new Path(dir))) {
			fs.close();
			log.info("-------------->HDFS exitFile end");
			return true;
		} else {
			fs.close();
			log.info("-------------->HDFS exitFile end");
			return false;
		}
	}

	/**
	 * 删除文件或目录
	 * 返回父亲目录
	 *
	 * @param path
	 */
	@Override
	public String rm(String path) throws Exception {
		FileSystem fs = getFileSystem();
		Path filePath = new Path(path);
		fs.delete(filePath, true);
		//不需要再操作FileSystem了，关闭client
		fs.close();
		return null;
	}

	/**
	 * 上传
	 *
	 * @param is
	 * @param dstHDFSFile
	 */
	@Override
	public void upload(InputStream is, String dstHDFSFile) throws Exception {
		log.info("-------------->HDFS upload start");
		FileSystem fs = getFileSystem();
		Path dstPath = new Path(dstHDFSFile);
		FSDataOutputStream os = fs.create(dstPath);
		IOUtils.copyBytes(is, os, 1024, true);
	}

	/**
	 * 下载
	 *
	 * @param file
	 * @param os
	 */
	@Override
	public void download(String file, OutputStream os) throws Exception {
		log.info("文件下载开始");
		FileSystem fs = getFileSystem();
		Path srcPath = new Path(file);
		FSDataInputStream is = fs.open(srcPath);
		IOUtils.copyBytes(is, os, 1024, true);
	}

	/**
	 * 移动到
	 */
	@Override
	public void mv() {

	}

	/**
	 * 重命名
	 *
	 * @param path
	 * @param dirName
	 */
	@Override
	public String[] rename(String path, String dirName) throws Exception {
		return new String[0];
	}

	/**
	 * 获取文件命
	 *
	 * @param path
	 * @return
	 */
	@Override
	public String getFileName(String path) {
		return null;
	}

	@Override
	public List getOptionTranPath(String path) {
		return null;
	}

	@Override
	public List getStaticNums() {
		return null;
	}

	@Override
	public void multipleDownload(List<Directory> directories, List<UserFile> userFiles, ZipOutputStream zipOutputStream, String path) throws IOException, URISyntaxException, InterruptedException {
		for (Directory directory : directories) {
			String filePath = path + directory.getDirectoryName() + "/";
			ZipEntry zipEntry = new ZipEntry(filePath);
			zipOutputStream.putNextEntry(zipEntry);
			List<Directory> subDir = this.directoryDao.selectList(new QueryWrapper<Directory>().lambda().eq(Directory::getParentId, directory.getId()));
			List<UserFile> subUserFile = this.userFileDao.selectList(new QueryWrapper<UserFile>().lambda().eq(UserFile::getDirectoryId, directory.getId()));
			if (!subDir.isEmpty() || !subUserFile.isEmpty()) {
				multipleDownload(subDir, subUserFile, zipOutputStream, path);
			}
		}
		FileSystem fileSystem = this.getFileSystem();
		for (UserFile userFile : userFiles) {
			InputStream inputStream = fileSystem.open(new Path(userFile.getFilePath()));
			String fileName;
			if (userFile.getFileType() != null) {
				fileName = path + userFile.getFileName() + "." + userFile.getFileType();
			} else {
				fileName = path + userFile.getFileName();
			}
			byte[] buffer = new byte[1024];
			int len = 0;
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOutputStream.putNextEntry(zipEntry);
			while ((len = inputStream.read(buffer)) != -1) {
				zipOutputStream.write(buffer, 0, len);
			}
			inputStream.close();
			zipOutputStream.closeEntry();
		}
	}

}
