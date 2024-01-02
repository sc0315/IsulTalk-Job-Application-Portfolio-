package com.isul.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.ReplyDTO;

@Service
public class ReplyService {
	@Autowired
	private ReplyDAO replyDAO;
	
	public List<ReplyDTO> getReplyList(String board_number){
		return replyDAO.getReplyList(board_number);
	}
	
	public String countReply(String board_number) {
		return replyDAO.countReply(board_number);
	}
	
	public void insertReply(ReplyDTO replyDTO) {
		replyDAO.insertReply(replyDTO);
	}
	
	public ReplyDTO getReply(String reply_number) {
		return replyDAO.getReply(reply_number);
	}
	
	public void deleteReply(String reply_number) {
		replyDAO.deleteReply(reply_number);
	}
	
	public void reinsertReply(ReplyDTO replyDTO) {
		replyDAO.reinsertReply(replyDTO);
	}
	
	public ReplyDTO replyRef(String replyRef) {
		return replyDAO.replyRef(replyRef);
	}
	public int maxLevel() {
		return replyDAO.maxLevel();
	}
	public void updateLevel(ReplyDTO replyDTO) {
		replyDAO.updateLevel(replyDTO);
	}
}
