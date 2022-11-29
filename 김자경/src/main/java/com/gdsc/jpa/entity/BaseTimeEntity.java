package com.gdsc.jpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // Getter를 지향하고 Setter를 지양하자.(OCP: 개방폐쇄의 원칙)
@EntityListeners(AuditingEntityListener.class) // Audit : DB를 관리하기 편하도록 DB에 값을 넣을 때, 항상 특정 데이터가 포함
@MappedSuperclass // 속성만 내려서 테이블에서 같이 사용하는 상속
public class BaseTimeEntity {

    // 생성된 시간 정보를 자동으로 저장
    @CreatedDate
    // 최초로 등록은 되지만 수정은 불가능한 것이 보장
    @Column(updatable = false)
    protected LocalDateTime createDate; // 생성 시간

    // 수정된 시간 정보를 자동으로 저장
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate; // 마지막 수정 시간
}
