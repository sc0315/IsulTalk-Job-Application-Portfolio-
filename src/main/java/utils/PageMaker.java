package utils;

public class PageMaker {
	
	private int currentPage;	// 현재 페이지
	private int startPage=1; // 시작 페이지
	private int endPage=10;	// 끝 페이지
	private boolean prev;	// 이전
	private boolean next;	// 다음
	
	
	private Criteria cri;
	
	public PageMaker(Criteria cri,int total){
		this.cri=cri;
		int realEnd = (int)Math.ceil(total / (double)cri.getamount());
		this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0) * 10);
		this.startPage = getEndPage()-9;
		
		if( this.endPage > realEnd ) {
			this.endPage = realEnd;
		}
		
		this.setCurrentPage(cri.getPageNum());
		this.next = getEndPage() < realEnd;
		this.prev = (startPage ==1) ? false : true;	
	}
	
	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "PageMaker [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + "]";
	}

	
}