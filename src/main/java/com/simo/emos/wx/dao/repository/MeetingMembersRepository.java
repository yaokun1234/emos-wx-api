package com.simo.emos.wx.dao.repository;


import com.simo.emos.wx.dao.entity.MeetingMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingMembersRepository extends JpaRepository<MeetingMembers, String> {

    List<MeetingMembers> findAllByMeetingId(String memberId);
    List<MeetingMembers> findAllByMemberId(String memberId);

    void deleteAllByMeetingId(String meetingId);
}
