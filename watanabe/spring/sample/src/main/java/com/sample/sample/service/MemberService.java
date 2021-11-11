package com.sample.sample.service;

import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * member情報 Service
 */
@Service
public class MemberService{
    /**
     * member情報 Mapper
     */
    @Autowired
    private MemberMapper memberMapper;
    
    /**
     * 全件検索
     * @return member list
     */
    public List<Member> searchAll() {
        return memberMapper.findAll();
    }
}
