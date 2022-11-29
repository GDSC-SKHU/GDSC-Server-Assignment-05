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
@EntityListeners(AuditingEntityListener.class) //BaseTime 클래스에 Auditing 기능을 포함한다
//Audit - Spring Data JPA에서 LastModifiedDate에 대해서 자동으로 값을 넣어줌
@MappedSuperclass //공통 매핑 정보가 필요할 때, 부모 클래스에 선언하여 속성만 상속받아 사용하고 싶을 때 쓰는 어노테이션
//DB 테이블이랑은 상관 없음
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate; //엔티티가 생성되어 저장될 떄 시간이 자동 저장

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate; //조회한 엔티티의 값을 변경할 때 시간이 자동 저장
}

/*
Entity - 데베에 쓰일 필드와 여러 엔티티간 연관관계를 정의한다,
테이블에 서비스에서 필요한 정보를 다 저장하고 활용하게 됨
테이블 전체가 엔티티, 가로의 행 부분이 엔티티 객체가 됨
필드란 엔티티의 각 Column
 */