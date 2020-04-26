package com.opensource.component.logs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("component.req.logs")
@Data
public class ReqLogProperties {

    private boolean enabled = true;

    private int slowReqElapsed = 3000;
}
