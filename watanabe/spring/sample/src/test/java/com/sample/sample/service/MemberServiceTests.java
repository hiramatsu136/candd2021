package com.sample.sample.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.form.MemberForm;
import com.sample.sample.form.MemberUpdForm;
import com.sample.sample.mapper.MemberMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberMapper memberMapper;

    @Captor
    ArgumentCaptor<Member> aCaptor;

    private Member member;

    @BeforeEach
    void setUp() {
        Date now = new Date();
        member = new Member();
        member.setMail_address("1234@aaa.com");
        member.setName("test_name");
        member.setCreated_date(now);
        member.setCreated_user("created_user");
        member.setUpdated_date(now);
        member.setUpdated_user("updated_user");
    }

    @DisplayName("searchAllをテストする")
    @Test
    public void test_01() {
        // mock set
        List<Member> members = new ArrayList<Member>();
        members.add(member);
        when(memberMapper.findAll()).thenReturn(members);
        // 実行
        List<Member> actualList = memberService.searchAll();
        // 返却値の検証
        assertArrayEquals(members.toArray(), actualList.toArray());
    }

    @DisplayName("searchByMailAddressをテストする")
    @Test
    public void test_02() {
        // mock set
        when(memberMapper.findByKey("1234@aaa.com")).thenReturn(member);
        // 実行
        Member actual = memberService.searchByMailAddress("1234@aaa.com");
        // 返却値の検証
        assertEquals(member, actual);
    }

    @DisplayName("updateByMailAddressをテストする")
    @Test
    public void test_03() {
        try {
            // mapper mock set
            when(memberMapper.update(eq("12345@aaa.com"), eq("test"), any(Date.class), eq("system_upd"),
                    eq("1234@aaa.com"))).thenReturn(1);

            // 実行
            MemberUpdForm form = new MemberUpdForm();
            form.setMailAddress("12345@aaa.com");
            form.setName("test");
            form.setBeforeMailAddress("1234@aaa.com");
            memberService.updateByMailAddress(form);

            // memberMapper.updateが呼び出されたことを確認
            Mockito.verify(memberMapper, times(1)).update(eq("12345@aaa.com"), eq("test"), any(Date.class),
                    eq("system_upd"), eq("1234@aaa.com"));
        } catch (Exception e) {
            // dateのformat変換でエラー
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("updateByMailAddress 異常系をテストする")
    @Test
    public void test_04() {
        try {
            // mapper mock set
            when(memberMapper.update(eq("12345@aaa.com"), eq("test"), any(Date.class), eq("system_upd"),
                    eq("1234@aaa.com"))).thenThrow(new RuntimeException("test"));

            // 実行
            MemberUpdForm form = new MemberUpdForm();
            form.setMailAddress("12345@aaa.com");
            form.setName("test");
            form.setBeforeMailAddress("1234@aaa.com");
            memberService.updateByMailAddress(form);
        } catch (RuntimeException e) {
            assertEquals("test", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("deleteByMailAddressをテストする")
    @Test
    public void test_05() {
        // mock set
        doNothing().when(memberMapper).delete("1234@aaa.com");
        // 実行
        memberService.deleteByMailAddress("1234@aaa.com");
        // deleteが呼び出されたことを確認
        Mockito.verify(memberMapper, times(1)).delete("1234@aaa.com");
    }

    @DisplayName("deleteByMailAddress 異常系をテストする")
    @Test
    public void test_06() {
        try {
            // mock set
            doThrow(new RuntimeException("test")).when(memberMapper).delete("1234@aaa.com");
            // 実行
            memberService.deleteByMailAddress("1234@aaa.com");
        } catch (Exception e) {
            assertEquals("test", e.getMessage());
        }
    }

    @DisplayName("createをテストする")
    @Test
    public void test_07() {
        try {
            // mapper mock set
            // 引数をキャプチャする
            doNothing().when(memberMapper).insert(aCaptor.capture());

            // 実行
            MemberForm memberForm = new MemberForm();
            memberForm.setMailAddress(member.getMail_address());
            memberForm.setName(member.getName());
            memberService.create(memberForm);

            // memberMapper.updateが呼び出されたことを確認
            Mockito.verify(memberMapper, times(1)).insert(aCaptor.capture());
            // mockの引数を確認
            Member actual = aCaptor.getValue();
            assertEquals(member.getMail_address(), actual.getMail_address());
            assertEquals(member.getName(), actual.getName());
            assertEquals("system_ins", actual.getCreated_user());
            assertEquals("system_upd", actual.getUpdated_user());
            assertNotNull(actual.getCreated_date());
            assertNotNull(actual.getUpdated_date());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("create 異常系をテストする")
    @Test
    public void test_08() {
        try {
            // mapper mock set
            doThrow(new RuntimeException("test")).when(memberMapper).insert(any(Member.class));

            // 実行
            MemberForm memberForm = new MemberForm();
            memberForm.setMailAddress(member.getMail_address());
            memberForm.setName(member.getName());
            memberService.create(memberForm);

        } catch (RuntimeException e) {
            assertEquals("test", e.getMessage());
        } catch (Exception e) {
            // 予期せぬエラー
            e.printStackTrace();
            fail();
        }
    }
}
