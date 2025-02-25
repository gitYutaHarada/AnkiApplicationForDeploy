package utils;

import bean.DataOfFile;

public class StringUtils {
	
	public StringUtils(){
	}
	public boolean isValidString(String str) {
		return str.matches("[a-zA-Z0-9]+");
	}
	
	public boolean isDataOfFile(DataOfFile dataOfFile, int id) {
		boolean isFileOfData = false;
		if(dataOfFile.getQuestionMap().containsKey(id)) isFileOfData = true;
		return isFileOfData;
	}
	
	public int backOrNextId(DataOfFile dataOfFile, String backOrNext, int id) {
		if("back".equals(backOrNext)) {
			//idが最小ではなくそのidが存在するときwhile文を抜ける
			id--;
			while(!isDataOfFile(dataOfFile, id) && id != dataOfFile.getMinId()) {
				id--;
			}
			return id;
		}else {
			id++;
			while(isDataOfFile(dataOfFile, id) && id != dataOfFile.getMaxId()) {
				id++;
			}
			return id;
		}
	}
	
	public boolean isEmptyOrSpace(String str) {
		return str == null || str.trim().isEmpty();
	}
	
}
