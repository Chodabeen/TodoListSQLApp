package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private Date date;
    private int is_completed;
    

    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        this.date=new Date();
        this.id  = 0;
        this.is_completed = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    	current_date = format.format(date);
    }
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
    	return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	public int getIs_completed() {
		return is_completed;
	}


	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}


	@Override
	public String toString() {
		return id + " [" + category + "] " + title + " - " + desc + " - [" + due_date + "] - "  + current_date + "\n";
	}


	public String toSaveString() {
		return category + "##" + title + "##" + desc + "##" + due_date + "##" +current_date + "\n";
	}
   
}
