package com.applechip.core.entity;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.applechip.core.abstact.GenericDtUpdated;
import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.FileUtil;

@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "multipartFile" })
@NoArgsConstructor
@Data
@Entity
@Table(name = "zt_upload_file")
public class UploadFile extends GenericDtUpdated<String> {

	private static final long serialVersionUID = -7837313689981801628L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "save_file_name")
	private String saveFileName;

	@Column(name = "upload_dir")
	private String uploadDir;

	@Column(name = "file_size")
	private Long fileSize;

	@Transient
	private transient MultipartFile multipartFile;

	public UploadFile(MultipartFile multipartFile, String uploadDir) {
		super();
		this.multipartFile = multipartFile;
		this.uploadDir = uploadDir;
	}

	public UploadFile(MultipartFile multipartFile, String uploadDir, String saveFileName) {
		super();
		this.multipartFile = multipartFile;
		this.uploadDir = uploadDir;
		this.saveFileName = saveFileName;
	}

	public File upload(String basePath) {
		if (!existsFileToUpload())
			return null;
		String uploadPath = getUploadPath(basePath);
		if (StringUtils.isBlank(saveFileName)) {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("set filename or save before upload file...");
			}
			this.saveFileName = id;
		}
		this.fileName = multipartFile.getOriginalFilename();
		this.fileSize = multipartFile.getSize();
		return FileUtil.upload(multipartFile, uploadPath, saveFileName);
	}

	public void download(String basePath, HttpServletRequest request, HttpServletResponse response) {
		File file = new File(getFilePath(basePath));
		if (!file.exists())
			throw new RuntimeException(String.format("file not found...(%s)", getFilePath(basePath)));
		FileUtil.download(file, fileName, request, response);
	}

	public void removeFile(String basePath) {
		File file = new File(getUploadPath(basePath));
		if (!file.exists())
			return;
		file.delete();
	}

	@Transient
	public boolean existsFileToUpload() {
		return multipartFile != null && !multipartFile.isEmpty();
	}

	private String getUploadPath(String basePath) {
		return StringUtils.isBlank(uploadDir) ? basePath : String.format("%s/%s", basePath, uploadDir);
	}

	@Transient
	public String getFilePath(String basePath) {
		return String.format("%s%s%s", getUploadPath(basePath) + File.separatorChar + saveFileName);
	}

	@Transient
	public String getFormattedFileSize() {
		return FileUtils.byteCountToDisplaySize(this.fileSize);
	}
}
