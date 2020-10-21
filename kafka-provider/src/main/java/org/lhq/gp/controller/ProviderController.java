package org.lhq.gp.controller;

import org.lhq.gp.service.SendService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-10-21 11:07
 */

@RestController
public class ProviderController {

    @Resource
    private SendService sendService;

    @PostMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg){
        sendService.sendMsg(msg);
    }
}
