package com.sample.sample.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sample.sample.entity.Member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
public class MemberMapperTests {
  @Autowired
  MemberMapper memberMapper;

  @DisplayName("findAllをテストする")
  @Test
  public void test_01() {
    // findAll実行
    List<Member> members = memberMapper.findAll();

    // 検証 起動時のデータ2件が取得できていること
    assertEquals(members.size(), 2);
  }

  @DisplayName("insert後findByKeyを実行し、両メソッドをテストする")
  @Test
  public void test_02() {
    // 準備
    Date now = new Date();
    Member member = new Member();
    member.setMail_address("mail_address");
    member.setName("name");
    member.setCreated_user("created_user");
    member.setCreated_date(now);
    member.setUpdated_user("updated_user");
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, 1);
    date = calendar.getTime();
    member.setUpdated_date(date);
    // insert実行
    memberMapper.insert(member);

    // findByKey実行
    final Member result = memberMapper.findByKey("mail_address");
    // 検証 insertしたデータが取得できること
    assertEquals(result.getMail_address(), member.getMail_address());
    assertEquals(result.getName(), member.getName());
    assertEquals(result.getCreated_date(), member.getCreated_date());
    assertEquals(result.getCreated_user(), member.getCreated_user());
    assertEquals(result.getUpdated_date(), member.getUpdated_date());
    assertEquals(result.getUpdated_user(), member.getUpdated_user());
  }

  @DisplayName("updateをテストする")
  @Test
  public void test_03() throws ParseException {
    Date now = new Date();
    // 実行前 データが存在することを確認
    final Member result0 = memberMapper.findByKey("xxxx2@aaa.co.jp");
    assertNotNull(result0);

    // update実行
    memberMapper.update("upd_test@aaa.com", "upd_name", now, "test_user", "xxxx2@aaa.co.jp");

    // 検証 更新できていること
    // findByKey実行
    final Member result1 = memberMapper.findByKey("xxxx2@aaa.co.jp");
    final Member result2 = memberMapper.findByKey("upd_test@aaa.com");
    // 変更前主キーでは検索結果なしになること
    assertNull(result1);
    // 変更後検証
    assertEquals(result2.getMail_address(), "upd_test@aaa.com");
    assertEquals(result2.getName(), "upd_name");
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date dateTime = sdformat.parse("2021-11-16 01:00:00");
    assertEquals(result2.getCreated_date(), dateTime);
    assertEquals(result2.getCreated_user(), "ins_user2");
    assertEquals(result2.getUpdated_date(), now);
    assertEquals(result2.getUpdated_user(), "test_user");
  }

  @DisplayName("deleteをテストする")
  @Test
  public void test_04() {
    // 実行前確認
    final Member result1 = memberMapper.findByKey("xxxx1@aaa.co.jp");
    assertNotNull(result1);

    // delete実行
    memberMapper.delete("xxxx1@aaa.co.jp");
    // 検証 削除されていること
    final Member result2 = memberMapper.findByKey("xxxx1@aaa.co.jp");
    assertNull(result2);
  }
}
