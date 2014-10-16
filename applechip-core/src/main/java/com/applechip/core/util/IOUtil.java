package com.applechip.core.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class IOUtil extends IOUtils {

  public static void closeQuietly(List<? extends Closeable> list) {
    for (Closeable closeable : list) {
      if (closeable instanceof OutputStream) {
        closeQuietly((OutputStream) closeable);
      } else if (closeable instanceof InputStream) {
        closeQuietly((InputStream) closeable);
      } else if (closeable instanceof Writer) {
        closeQuietly((Writer) closeable);
      } else if (closeable instanceof Reader) {
        closeQuietly((Reader) closeable);
      }
    }
  }
}
