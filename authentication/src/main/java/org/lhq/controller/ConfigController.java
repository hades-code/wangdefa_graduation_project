package org.lhq.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wangdefa_graduation_project
 * @description: Config
 * @author: Wang defa
 * @create: 2020-09-09 16:52
 */

@RestController
@RefreshScope
@RequestMapping("/config")
public class ConfigController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
