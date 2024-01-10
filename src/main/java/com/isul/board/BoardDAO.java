package com.isul.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.BoardDTO;

import utils.Criteria;

@Mapper
@Repository
public interface BoardDAO {

	public List<BoardDTO> getBoardList(Criteria cri, String condition, String keyword);
	public BoardDTO getBoard(String board_number);
	public void insertBoard(BoardDTO boardDTO);
	public void updateBoard(BoardDTO boardDTO);
	public void deleteBoard(BoardDTO boardDTO);
	int totalCnt(String condition, String keyword);
	public void updateReplyCount(String board_number);
	public List<BoardDTO> searchBoard(String condition, String keyword);
}
