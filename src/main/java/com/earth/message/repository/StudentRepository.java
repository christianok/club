package com.earth.message.repository;

import com.earth.message.model.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentRepository extends ElasticsearchRepository<Student, Integer> {

}
