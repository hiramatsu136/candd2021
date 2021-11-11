package com.sample.sample.mapper;

import java.util.List;

import com.sample.sample.entity.Member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    // 全件検索
    @Select("SELECT * FROM MEMBER")
    List<Member> findAll();
}
