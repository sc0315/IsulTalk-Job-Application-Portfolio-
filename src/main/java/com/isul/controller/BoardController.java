package com.isul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.isul.board.BoardService;
import com.isul.dto.BoardDTO;

@Controller
@RequestMapping("/main/")
@SessionAttributes("board")
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
	
	@GetMapping("/getBoardForm")
	public String getBoard(BoardDTO boardDTO, Model model, @RequestParam("board_number") String board_number) {
		BoardDTO board = boardService.getBoard(board_number);
		model.addAttribute("board", board);
		System.out.println(board);
		return "board/getBoard";
	}
	
	@GetMapping("/insertBoardForm")
	public String insertBoardView(BoardDTO boardDTO, Model model) {
		model.addAttribute("board", boardDTO);
		System.out.println(boardDTO);
		return "board/insertBoard";
	}
	 
}
