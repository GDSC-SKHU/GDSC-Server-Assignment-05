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
@EntityListeners(AuditingEntityListener.class)      // audit = 자동으로 값을 넣어주는 기능, ex) 시간
@MappedSuperclass       // 해당 추상 클래스를 상속할 경우 변수들을 컬럼으로 인식하게 해준다.
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate;

    @LastModifiedDate
    protected  LocalDateTime lastModifiedDate;
}
