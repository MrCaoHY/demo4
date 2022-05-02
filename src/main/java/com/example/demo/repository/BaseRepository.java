package com.example.demo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @program: demo4
 * @description: 基础功能
 * @author: CaoHaiyang
 * @create: 2022-05-02 17:56
 **/
@NoRepositoryBean
public interface BaseRepository<T,Long> extends PagingAndSortingRepository<T,Long> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
