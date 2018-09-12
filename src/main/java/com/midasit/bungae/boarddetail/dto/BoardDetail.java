package com.midasit.bungae.boarddetail.dto;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.user.dto.User;

import java.util.ArrayList;
import java.util.List;

public class BoardDetail {
    private Board board;
    private List<User> participants = new ArrayList();

    public BoardDetail() { }

    public BoardDetail(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }
}
