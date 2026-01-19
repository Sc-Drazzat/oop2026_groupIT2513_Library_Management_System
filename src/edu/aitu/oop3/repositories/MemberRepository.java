package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Member;
import  java.util.List;

public interface MemberRepository {
    Member findById(int id);
    List<Member> findAll();
    void addMember(Member member);
    void updateMember(Member member);
    void deleteMember(Member member);
}