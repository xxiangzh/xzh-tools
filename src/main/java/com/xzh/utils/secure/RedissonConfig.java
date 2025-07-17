package com.xzh.utils.secure;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 *
 * @author 向振华
 * @date 2025/07/16 17:45
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }
}
