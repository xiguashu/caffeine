/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.github.benmanes.caffeine.cache.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author fengjian
 * @version HashUtils.java, v 0.1 2024-08-26 11:05 fengjian
 */
public class HashUtils {

  public static long hash64A(byte[] data, int seed) {
    return hash64A(ByteBuffer.wrap(data), seed);
  }

  public static long hash64A(byte[] data, int offset, int length, int seed) {
    return hash64A(ByteBuffer.wrap(data, offset, length), seed);
  }

  public static long hash64A(ByteBuffer buf, int seed) {
    ByteOrder byteOrder = buf.order();
    buf.order(ByteOrder.LITTLE_ENDIAN);
    long m = -4132994306676758123L;
    int r = 47;

    long h;
    for(h = (long)seed ^ (long)buf.remaining() * m; buf.remaining() >= 8; h *= m) {
      long k = buf.getLong();
      k *= m;
      k ^= k >>> r;
      k *= m;
      h ^= k;
    }

    if (buf.remaining() > 0) {
      ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
      finish.put(buf).rewind();
      h ^= finish.getLong();
      h *= m;
    }

    h ^= h >>> r;
    h *= m;
    h ^= h >>> r;
    buf.order(byteOrder);
    return h;
  }

  public static long hash(byte[] key) {
    return hash64A(key, 305441741);
  }

  public static long hash(String key) {
    if (key == null) {
      return 0;
    }
    return hash(key.getBytes());
  }
}
