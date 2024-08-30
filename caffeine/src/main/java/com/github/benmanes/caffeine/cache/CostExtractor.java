/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.github.benmanes.caffeine.cache;

/**
 * @author fengjian
 * @version CostExtractor.java, v 0.1 2024-08-13 14:19 fengjian
 */
public interface CostExtractor<K, V> {

  double extract(K key, V value);

  static <K, V> CostExtractor<K, V> singletonCostExtractor() {
    @SuppressWarnings("unchecked")
    CostExtractor<K, V> instance = (CostExtractor<K, V>) SingletonCostExtractor.INSTANCE;
    return instance;
  }

  enum SingletonCostExtractor implements CostExtractor<Object, Object> {

    INSTANCE;

    @Override
    public double extract(Object key, Object value) {
      return 1;
    }
  }
}
