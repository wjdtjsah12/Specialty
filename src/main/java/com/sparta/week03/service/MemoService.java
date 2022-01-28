package com.sparta.week03.service;

import com.sparta.week03.domain.Memo;
import com.sparta.week03.domain.MemoRepository;
import com.sparta.week03.domain.MemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
//final 과 같이 추가하는
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    //꼭필요하므로 final 선언

    @Transactional
    // DB에 반영하라는 어노테이션
    public Long update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                //        () -> new NullPointerException("아이디가 존재하지 않습니다.");
                //도 사용가능
        );
        memo.update(requestDto);
        return memo.getId();
    }
}
