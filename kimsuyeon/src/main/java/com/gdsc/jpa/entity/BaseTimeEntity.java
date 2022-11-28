package com.gdsc.jpa.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)//시간에 대해서 자동으로 값을 넣어주는 기능
@MappedSuperclass//부모 클래스에 선언하고 속성만 상속 받아서 사용하고 싶을 때 사용
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;


}
