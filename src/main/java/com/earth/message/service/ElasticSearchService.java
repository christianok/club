package com.earth.message.service;

import com.earth.message.model.entity.Student;
import com.earth.message.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ElasticSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent() {
        Student student = new Student();
        student.setAge(15);
        student.setClazzId("20201111");
        student.setId(2);
        student.setName("zhangping");
        student.setNumber("15335305530");
        Student save = studentRepository.save(student);
    }

    public List<Student> findDoc() {
        // 构造搜索条件
        QueryBuilder builder = QueryBuilders.existsQuery("name");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
        log.debug("searchQuery {}", searchQuery);
        // 执行查询
        List<Student> novels = elasticsearchTemplate.queryForList(searchQuery, Student.class);

        return novels;
    }

    public List<Student> singleMatch(String value) {
        QueryBuilder matchQuery = QueryBuilders.matchQuery("name", value);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery).build();
        return elasticsearchTemplate.queryForList(searchQuery, Student.class);
    }

    public void queryCreate() {
        // 创建一个查询条件对象
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 拼接查询条件
        queryBuilder.should(QueryBuilders.termQuery("字段", "值"));
        // 创建聚合查询条件
        TermsAggregationBuilder agg = AggregationBuilders.terms("聚合名称，自定义，取出时会用到").field("聚合字段").size(100);
        // size是查询聚合出来的条数
        // 创建查询对象
        SearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder) //添加查询条件
                .addAggregation(agg) // 添加聚合条件
                .withPageable(PageRequest.of(0, 1)) //符合查询条件的文档分页（不是聚合的分页）
                .build();

        // 执行查询
        AggregatedPage<Student> students = elasticsearchTemplate.queryForPage(build, Student.class);

        // 取出聚合结果
        Aggregations studentsAggregations = students.getAggregations();
        Terms terms = (Terms)studentsAggregations.get("聚合名称，之前自定义的");

        // 遍历取出聚合字段列的值，与对应的数量
        for (Terms.Bucket bucket : terms.getBuckets()) {
            String keyAsString = bucket.getKeyAsString(); // 聚合字段列的值
            long docCount = bucket.getDocCount();// 聚合字段对应的数量
        }

    }
}
