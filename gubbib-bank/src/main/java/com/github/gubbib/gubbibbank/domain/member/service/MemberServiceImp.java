package com.github.gubbib.gubbibbank.domain.member.service;

import com.github.gubbib.gubbibbank.domain.member.dto.MemberResponse;
import com.github.gubbib.gubbibbank.domain.member.dto.MemberSignupRequest;
import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.repository.MemberRepository;
import com.github.gubbib.gubbibbank.exception.BusinessException;
import com.github.gubbib.gubbibbank.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberResponse signup(MemberSignupRequest request) {

        log.debug("회원가입 요청: email = {}", request.email());

        if(memberRepository.existsByEmail(request.email())){
            log.debug("이미 존재하는 이메일: {}", request.email());
            throw new BusinessException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        Member m = Member.create(
                request.email(),
                request.password(),
                request.name(),
                request.phone()
        );

        memberRepository.save(m);

        log.debug("회원가입 완료: id={}, email={}",
                m.getId(),
                m.getEmail());

        return MemberResponse.from(m);
    }
}
