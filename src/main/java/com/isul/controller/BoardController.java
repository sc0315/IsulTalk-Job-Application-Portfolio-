package com.isul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isul.board.BoardService;
import com.isul.dto.BoardDTO;

@Controller
@RequestMapping("/main/")
public class BoardController {
	
	@Autowired public BoardService boardService;
	
	@GetMapping("/csboard") 
	public String csBoardView(BoardDTO boardDTO, Model model){ 
	  System.out.println("게시판 열기");
	 
	  List<BoardDTO> boardList = boardService.getBoardList(boardDTO);
	  model.addAttribute("boardList", boardList);
	  System.out.println("보드리스트 열기"+boardList);
		 
	  return "board/csboard";
	}
	 
}
