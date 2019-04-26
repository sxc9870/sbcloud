package com.sbcloud.dao;
import org.springframework.data.repository.CrudRepository;

import com.sbcloud.pojo.TestModel;

public interface TargetRepository extends CrudRepository<TestModel,Integer>{
     
}