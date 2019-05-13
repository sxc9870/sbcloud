package com.sbcloud.dao;
import org.springframework.data.repository.CrudRepository;

import com.sbcloud.pojo.KLinePojo;
import com.sbcloud.pojo.StockPojo;

public interface StockRepository extends CrudRepository<StockPojo,Integer>{
     
}