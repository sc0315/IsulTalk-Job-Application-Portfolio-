package com.isul.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.BoardDTO;

import utils.Criteria;

@Service
public class BoardService {

	@Autowired
	public BoardDAO boardDAO;
	
	public List<BoardDTO> getBoardList(Criteria cri, String condition, String keyword){
		return boardDAO.getBoardList(cri, condition, keyword);
	}
	
	public BoardDTO getBoard(String board_number) {
		return boardDAO.getBoard(board_number);
	}
	
	public void insertBoard(BoardDTO boardDTO) {
		boardDAO.insertBoard(boardDTO);
	}
	
	public void updateBoard(BoardDTO boardDTO) {
		boardDAO.updateBoard(boardDTO);
	}

	public void deleteBoard(BoardDTO boardDTO) {
		boardDAO.deleteBoard(boardDTO);
	}
	public int totalCnt(String condition, String keyword) {
		return boardDAO.totalCnt(condition, keyword);
	}
	public void updateReplyCount(String board_number) {
		 boardDAO.updateReplyCount(board_number);
	}
	
	public List<BoardDTO> searchBoard(String condition, String keyword){
		return boardDAO.searchBoard(condition, keyword);
	}
}
