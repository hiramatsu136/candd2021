package com.sample.sample.mapper;

import java.util.List;

import com.sample.sample.entity.Member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * member情報 Mapper
 */
@Mapper
public interface MemberMapper {
    /**
     * 全件検索
     * @return member list
     */
    @Select("SELECT * FROM MEMBER")
    List<Member> findAll();
}
