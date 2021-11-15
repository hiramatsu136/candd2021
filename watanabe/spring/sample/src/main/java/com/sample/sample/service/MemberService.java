package com.sample.sample.service;

import java.util.Date;
import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.form.MemberForm;
import com.sample.sample.form.MemberUpdForm;
import com.sample.sample.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * member情報 Service
 */
@Service
public class MemberService {
    /**
     * member情報 Mapper
     */
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 全件検索
     * 
     * @return member list
     */
    public List<Member> searchAll() {
        return memberMapper.findAll();
    }

    /**
     * メールアドレス検索
     * 
     * @param mailAddress メールアドレス
     * @return member
     */
    public Member searchByMailAddress(String mailAddress) {
        return memberMapper.findByKey(mailAddress);
    }

    /**
     * member更新
     * 
     * @param memberUpdForm member update request情報
     */
    public void updateByMailAddress(MemberUpdForm memberUpdForm) {
        try {
            Date now = new Date();
            // 更新
            memberMapper.update(memberUpdForm.getMailAddress(), memberUpdForm.getName(), now, "system_upd",
                    memberUpdForm.getBeforeMailAddress());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * member削除
     * 
     * @param mailAddress メールアドレス
     */
    public void deleteByMailAddress(String mailAddress) {
        try {
            // 削除
            memberMapper.delete(mailAddress);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 新規登録
     * 
     * @param memberForm member request情報
     */
    public void create(MemberForm memberForm) {
        try {
            Date now = new Date();
            Member member = new Member();
            member.setMail_address(memberForm.getMailAddress());
            member.setName(memberForm.getName());
            member.setCreated_date(now);
            member.setCreated_user("sysmtem_ins");
            member.setUpdated_date(now);
            member.setUpdated_user("system_upd");
            memberMapper.insert(member);
        } catch (Exception e) {
            throw e;
        }
    }
}
