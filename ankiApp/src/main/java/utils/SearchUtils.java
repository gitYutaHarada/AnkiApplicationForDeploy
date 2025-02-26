package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.DataOfFile;

public class SearchUtils {
	
	public List<Integer> searchWord(DataOfFile dataOfFile, String searchWord) {
		HashMap<Integer, String> question = dataOfFile.getQuestionMap();
		HashMap<Integer, String> answer = dataOfFile.getAnswerMap();
		List<Integer> searchIds = new ArrayList<>();
		
		for(int id = 1 ; id <= dataOfFile.getMaxId(); id++ ) {
			if(question.get(id).contains(searchWord) || answer.get(id).contains(searchWord)) {
				searchIds.add(id);
			}
		}
		return searchIds;
	}
}
