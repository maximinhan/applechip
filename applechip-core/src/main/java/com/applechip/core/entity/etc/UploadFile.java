package com.applechip.core.entity.etc;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedDt;
import com.applechip.core.entity.network.Server;
import com.applechip.core.util.FileUtil;
import com.applechip.core.util.StringUtil;

@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"multipartFile"})
@NoArgsConstructor
@Data
@Entity
@Table(name = "et_upload_file")
public class UploadFile extends GenericUpdatedDt<String> {

  private static final long serialVersionUID = -7837313689981801628L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", unique = true, length = ColumnLengthConstant.UUID)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "save_name")
  private String saveName;

  @Column(name = "upload_path")
  private String uploadPath;

  @Column(name = "upload_uri")
  private String uploadUri;

  // @Column(name = "size")
  // private long size;

  @ManyToOne(optional = true)
  @JoinColumn(name = "server_id")
  private Server server;

  @Transient
  private transient MultipartFile multipartFile;

  public UploadFile(MultipartFile multipartFile, String uploadUri) {
    this(multipartFile, uploadUri, null, null);
  }

  public UploadFile(MultipartFile multipartFile, String uploadUri, String saveName) {
    this(multipartFile, uploadUri, saveName, null);
  }

  public UploadFile(MultipartFile multipartFile, String uploadUri, String saveName, String uploadPath) {
    this(multipartFile, uploadUri, saveName, uploadPath, null);
  }

  public UploadFile(MultipartFile multipartFile, String uploadUri, String saveName, String uploadPath, Server server) {
    super();
    this.multipartFile = multipartFile;
    this.uploadUri = uploadUri;
    this.uploadPath = uploadPath;
    this.saveName = saveName;
    this.server = server;
  }

  public File upload(String basePath) {
    if (!this.existsFileToUpload())
      return null;
    String uploadPath = getUploadPath(basePath);
    if (StringUtil.isBlank(saveName)) {
      if (StringUtil.isBlank(id)) {
        throw new RuntimeException("set filename or save before upload file...");
      }
      this.saveName = id;
    }
    this.name = multipartFile.getOriginalFilename();
    // this.size = multipartFile.getSize();
    return FileUtil.upload(multipartFile, uploadPath, saveName);
  }

  public void download(String basePath, HttpServletRequest request, HttpServletResponse response) {
    File file = new File(getFilePath(basePath));
    if (!file.exists())
      throw new RuntimeException(String.format("file not found...(%s)", getFilePath(basePath)));
    FileUtil.download(file, name, request, response);
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
    return StringUtil.isBlank(uploadUri) ? basePath : String.format("%s/%s", basePath, uploadUri);
  }

  @Transient
  public String getFilePath(String basePath) {
    return String.format("%s%s%s", getUploadPath(basePath) + File.separatorChar + saveName);
  }

  // @Transient
  // public String getByteCountToDisplaySize() {
  // return FileUtil.byteCountToDisplaySize(this.size);
  // }
}
