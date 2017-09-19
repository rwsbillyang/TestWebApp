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

package hello;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 runs on port: server.port=9090 
 
Type mvn spring-boot:run from the root project directory to start the application:
To gracefully exit the application hit ctrl-c.

Creating an executable jar:
$ mvn package

Peek inside, you can use jar tvf: $ jar tvf target/myproject-0.0.1-SNAPSHOT.jar

To run that application, use the java -jar command:
$ java -jar target/myproject-0.0.1-SNAPSHOT.jar

As before, to gracefully exit the application hit ctrl-c.

 * */

@Controller
@EnableAutoConfiguration
public class SampleController {
	@Autowired
	private MyBean myBean;
	
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    
    @RequestMapping("/delKeys")
    @ResponseBody
    String delKeys(String prefix)
    {
    	if(StringUtils.isEmpty(prefix))
    	{
    		return "prefix is empty";
    	}
    	myBean.deleteKeyValuesByPrefix(prefix);
    	return "Done,  please check";
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}