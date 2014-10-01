package com.applechip.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;

import com.google.common.collect.Lists;

public class IteratorUtil extends IteratorUtils {

	public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
		return Lists.newArrayList(elements);
	}

	public static <E> Set<E> union(Set<E> set1, Set<E> set2) {
		set1.addAll(set2);
		return set1;
	}
}
