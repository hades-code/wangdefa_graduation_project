package org.lhq.entity.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author hades
 */
@Data
public class Chunk {
	private Long id;
	/**
	 * 分片号
	 */
	private String chunkNumber;
	/**
	 * 分片大小
	 */
	private Long chunkSize;
	/**
	 * 当前分片大小
	 */
	private Long currentChunkSize;
	/**
	 * 总大小
	 */
	private Long totalSize;
	/**
	 * 文件表示
	 */
	private String identifier;
	/**
	 * 文件名
	 */
	private String filename;
	/**
	 * 相对路径
	 */
	private String relativePath;
	/**
	 * 分片数量
	 */
	private Integer totalChunks;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 文件目录
	 */
	private Long dirId;
	@Transient
	private MultipartFile file;
}
