package com.applechip.core;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class VO {
  static Map<Integer, WeakReference<VO>> cache = new LinkedHashMap<Integer, WeakReference<VO>>();

  public static VO getInstance(int value) {
    WeakReference<VO> cached = cache.get(value);
    if (cached == null) {
      VO vo = new VO(value);
      cache.put(value, new WeakReference<VO>(vo));
      return vo;
    }
    return cached.get();
  }

  private int value;

  private VO(int value) {
    this.value = value;
  }
}
