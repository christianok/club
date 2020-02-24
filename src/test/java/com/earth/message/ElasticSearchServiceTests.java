package com.earth.message;

import com.earth.message.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElasticSearchServiceTests {
    @Autowired
    private ElasticSearchService elasticSearchService;

}
