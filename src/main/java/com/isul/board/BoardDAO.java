package com.isul.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.BoardDTO;

@Mapper
@Repository
public interface BoardDAO {

	List<BoardDTO> getBoardList(BoardDTO boardDTO);
	
}
