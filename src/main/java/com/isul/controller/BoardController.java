package com.isul.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.isul.board.BoardService;
import com.isul.dto.BoardDTO;

@Controller
@RequestMapping("/main/")
@SessionAttributes("board")
public class BoardController {

	@Autowired
	public BoardService boardService;

	@GetMapping("/csboard")
	public String csBoardView(BoardDTO boardDTO, Model model) {

		List<BoardDTO> boardList = boardService.getBoardList(boardDTO);
		model.addAttribute("boardList", boardList);
		
		System.out.println("게시판 열기" + boardList);

		return "board/csboard";
	}

	@GetMapping("/getBoardForm")
	public String getBoard(BoardDTO boardDTO, Model model, @RequestParam("board_number") String board_number,HttpSession session) {
		BoardDTO board = boardService.getBoard(board_number);
		session.setAttribute("board_number", board_number);
		// 글 상세정보 소환
		model.addAttribute("board", board);
		// 수정삭제 권한
		model.addAttribute("loginId", session.getAttribute("loginId"));
		
		System.out.println("상세글 읽기"+board);
		
		return "board/getBoard";
	}

	@GetMapping("/insertBoardForm")
	public String insertBoardView(BoardDTO boardDTO, HttpSession session, Model model) {
		model.addAttribute("loginId", session.getAttribute("loginId"));
		
		System.out.println("글 등록 Id: "+session.getAttribute("loginId"));

		return "board/insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(BoardDTO boardDTO, HttpSession session) {
		String id = (String)session.getAttribute("loginId");
		boardDTO.setBoard_writer(id);
		
		boardService.insertBoard(boardDTO);
		
		System.out.println("글 등록 성공");
		System.out.println(boardDTO);
		
		return "board/csboard";
	}

	@GetMapping("/updateBoardForm")
	public String updateBoardView(BoardDTO boardDTO, HttpSession session, Model model) {
		model.addAttribute("loginId", session.getAttribute("loginId"));
		boardDTO = (BoardDTO) session.getAttribute("board");
		
		System.out.println("글 등록 Id: "+session.getAttribute("loginId"));
		System.out.println(boardDTO);
		System.out.println("글 수정 정보: "+ boardDTO);
		
		return "board/updateBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(BoardDTO boardDTO, HttpSession session) {
		boardDTO.setBoard_number((String)session.getAttribute("board_number"));
		boardService.updateBoard(boardDTO);
		
		System.out.println(boardDTO);
		
		return "board/csBoard";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(BoardDTO boardDTO, HttpSession session) {
		boardDTO.setBoard_number((String)session.getAttribute("board_number"));
		boardService.deleteBoard(boardDTO);
		
		System.out.println("게시판 삭제: "+boardDTO);
		
		return "board/csBoard";
	}
}
