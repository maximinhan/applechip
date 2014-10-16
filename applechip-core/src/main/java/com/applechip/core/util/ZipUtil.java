package com.applechip.core.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

import com.applechip.core.constant.SystemConstant;

@Slf4j
public class ZipUtil {

  /*
   * ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\w\d\s])(?=\S+$).{8,24}$
   * 
   * ^ : start-of-string (?=.*[0-9]) : a digit must occur at least once (?=.*[a-z]) : a lower case
   * letter must occur at least once (?=.*[A-Z]) : a upper case letter must occur at least once
   * (?=.*[^\w\d\s]) : a special character must occur at least once (?=\S+$) : no whitespace allowed
   * in the entire string .{8,24} : anything, at least 8~24 length $ : end-of-string
   */
  public static void zip(File directory, File zip) {
    zip(directory, zip, null, true);
  }

  public static void zip(File directory, File zip, String pattern, boolean include) {
    URI uri = directory.toURI();
    Deque<File> deque = new LinkedList<File>();
    deque.push(directory);
    ZipOutputStream zipOutputStream = null;
    OutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(zip);
      zipOutputStream = new ZipOutputStream(outputStream);
      while (!deque.isEmpty()) {
        directory = deque.pop();
        for (File file : directory.listFiles()) {
          if (StringUtil.isNotBlank(pattern)) {
            boolean matches = file.getName().matches(pattern);
            if ((matches && !include) || (!matches && include)) {
              continue;
            }
          }
          String name = uri.relativize(file.toURI()).getPath();
          if (file.isDirectory()) {
            deque.push(file);
            if (!StringUtil.endsWith(name, SystemConstant.FILE_SEPARATOR)) {
              name = String.format("%s%s", name, SystemConstant.FILE_SEPARATOR);
            }
            zipOutputStream.putNextEntry(new ZipEntry(name));
          } else {
            zipOutputStream.putNextEntry(new ZipEntry(name));
            copy(file, zipOutputStream);
            zipOutputStream.closeEntry();
          }
        }
      }
    } catch (IOException e) {
      log.debug("Failed to zip the file. (directory: {}, zip: {}) ", directory.getPath(), zip.getName());
      throw new RuntimeException(String.format("Failed to zip the file. (directory: %s, zip: %s) ", directory.getPath(), zip.getName()), e);
    } finally {
      IOUtil.closeQuietly(Arrays.asList(zipOutputStream, outputStream));
    }
  }

  public static void unzip(File zip, File directory) {
    ZipFile zipFile = null;
    InputStream inputStream = null;
    try {
      zipFile = new ZipFile(zip);
      Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
      while (zipEntries.hasMoreElements()) {
        ZipEntry zipEntry = zipEntries.nextElement();
        File file = new File(directory, zipEntry.getName());
        if (zipEntry.isDirectory()) {
          FileUtil.forceMkdir(file);
        } else {
          FileUtil.forceMkdir(file.getParentFile());
          inputStream = zipFile.getInputStream(zipEntry);
          copy(inputStream, file);
        }
      }
    } catch (IOException e) {
      log.debug("Failed to unzip the file. (directory: {}, zip: {}) ", directory.getPath(), zip.getName());
      throw new RuntimeException(String.format("Failed to unzip the file. (directory: %s, zip: %s) ", directory.getPath(), zip.getName()), e);
    } finally {
      IOUtil.closeQuietly(Arrays.asList(zipFile, inputStream));
    }
  }

  public static void unzip(String zip, String directory, String password) {
    try {
      net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zip);
      if (zipFile.isEncrypted()) {
        zipFile.setPassword(password);
      }
      zipFile.extractAll(directory);
    } catch (ZipException e) {
      log.debug("Failed to unzip the file. (directory: {}, zip: {}) ", directory, zip);
      throw new RuntimeException(String.format("Failed to unzip the file. (directory: %s, zip: %s) ", directory, zip), e);
    }
  }

  private static void copy(File file, OutputStream outputStream) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    try {
      IOUtil.copy(inputStream, outputStream);
    } finally {
      IOUtil.closeQuietly(inputStream);
    }
  }

  private static void copy(InputStream inputStream, File file) throws IOException {
    OutputStream outputStream = new FileOutputStream(file);
    try {
      IOUtil.copy(inputStream, outputStream);
    } finally {
      IOUtil.closeQuietly(outputStream);
    }
  }
}
