package org.lhq.gp.product.common;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author hades
 */
@Entity
@Data
public class Chunk {
	@Id
	@GeneratedValue
	private Long id;
	private String chunkNumber;
	private Long chunkSize;
	private Long currentChunkSize;
	private Long totalSize;
	private String identifier;
	private String filename;
	private String relativePath;
	private Integer totalChunks;
	private String type;
	private String dirId;
	@Transient
	private MultipartFile file;
}
