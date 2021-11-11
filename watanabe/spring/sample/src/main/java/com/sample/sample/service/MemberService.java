package com.sample.sample.service;

import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService{
    @Autowired
    private MemberMapper memberMapper;
    
    public List<Member> searchAll() {
        return memberMapper.findAll();
    }
}
