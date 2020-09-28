package com.tc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.tc.core.utils.RedisUtil;

public class RedisTest {
	@Autowired
	private RedisUtil redisTemplate;
	
	
//	@Test
//	public void test() {
//		boolean set = redisTemplate.set("ddd", "1433");
//		System.out.println(set);
//	}
}
