package com.applechip.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class IteratorUtil extends IteratorUtils {

  public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
    return Lists.newArrayList(elements);
  }

  public static <E> Set<E> union(Set<E> set1, Set<E> set2) {
    set1.addAll(set2);
    return set1;
  }

  public static <E> Map<String, E> toto(final String fieldName, Iterator<E> elements) {
    Map<String, E> map = Maps.uniqueIndex(elements, new Function<E, String>() {
      public String apply(E element) {
        return FieldUtil.readField(element, fieldName).toString();
      }
    });
    return map;
  }
}
