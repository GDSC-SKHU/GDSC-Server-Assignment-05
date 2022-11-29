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
@EntityListeners(AuditingEntityListener.class) // 시간을 자동으로 값을 넣어주는 어노테이션
@MappedSuperclass // 속성만 상속받을 수 있는 어노테이션
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
