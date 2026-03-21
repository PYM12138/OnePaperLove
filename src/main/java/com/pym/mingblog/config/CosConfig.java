package com.pym.mingblog.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CosConfig {

    @Value("${cos.secret-id}")
    private String secretId;

    @Value("${cos.secret-key}")
    private String secretKey;

    @Value("${cos.region}")
    private String region;

    @Value("${cos.bucket-name}")
    private String bucketName;

    @Bean
    public COSClient cosClient() {
        // 1. 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2. 设置存储桶的地域
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3. 生成 COS 客户端
        return new COSClient(cred, clientConfig);
    }

    // 如果你在其他地方需要用到 bucketName，也可以把它作为一个 Bean 注入
    @Bean
    public String cosBucketName() {
        return this.bucketName;
    }
}