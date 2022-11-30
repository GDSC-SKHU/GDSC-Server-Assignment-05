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
@EntityListeners(AuditingEntityListener.class)//Entitiy가 삽입, 삭제, 수정, 조회 등의 작업을 할 때 전, 후에 작업을 하기 위해 이벤트 처리를 위한 어노테이션
@MappedSuperclass//공통된 매핑 정보
public abstract class BaseTimeEntity {

    @CreatedDate//날짜 생성
    @Column(updatable = false)//upadate 이후 기존의 저장되어있던 데이터를 수정할 수 없게 만든다.
    protected LocalDateTime createDate;

    @LastModifiedDate//마지막에 수정된 시각
    protected LocalDateTime lastModifiedDate;

}
