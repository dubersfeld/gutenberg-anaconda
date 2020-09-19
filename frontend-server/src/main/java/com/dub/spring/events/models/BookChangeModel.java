package com.dub.spring.events.models;


public class BookChangeModel {

	private String type;
    private String action;
    private String bookId;
    
    public  BookChangeModel() {
    	
    }
    
    public  BookChangeModel(String type, String action, String bookId) {
        super();
        this.type   = type;
        this.action = action;
        this.bookId = bookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}
