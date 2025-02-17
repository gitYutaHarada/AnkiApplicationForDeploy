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
			do {
				id--;
				System.out.println(id);
			}while(isFileOfData(fileofdata, id) && id != fileofdata.getMinId());
			return id;
			
		}else {
			while(isFileOfData(fileofdata, id) && id != fileofdata.getMaxId()) {
				id++;
				System.out.println(id);
			}

			System.out.println(isFileOfData(fileofdata, id));
			System.out.println(fileofdata.getMaxId());
			System.out.println(id);

			return id;
		}

	}
}
