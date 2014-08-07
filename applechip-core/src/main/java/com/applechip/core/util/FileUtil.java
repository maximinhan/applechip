package com.applechip.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
  public static File upload(MultipartFile multipartFile, String uploadDir, String saveFileName) {
    File targetFile = new File(String.format("%s%s%s", uploadDir + File.separator + saveFileName));
    try {
      FileUtils.forceMkdir(targetFile.getParentFile());
      multipartFile.transferTo(targetFile);
    } catch (IOException ex) {
      throw new RuntimeException(String.format("file upload error:%s", targetFile), ex);
    }
    return targetFile;
  }

  public static void download(File file, String filename, HttpServletRequest request, HttpServletResponse response) {
    FileInputStream fileInputStream = null;
    try {
      response.setContentLength((int) file.length());
      fileInputStream = new FileInputStream(file);
      OutputStream outputStream = getOutputStream(request, response, filename);
      IOUtils.copy(fileInputStream, outputStream);
      outputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(String.format("file(%s) download error.., message:%s", file.getAbsolutePath(), e.getMessage()), e);
    } finally {
      IOUtils.closeQuietly(fileInputStream);
    }
  }

  private static OutputStream getOutputStream(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException {
    response.setContentType(String.format("application/octet-stream; charset=%s", Charsets.UTF_8.toString()));
    String userAgent = StringUtils.defaultString(request.getHeader("User-Agent"));
    String header = "";
    if (StringUtils.contains(userAgent, "MSIE 5.5")) {
      header = String.format("filename=%s;", URLEncoder.encode(filename, Charsets.UTF_8.toString()));
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");
    } else if (StringUtils.contains(userAgent, "MSIE")) {
      header = String.format("attachment; filename=%s;", URLEncoder.encode(filename, Charsets.UTF_8.toString()).replace("+", " "));
      response.setHeader("Cache-Control", null);
      response.setHeader("Pragma", null);
    } else {
      header = String.format("attachment; filename=\"%s\";", new String(filename.getBytes(Charsets.UTF_8), Charsets.UTF_8));
    }
    response.setHeader("Content-Disposition", header);
    response.setHeader("Content-Transfer-Encoding", "binary");
    return response.getOutputStream();
  }

}
