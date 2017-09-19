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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class App 
{

    public static void main(String[] args) throws Exception {
    	//System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(App.class, args);
        //SpringApplication app = new SpringApplication(MySpringConfiguration.class);
       // app.setBannerMode(Banner.Mode.OFF);
       // app.run(args);
    }
}