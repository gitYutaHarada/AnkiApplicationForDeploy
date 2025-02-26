package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.DataOfFile;

public class SearchUtils {
	
	public List<Integer> searchWord(DataOfFile dataOfFile, String searchWord) {
		HashMap<Integer, String> questions = dataOfFile.getQuestionMap();
		HashMap<Integer, String> answers = dataOfFile.getAnswerMap();
		List<Integer> searchIds = new ArrayList<>();
		
		for(Map.Entry<Integer, String> question : questions.entrySet()) {
			if(question.getValue().contains(searchWord)) {
				searchIds.add(question.getKey());
			}
		}
		for(Map.Entry<Integer, String> answer : answers.entrySet()) {
			if(answer.getValue().contains(searchWord) && !searchIds.contains(answer.getKey())) {
				searchIds.add(answer.getKey());
			}
		}
		return searchIds;
	}
}
