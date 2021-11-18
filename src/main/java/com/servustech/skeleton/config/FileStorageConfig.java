package com.servustech.skeleton.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.servustech.skeleton.utils.filestorage.AWSFileStorage;
import com.servustech.skeleton.utils.filestorage.FSFileStorage;
import com.servustech.skeleton.utils.filestorage.FileStorage;
import com.servustech.skeleton.utils.filestorage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FileStorageConfig {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.storage.type}")
    private StorageType storageType;

    @Value("${cloud.aws.bucket}")
    private String bucket;

    @Bean
    public FileStorage defaultFileStorage() {
        if (storageType.isAWS()) {
            return new AWSFileStorage(amazonS3Client, bucket);
        }
        return new FSFileStorage();
    }

}
