package com.applechip.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.applechip.core.constant.SystemConstant;

/**
 * FileUtil
 * @author https://github.com/maximinhan/applechip
 */
public class FileUtil extends FileUtils {
	private FileUtil() {
	}

	/**
	 * <p>
	 * Use: FileUtil.upload(multipartFile, uploadDir, saveFileName)
	 * </p>
	 * 
	 * @param MultipartFile multipartFile
	 * @param String uploadDir
	 * @param String saveFileName
	 * @return File
	 */
	public static File upload(MultipartFile multipartFile, String uploadDir, String saveFileName) {
		File file = new File(String.format("%s%s%s", uploadDir + File.separator + saveFileName));
		try {
			forceMkdir(file.getParentFile());
			multipartFile.transferTo(file);
		}
		catch (IOException ex) {
			throw new RuntimeException(String.format("file upload error:%s", file), ex);
		}
		return file;
	}

	public static void download(File file, String filename, HttpServletRequest request, HttpServletResponse response) {
		FileInputStream fileInputStream = null;
		OutputStream outputStream = null;
		String userAgent = StringUtil.defaultString(request.getHeader("User-Agent"));
		try {
			String header = "";
			if (StringUtil.contains(userAgent, "MSIE 5.5")) {
				header = String.format("filename=%s;", URLEncoder.encode(filename, SystemConstant.CHARSET.toString()));
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
			}
			else if (StringUtil.contains(userAgent, "MSIE")) {
				header = String.format("attachment; filename=%s;",
						URLEncoder.encode(filename, SystemConstant.CHARSET.toString()).replace("+", " "));
				response.setHeader("Cache-Control", null);
				response.setHeader("Pragma", null);
			}
			else {
				header = String.format("attachment; filename=\"%s\";",
						new String(filename.getBytes(SystemConstant.CHARSET), SystemConstant.CHARSET));
			}

			response.setContentType(String.format("application/octet-stream; charset=%s",
					SystemConstant.CHARSET.toString()));
			response.setHeader("Content-Disposition", header);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentLength((int) file.length());

			outputStream = response.getOutputStream();
			fileInputStream = new FileInputStream(file);

			IOUtil.copy(fileInputStream, outputStream);
			outputStream.flush();
		}
		catch (IOException e) {
			throw new RuntimeException(String.format("file(%s) download error.., message:%s", file.getAbsolutePath(),
					e.getMessage()), e);
		}
		finally {
			IOUtil.closeQuietly(fileInputStream, outputStream);
		}
	}
}
