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

import com.isul.board.BoardService;
import com.isul.board.ReplyService;
import com.isul.dto.BoardDTO;
import com.isul.dto.ReplyDTO;

import utils.Criteria;
import utils.PageMaker;

@Controller
@RequestMapping("/main/")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/insertReply")
	public String insertReply(ReplyDTO replyDTO, HttpSession session, Model model, Criteria cri, @RequestParam("board_number") String board_number){
		System.out.println("insertReply controller");
		String id = (String) session.getAttribute("loginId");		
		
		replyDTO.setReply_writer(id);	
		replyService.insertReply(replyDTO);
	
		List<BoardDTO> boardList = boardService.getBoardList(cri);
		int total = boardService.totalCnt();
		PageMaker pageMaker = new PageMaker(cri, total);

		model.addAttribute("boardList", boardList);
		model.addAttribute("paging", pageMaker);
		System.out.println("인설트 끝");
		return "redirect:/main/getBoardForm?"+ "board_number="+board_number;
	}
	
	@GetMapping("/deleteReply")
	public String deleteReply(HttpSession session, @RequestParam("reply_number") String reply_number){
		String board_number = (String)session.getAttribute("board_number");
		
		replyService.deleteReply(reply_number);
		
		return "redirect:/main/getBoardForm?"+ "board_number="+board_number;
	}
	
	@PostMapping("/reinsertReply")
	public String reinsertReply(ReplyDTO replyDTO, HttpSession session, Model model, Criteria cri, @RequestParam("board_number") String board_number
			){
		System.out.println("대댓글insertReply controller");
		String id = (String) session.getAttribute("loginId");
		
		replyDTO.setReply_writer(id);
		
		replyService.reinsertReply(replyDTO);
		System.out.println(replyDTO.getReply_level());
		List<BoardDTO> boardList = boardService.getBoardList(cri);
		int total = boardService.totalCnt();
		PageMaker pageMaker = new PageMaker(cri, total);

		model.addAttribute("boardList", boardList);
		model.addAttribute("paging", pageMaker);
		System.out.println("대댓글인설트 끝");
		return "redirect:/main/getBoardForm?"+ "board_number="+board_number;
	}
}
