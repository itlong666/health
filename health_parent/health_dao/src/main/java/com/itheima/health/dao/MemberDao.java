package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {

    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String m);

    Integer findMemberCountByDate(String today);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String thisWeekMonday);
}
