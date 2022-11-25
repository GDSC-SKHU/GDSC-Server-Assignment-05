package com.gdsc.gdscjpaexample.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
   Member, Team 객체는 모두 createDate, lastModifiedDate 공통으로 포함하고 있음
   BaseTimeEntity 만들어서 코드의 중복을 줄임, 상속 받으면 사용 가능
 */

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTimeEntity {

    // 생성일자는 update 불가능하게 설정
    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate;

    // 수정일자는 계속 바뀔 수 있으니 따로 설정하지 않음
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
