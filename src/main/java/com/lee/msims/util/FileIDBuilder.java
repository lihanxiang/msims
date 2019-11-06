package com.lee.msims.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileIDBuilder {

    public String generateFileId(){
        return UUID.randomUUID().toString().replace("-", ""); // from xxx-xxx to xxxxxx
    }
}
