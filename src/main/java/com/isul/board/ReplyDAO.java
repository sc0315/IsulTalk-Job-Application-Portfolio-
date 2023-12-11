package com.isul.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.ReplyDTO;

@Mapper
@Repository
public interface ReplyDAO {
	public List<ReplyDTO> getReplyList(String board_number);
	public String countReply(String board_number);
	public void insertReply(ReplyDTO replyDTO);
	public String getReply(String reply_number);
	public void deleteReply(String reply_number);
}
