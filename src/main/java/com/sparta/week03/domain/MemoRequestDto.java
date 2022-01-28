package com.sparta.week03.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
//Setter는 필요없다고함.
@RequiredArgsConstructor
public class MemoRequestDto {
    // username, contents

    private final String username;
    private final String contents;
    private final String title;
}
