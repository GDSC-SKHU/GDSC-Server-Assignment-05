package com.gdsc.jpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
OCP(개방-폐쇄 원칙): Entity는 기존 코드를 변경하지 않으면서(Close) 기능을 추가(Open)할 수 있도록 설계가 되어야 한다는 원칙
Getter를 지향, Setter는 지양
*/

@Getter         // Getter 메소드 자동 생성
// JPA Entity에 Persist, Remove, Update, Load에 대한 event 전과 후에 대한 콜백 메서드를 요청
@EntityListeners(AuditingEntityListener.class)      // 데이터가 갱신 or 생성됨에 따라 해당 시간을 자동으로 값을 넣어주는 기능
@MappedSuperclass       // 객체의 입장에서 공통 매핑 정보가 필요할 때, 공통 속성만 상속받아서 사용
public class BaseTimeEntity {

    @CreatedDate                        // 데이터 생성 시간 자동 저장
    @Column(updatable = false)          // 생성되면 갱신X
    protected LocalDateTime createDate;

    @LastModifiedDate                   // 데이터 갱신 시간 자동 저장
    protected  LocalDateTime lastModifiedDate;
}
