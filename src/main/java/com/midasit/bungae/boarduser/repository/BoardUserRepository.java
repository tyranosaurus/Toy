package com.midasit.bungae.boarduser.repository;

import java.util.List;

public interface BoardUserRepository {
    int add(int boardNo, int participantNo);
    void deleteBoard(int boardNo);
    void deleteParticipant(int boardNo, int participantNo);
    void deleteAll();
    int getParticipantCount(int boardNo);
    void addParticipant(int boardNo, int participantNo);
    List<Integer> getParticipantNoList(int boardNo);
    int hasParticipant(int boardNo, int participantNo);
}
    