package com.applechip.core.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

public class IOUtil extends IOUtils {

  public static void closeQuietly(Closeable[] closeables) {
    for (Closeable closeable : closeables) {
      if (closeable instanceof OutputStream) {
        closeQuietly((OutputStream) closeable);
      } else if (closeable instanceof InputStream) {
        closeQuietly((OutputStream) closeable);
      } else if (closeable instanceof Writer) {
        closeQuietly((OutputStream) closeable);
      } else if (closeable instanceof Reader) {
        closeQuietly((OutputStream) closeable);
      }
    }
  }
}
