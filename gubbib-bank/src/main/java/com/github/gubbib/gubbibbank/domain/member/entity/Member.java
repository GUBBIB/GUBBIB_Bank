package com.github.gubbib.gubbibbank.domain.member.entity;


import com.github.gubbib.gubbibbank.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_seq"
    )
    @SequenceGenerator(
            name = "member_seq",
            sequenceName = "member_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    private Member(
            String email,
            String password,
            String name,
            String phone
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;

        this.role = Role.USER;
        this.status = MemberStatus.ACTIVE;
    }

    public static Member create(
            String email,
            String password,
            String name,
            String phone
    ){
        return new Member(email, password, name, phone);
    }

    public void changePassword(String encodedPassword){
        this.password = encodedPassword;
    }

    public void changePhone(String phone){
        this.phone = phone;
    }

    public void suspend(){
        this.status = MemberStatus.SUSPENDED;
    }

    public void activate(){
        this.status = MemberStatus.ACTIVE;
    }

    public void withdraw(){
        this.status = MemberStatus.WITHDRAWN;
        delete();
    }

    public void promoteToAdmin(){
        this.role = Role.ADMIN;
    }
}
