package com.sample.sample.mapper;

import java.util.Date;
import java.util.List;

import com.sample.sample.entity.Member;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * member情報 Mapper
 */
@Mapper
public interface MemberMapper {
    /**
     * 全件検索
     * 
     * @return member list
     */
    @Select("SELECT * FROM MEMBER")
    List<Member> findAll();

    /**
     * 主キー検索
     * 
     * @param mail_address メールアドレス
     * @return member
     */
    @Select("SELECT * FROM MEMBER WHERE MAIL_ADDRESS = #{mail_address}")
    Member findByKey(String mail_address);

    /**
     * アップデート（主キー指定）
     * 
     * @param name         名前
     * @param updated_date 更新日時
     * @param updated_user 更新者
     * @param mail_address メールアドレス
     */
    @Update("UPDATE MEMBER SET MAIL_ADDRESS = #{mail_address}, NAME = #{name}, UPDATED_DATE = #{updated_date}, UPDATED_USER = #{updated_user} WHERE MAIL_ADDRESS = #{key}")
    int update(String mail_address, String name, Date updated_date, String updated_user, String key);

    /**
     * 削除（主キー指定）
     * 
     * @param mail_address メールアドレス
     */
    @Delete("DELETE FROM MEMBER WHERE MAIL_ADDRESS = #{mail_address}")
    void delete(String mail_address);

    /**
     * 新規登録
     * 
     * @param member member 登録情報
     */
    @Insert("INSERT INTO MEMBER VALUES(#{mail_address},#{name},#{created_date},#{created_user},#{updated_date},#{updated_user})")
    void insert(Member member);
}
