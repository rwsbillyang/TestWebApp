/**  
* @Copyright  www.redwolf-soft.com Inc.  All rights reserved. 
* This software is the confidential and proprietary   
* information of RedWolf Software.   
* ("Confidential Information"). You shall not disclose   
* such Confidential Information and shall use it only  
* in accordance with the terms of the contract agreement   
* you entered into with  RedWolf Software.  
* 
* @author:ycg<billyang@redwolf-soft.com>,24272238@qq.com
* Create date: Sep 19, 2017
* Description: TODO
*
*/

package cn.niukid.test;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class TestDelRedisKeys {
	private static Logger log = Logger.getLogger(TestDelRedisKeys.class);
	

    
    @RequestMapping("/delKeys")
    @ResponseBody
    String delKeys(String prefix)
    {
	    	if(StringUtils.isEmpty(prefix))
	    	{
	    		return "prefix is empty";
	    	}
	    	deleteKeysByPrefix(prefix);
	    	//deleteKeyValuesByPrefixInPipeline(prefix);
	    	return "Done,  please check";
    }
    
    
    
	@Autowired
	private RedisTemplate<String,String>  redisTemplate;
	
    //EVAL "local keys = redis.call('keys', ARGV[1]) \n for i=1,#keys,5000 do \n redis.call('del', unpack(keys, i, math.min(i+4999, #keys))) \n end \n return keys" 0 prefix:* – 
	private void deleteKeysByPrefix(String prefix)
	{
		final String pattern=prefix + "*";
		Set<String> set =redisTemplate.keys(pattern);
		if(set==null||set.size()==0)
		{
			log.info("no keys for "+pattern +", set="+set);
		}else
		{
			log.info(" keys for "+pattern + " size="+set.size());		
			//redisTemplate.delete(set);
		}
	}

	/**
	 * 清除某个前缀作为开头的所有键值对
	 * 清楚无效
	*/
	public void deleteKeyValuesByPrefixInPipeline(String prefix) {
		log.info("deleteKeyValuesByPrefix,prefix="+prefix);
		
		//final byte[] rawKey = ((StringRedisSerializer) redisTemplate.getKeySerializer()).serialize(prefix + "*");
		//printBytes("KeySerializer",rawKey);
		final String pattern=prefix + "*";
		final byte[] rawKey=pattern.getBytes();
		printBytes("getBytes",rawKey);
		
		// pipeline中删除所有缓存站点
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {			
				connection.openPipeline();

				Set<byte[]> set = connection.keys(rawKey);
				//FIXME: set is null for ever,actually has keys for pattern
				if(set==null||set.size()==0)
				{
					log.info("no keys for "+pattern +", set="+set);
				}else
				{
					log.info("del keys for "+pattern + ",size="+set.size());		
					byte[][] bytesArray = (byte[][]) set.toArray();
					//Long count = connection.del(bytesArray);
					
				}
				
				connection.closePipeline();
				return null;
			}
		}, false, true);

		// redisTemplate.delete(redisTemplate.keys(prefix+"*"));
	} 
   
	/*
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void deleteKeyValuesByPrefixInPipeline2(String prefix) {
		log.info("deleteKeyValuesByPrefix,prefix="+prefix);
		final String pattern=prefix + "*";
		// pipeline中删除所有缓存站点
		stringRedisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				stringRedisConn.openPipeline();
				
				Collection<String> collection = stringRedisConn.keys(pattern);
				
				if(collection==null||collection.size()==0)
				{
					log.info("no keys for "+pattern +", set="+collection);
				}else
				{
					log.info("del keys for "+pattern + ", ......");				
					//Long count = stringRedisConn.del(collection.toArray(new String[collection.size()]));
					
				}
				
				stringRedisConn.closePipeline();
				return null;
			}
		}, false, true);

		// redisTemplate.delete(redisTemplate.keys(prefix+"*"));
	}
	private void deleteKeysByPrefix2(String prefix)
	{
		final String pattern=prefix + "*";
		Set<String> set =stringRedisTemplate.keys(pattern);
		if(set==null||set.size()==0)
		{
			log.info("no keys for "+pattern +", set="+set);
		}else
		{
			log.info(" keys for "+pattern + " size="+set.size());		
			//stringRedisTemplate.delete(set);
		}
	}*/
	

	

	
	
	private void printBytes(String qualifer,byte[] bytes)
	{
		log.info("==============print bytes start, qualifer="+qualifer);
		for (int i = 0; i < bytes.length; i ++) {
            System.out.printf("0x%02X ", bytes[i]);
     }
		log.info("==============print bytes Done! qualifer="+qualifer);
	}
	
}