package com.Desert.Utility;

import com.Desert.Configuration.ResourceConfig;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FXUtility {

    default Resource getVector(String vectorName) {
        return ResourceConfig.getVector(vectorName);
    }

    default String getVectorContent(String vectorName) throws IOException {
        return SVGPathGenerator.generate(getVector(vectorName));
    }
}
