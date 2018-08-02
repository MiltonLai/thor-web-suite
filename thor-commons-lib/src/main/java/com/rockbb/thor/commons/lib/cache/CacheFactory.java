package com.rockbb.thor.commons.lib.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheFactory
{
	private CacheManager cacheManager;

	public CacheFactory(){}

	public CacheFactory(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	public Cache get(String key, int size, int live)
	{
		return get(key, size, false, false, live, live, true);
	}

	public Cache get(String key, int size, int live, boolean create)
	{
		return get(key, size, false, false, live, live, create);
	}

	/**
	 * 根据指定参数获取缓存组, 在不存在的情况下创建并返回(或不创建)
	 *
	 * @param key cache名称
	 * @param size cache存放记录的数量
	 * @param toDisk 是否持久化到磁盘
	 * @param eternal 是否永不过期
	 * @param live 缓存存活期
	 * @param idle 缓存无访问失效期
	 * @param create 不存在时, 是否创建
	 * @return 缓存组
	 */
	public Cache get(
			String key,
			int size,
			boolean toDisk,
			boolean eternal,
			int live,
			int idle,
			boolean create)
	{
		Cache cache = cacheManager.getCache(key);
		if (cache == null && create)
		{
			cache = new Cache(key, size, toDisk, eternal, live, idle);
			cacheManager.addCacheIfAbsent(cache);
		}
		return cache;
	}

	/**
	 * 删除某个缓存组
	 *
	 * @param key 组名
	 */
	public void remove(String key)
	{
		cacheManager.removeCache(key);
	}
}
