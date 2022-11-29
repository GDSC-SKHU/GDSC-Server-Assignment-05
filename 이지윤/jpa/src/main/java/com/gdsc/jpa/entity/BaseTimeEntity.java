package com.gdsc.jpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//상속으로만 사용가능
@Getter
@MappedSuperclass  //JPA Entity 클래스들이 BaseTimeEntity를 상속 할 경우 두 필드도 컬럼으로 인식하도록 설정.
//해당 클래스에 Auditing 기능을 포함시켜줌. auditing을 이용하면 자동으로 시간을 매핑하여 db테이블에 저장해줌.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate //수정되지 않고 감. 생성 시 날짜 자동 생성
    @Column(updatable = false)
    protected LocalDateTime createDate; //db로 가면 자동으로 create_date로 됨.

    @LastModifiedDate //수정 시 날짜 자동 갱신.
    protected LocalDateTime lastModifiedDate;
}
