package com.github.gubbib.gubbibbank.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
/*
Entity는 아니지만 상속받는 Entity에게 필드를 주게하는 어노테이션
 */
@EntityListeners(AuditingEntityListener.class)
/*
아래 @CreatedDate, @LastModifiedDate 일을 할 때
JPA Auditing이 자동으로 [수정, 생성] 을 감사히는데
@EntityListeners 어노테이션이 없으면 일을 안한다
AuditingEntityListeners 클래스에 있다
 */

/*
MappedSuperclass, EntityListeners, CreatedDate, LastModifiedDate 전부다
JPAAuditing 의 기능이다

@EnableJpaAuditing 을 설정안하면 동작하지 않는다

 */
@Getter
public abstract class BaseEntity {

    @CreatedDate // DB에 저장될때 자동으로 생성일 넣어주는 어노테이션
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // DB에 업데이트 될 때마다 자동으로 현재시간을 넣어주는 어노테이션
    private LocalDateTime lastModifiedAt;

    private LocalDateTime deletedAt;

    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted(){
        return deletedAt != null;
    }

    public void restore(){
        this.deletedAt = null;
    }
}
