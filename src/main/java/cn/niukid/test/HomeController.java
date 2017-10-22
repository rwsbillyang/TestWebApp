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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class HomeController {
	
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
  
}