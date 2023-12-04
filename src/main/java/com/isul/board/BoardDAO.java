package com.isul.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.BoardDTO;

@Mapper
@Repository
public interface BoardDAO {

	public List<BoardDTO> getBoardList(BoardDTO boardDTO);
	public BoardDTO getBoard(String board_number);
	public void insertBoard(BoardDTO boardDTO);
	public void updateBoard(BoardDTO boardDTO);
	public void deleteBoard(BoardDTO boardDTO);
	
}
