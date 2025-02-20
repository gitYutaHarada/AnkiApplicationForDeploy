package utils;

import bean.FileOfData;

public class Utils {
	public boolean isValidString(String str) {
		return str.matches("[a-zA-Z0-9]+");
	}
	
	public boolean isFileOfData(FileOfData fileofdata, int id) {
		boolean isfileofdata = false;
		if(fileofdata.getQuestionMap().containsKey(id)) isfileofdata = true;
		return isfileofdata;
	}
	public int backOrNextId(FileOfData fileofdata, String backOrNext, int id) {
		if("back".equals(backOrNext)) {
			while(isFileOfData(fileofdata, id) && id != fileofdata.getMinId()) {
				id--;
			}
			return id;
		}else {
			while(isFileOfData(fileofdata, id) && id != fileofdata.getMaxId()) {
				id++;
			}
			return id;
		}
	}
	
}
