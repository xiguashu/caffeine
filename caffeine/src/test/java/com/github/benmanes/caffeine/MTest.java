/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.github.benmanes.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CostExtractor;
import com.github.benmanes.caffeine.cache.Weigher;
import org.checkerframework.checker.index.qual.NonNegative;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author fengjian
 * @version Test.java, v 0.1 2024-08-12 14:17 fengjian
 */

public class MTest {
  Cache<String, Data> cache;

  @Before
  public void before() {
    cache = Caffeine.newBuilder()
      .maximumWeight(3)
      .expireAfterWrite(10, TimeUnit.MINUTES)
      .weigher(new Weigher<String, Data>() {
        @Override
        public @NonNegative int weigh(String key, Data value) {
          return value.size;
        }
      })
      .costExtractor((key, value) -> value.cost)
      .softValues()
      .build();
  }

  @Test
  public void test() throws InterruptedException {
    cache.put("key0", new Data("v0", 1, 1));
    Thread.sleep(1200);
    cache.put("key1", new Data("v1", 1, 2));
    Thread.sleep(1200);
    cache.put("key2", new Data("v2", 1, 1));
    Thread.sleep(1200);
    cache.put("key3", new Data("v3", 10, 1));
    Thread.sleep(1200);

    System.out.println(cache.getIfPresent("key1"));
    System.out.println(cache.getIfPresent("key2"));
    System.out.println(cache.getIfPresent("key3"));
    System.out.println(cache.getIfPresent("key1"));
    System.out.println(cache.getIfPresent("key2"));
    System.out.println(cache.getIfPresent("key3"));
  }


  static class Data {

    private final String data;

    private final int cost;

    private final int size;

    public Data(String data, int cost, int size) {
      this.data = data;
      this.cost = cost;
      this.size = size;
    }

    @Override
    public String toString() {
      return "Data{" +
        "data='" + data + '\'' +
        ", cost=" + cost +
        ", size=" + size +
        '}';
    }
  }

}
