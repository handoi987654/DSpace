package com.Desert.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
@Configuration
public class ResourceConfig {

    @Value("classpath:vectors")
    private Resource vectorsRsrc;
    private static Map<String, Resource> vectorMap;

    @PostConstruct
    private void initializeResources() throws IOException {
        loadSVG();
    }

    private void loadSVG() throws IOException {
        File vectorDir = new File(vectorsRsrc.getURI());
        vectorMap = new HashMap<>();
        for (File file : vectorDir.listFiles()) {
            String key = file.getName().substring(0, file.getName().indexOf("."));
            vectorMap.put(key, new FileUrlResource(file.getAbsolutePath()));
        }
    }

    public static Resource getVector(String vectorName) {
        return vectorMap.get(vectorName);
    }
}
