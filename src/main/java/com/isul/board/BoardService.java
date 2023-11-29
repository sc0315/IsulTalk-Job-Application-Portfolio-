package com.isul.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.BoardDTO;

@Service
public class BoardService {

	@Autowired
	public BoardDAO boardDAO;
	
	public List<BoardDTO> getBoardList(BoardDTO boardDTO){
		return boardDAO.getBoardList(boardDTO);
	}


}
