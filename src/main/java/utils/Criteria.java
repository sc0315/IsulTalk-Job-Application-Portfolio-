package utils;

//현재 페이지와 관련된 정보 저장 클래스 
public class Criteria {
	private int pageNum;			//현재 페이지 번호
	private int amount;	// 페이지당 게시글의 수
	
	private String condition;
	private String keyword;
	

	public Criteria() {
		this(1,  10); // 기본 생성자 호출 시 : 페이지 번호 = 1, 페이지당 게시글 수 = 10
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum <= 0)
			this.pageNum = 1;
		else 
			this.pageNum = pageNum;
	}

	public int getamount() {
		return amount;
	}

	//페이지당 게시글 수 제한 1~20
	public void setamount(int amount) {
		if(amount <= 0 || amount > 20) 
			this.amount = 20;
		else
			this.amount = amount;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + "]";
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
