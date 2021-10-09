package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, cate, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 추가>\n" + "카테고리 : ");
		cate = sc.nextLine();
		
		System.out.print("제목: ");
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("중복되는 제목입니다!");
			return;
		}
		System.out.print("내용 : ");
		desc = sc.nextLine().trim();
	
		System.out.print("마감날짜(yyyy/MM/dd): ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, cate, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 삭제>\n" + "삭제할 항목의 번호 : ");
		int num = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (num-1 == l.indexOf(item)) {
				System.out.println(num + ". [" + item.getCategory() + "] - " +item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				System.out.print("위 항목을 삭제하시겠습니까? (y/n) ");
				char answer = sc.next().charAt(0);
				if(answer == 'y') {
					l.deleteItem(item);
					System.out.println("삭제되었습니다");
					break;
				}
				System.out.println("취소되었습니다 ");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 수정>\n" + "수정할 항목의 번호 : ");
		int num = sc.nextInt();
		if (num > (l.getList().size())+1 || num < 1) {
			System.out.println("존재하지 않는 항목입니다!");
			return;
		}
		
		sc.nextLine();
		System.out.print("새 카테고리 : ");
		String new_category = sc.nextLine();

		System.out.print("세 제목 : ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복되는 제목입니다!");
			return;
		}
	
		System.out.print("새 내용 : ");
		String new_description = sc.nextLine().trim();
		System.out.print("새 마감날짜(yyyy/MM/dd) : ");
		String new_due_date = sc.nextLine().trim();
		
		for (TodoItem item : l.getList()) {
			if (num-1 == l.indexOf(item)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다");
				break;
			}
		}

	}
	
	public static void find(TodoList l, String s) {
		int num,count = 0;
		String tit, des;
		
		for (TodoItem item : l.getList()) {
			num = l.indexOf(item) + 1;
			tit = item.getTitle();
			des = item.getDesc();
			if(tit.contains(s) || des.contains(s)) {
				count++;
				System.out.print(num + ". ");
				System.out.println("[" + item.getCategory() + "] - " +item.getTitle() + " - " + item.getDesc() + " - (" + item.getDue_date() + ") - " + item.getCurrent_date());
			}	
		}
		if(count == 0)
			System.out.println("해당 검색어를 포함한 항목이 없습니다");
		else
			System.out.println("총 " + count + "개의 항목을 찾았습니다");
				
	}
	
	public static void find_cate(TodoList l, String str) {
		int num,count = 0;
		String cate;
		
		for (TodoItem item : l.getList()) {
			num = l.indexOf(item) + 1;
			cate = item.getCategory();
			if(cate.contains(str)) {
				count++;
				System.out.print(num + ". ");
				System.out.println("[" + item.getCategory() + "] - " +item.getTitle() + " - " + item.getDesc() + " - (" + item.getDue_date() + ") - " + item.getCurrent_date());
			}	
		}
		if(count == 0)
			System.out.println("해당 검색어를 포함한 항목이 없습니다");
		else
			System.out.println("총 " + count + "개의 항목을 찾았습니다");
	}
	
	public static void listCateAll(TodoList l) {
		Set<String> clist = new HashSet<String>();
		
		for(TodoItem c : l.getList()) {
			clist.add(c.getCategory());
		}
		
		Iterator it = clist.iterator();
		while(it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if(it.hasNext()) 
				System.out.print(" / ");
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", clist.size());
	}

	public static void listAll(TodoList l) {
		int num = 0;
		int i = l.getList().size();
		System.out.println("[총 " + i + "개의 목록]");
	
		for (TodoItem item : l.getList()) {
			num = l.indexOf(item) + 1;
			System.out.print(num + ". ");
			System.out.println("[" + item.getCategory() + "] - " +item.getTitle() + " - " + item.getDesc() + " - (" + item.getDue_date() + ") - " + item.getCurrent_date());
		}
	}
	 
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();	
			System.out.println("모든 데이터가 저장되었습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int count = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();				
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, description, category, due_date);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count + "개의 항목을 읽었습니다 ");
		} catch (FileNotFoundException e) {
			System.out.println(filename + " 파일이 없습니다 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
