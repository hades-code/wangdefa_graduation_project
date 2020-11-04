package org.lhq.gp.controller;

import org.lhq.gp.service.MySendServcie;
import org.springframework.web.bind.annotation.*;

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
    private MySendServcie mySendServcie;

    @GetMapping("send/{msg}")
    public void sendMyMsg(@PathVariable("msg") String msg){
        mySendServcie.sendMsg(msg);
    }
}
