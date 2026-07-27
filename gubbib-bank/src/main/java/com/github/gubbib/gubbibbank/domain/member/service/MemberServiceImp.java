package com.github.gubbib.gubbibbank.domain.member.service;

import com.github.gubbib.gubbibbank.domain.auth.dto.LoginResponse;
import com.github.gubbib.gubbibbank.domain.auth.dto.SignupRequest;
import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.entity.MemberStatus;
import com.github.gubbib.gubbibbank.domain.member.repository.MemberRepository;
import com.github.gubbib.gubbibbank.exception.BusinessException;
import com.github.gubbib.gubbibbank.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean existsMember(String email) {
        return  memberRepository.existsByEmail(email);
    }

    @Override
    public Member register(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findActiveMemberByEmail(String email) {
        return memberRepository
                .findByEmailAndStatus(email, MemberStatus.ACTIVE)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
