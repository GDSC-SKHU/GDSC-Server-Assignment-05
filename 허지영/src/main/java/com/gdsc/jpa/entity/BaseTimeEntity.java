package com.gdsc.jpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// audi 기능 사용하여 DB에 값을 넣을 때 항상 특정 데이터가 포함되도록 함
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTimeEntity {
    @CreatedDate // 생성된 시간 저장
    @Column(updatable = false) // 컬럼을 수정한 이후 기존에 저장되어 있던 데이터 수정 x
    protected LocalDateTime createDate;

    protected LocalDateTime lastModifiedDate;
}
