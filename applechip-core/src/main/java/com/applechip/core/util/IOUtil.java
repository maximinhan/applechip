package com.applechip.core.util;

import java.io.Closeable;

import org.apache.commons.io.IOUtils;

public class IOUtil extends IOUtils {

  public static void closeQuietly(Closeable[] closeables) {
    for (Closeable closeable : closeables) {
      closeQuietly(closeable);
    }
  }
}
