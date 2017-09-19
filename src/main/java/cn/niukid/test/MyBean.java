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

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Component
public class MyBean {
	private static Logger log = Logger.getLogger(MyBean.class);
    //private StringRedisTemplate template;
	private RedisTemplate redisTemplate;

    @Autowired
    public MyBean(RedisTemplate template) {
        this.redisTemplate = template;
    }


	private void printBytes(String qualifer,byte[] bytes)
	{
		log.info("==============print bytes start, qualifer="+qualifer+" =================");
		for (int i = 0; i < bytes.length; i ++) {
            System.out.printf("0x%02X ", bytes[i]);
     }
		log.info("==============print bytes Done! qualifer="+qualifer+" =================");
	}
	/**
	 * 清除某个前缀作为开头的所有键值对
	 */
	public void deleteKeyValuesByPrefix(final String prefix) {
		log.info("deleteKeyValuesByPrefix,prefix="+prefix);
		
		final byte[] rawKey = ((StringRedisSerializer) redisTemplate.getKeySerializer()).serialize(prefix + "*");
		printBytes("KeySerializer",rawKey);
		
		byte[] rawkey2=(prefix + "*").getBytes();
		printBytes("getBytes",rawkey2);
		
		// pipeline中删除所有缓存站点
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				//StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				connection.openPipeline();
				//Collection<String> collection = stringRedisConn.keys(prefix + "*");
				//Long count = connection.del(collection.toArray(new String[collection.size()]));
				
				Collection<byte[]> collection = connection.keys(rawKey);
				if(collection==null||collection.size()==0)
				{
					log.info("no keys for "+prefix + "*"+", collection="+collection);
				}else
				{
					log.info("del keys for "+prefix + "*");
					byte[][] bytesArray = (byte[][]) collection.toArray();
					//Long count = connection.del(bytesArray);
					//
				}
				
				connection.closePipeline();
				return null;
			}
		}, false, true);

		// redisTemplate.delete(redisTemplate.keys(prefix+"*"));
	}

}
