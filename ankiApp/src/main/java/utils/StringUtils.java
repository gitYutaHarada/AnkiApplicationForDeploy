package utils;

import bean.DataOfFile;

public class StringUtils {
	
	public StringUtils(){
	}
	public boolean isValidString(String str) {
		return str.matches("[a-zA-Z0-9]+");
	}
	
	public boolean isDataOfFile(DataOfFile dataoffile, int id) {
		boolean isfileofdata = false;
		if(dataoffile.getQuestionMap().containsKey(id)) isfileofdata = true;
		return isfileofdata;
	}
	
	public int backOrNextId(DataOfFile dataoffile, String backOrNext, int id) {
		if("back".equals(backOrNext)) {
			System.out.println(id +"::" + dataoffile.getMaxId());
			//idが最小ではなくそのidが存在するときwhile文を抜ける
			id--;
			while(!isDataOfFile(dataoffile, id) && id != dataoffile.getMinId()) {
				id--;
			}
			return id;
		}else {
			id++;
			while(isDataOfFile(dataoffile, id) && id != dataoffile.getMaxId()) {
				id++;
			}
			return id;
		}
	}
	
	public boolean isEmptyOrSpace(String str) {
		return str == null || str.trim().isEmpty();
	}
	
}
