package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @program: demo4
 * @description: 基础类
 * @author: CaoHaiyang
 * @create: 2022-05-02 15:10
 **/
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
