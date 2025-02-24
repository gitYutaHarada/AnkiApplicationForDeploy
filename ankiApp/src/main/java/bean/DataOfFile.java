package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataOfFile implements Serializable{
	public String fileName;
	public int maxId = 0;
	public int minId = 0;
	public HashMap<Integer, String> question = new HashMap<>();
	public HashMap<Integer, String> answer = new HashMap<>();

	
	public DataOfFile() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HashMap<Integer, String> getQuestionMap() {
		return this.question;
	}
	public HashMap<Integer, String> getAnswerMap() {
		return this.answer;
	}

	public void setQuestionMap(HashMap<Integer, String> question) {
		this.question = question;
	}
	public void setAnswerMap(HashMap<Integer, String> answer) {
		this.answer = answer;
	}
	
	public void setElement(int id, String question, String answer) {
		this.question.put(id, question);
		this.answer.put(id, answer);
	}
	
	public String getAnswerByQuestion(String question) {
		int id = 0;
		for(Map.Entry<Integer, String> entry : this.question.entrySet()) {
			if(entry.getValue().equals(question)) {
				id = entry.getKey();
			}
		}
		return this.answer.get(id);
	}
	public String getQuestionByAnswer(String answer) {
		int id = 0;
		for(Map.Entry<Integer, String> entry : this.answer.entrySet()) {
			if(entry.getValue().equals(answer)) {
				id = entry.getKey();
			}
		}
		return this.question.get(id);
	}
	
	public String getAnswerById(int id) {
		return this.answer.get(id);
	}
	public String getQuestionById(int id) {
		return this.question.get(id);
	}
	
	public boolean isElement(int id) {
		return this.question.containsKey(id);
	}
	public int getDataOfFileSize() {
		return this.question.size();
	}

	public void setMaxId(int dataOfFileMax) {
		this.maxId = dataOfFileMax;
	}
	
	public int getMaxId() {
		return this.maxId;
	}
	
	public int getMinId() {
		return this.minId;
	}

	public void setMinId(int minId) {
		this.minId = minId;
	}
	
	public int getIdByQuestion(String question) {
		int id = 0;
		for(Map.Entry<Integer, String> entry : this.question.entrySet()) {
			if(entry.getValue().equals(question)) {
				id = entry.getKey();
			}
		}
		return id;
	}
	public int getIdByAnswer(String answer) {
		int id = 0;
		for(Map.Entry<Integer, String> entry : this.answer.entrySet()) {
			if(entry.getValue().equals(answer)) {
				id = entry.getKey();
			}
		}
		return id;
	}

	public void removeElementById(int id) {
		this.question.remove(id);
		this.answer.remove(id);
	}
	
	public void editElement(int id, String edit_question, String edit_answer) {
		this.question.put(id, edit_question);
		this.answer.put(id, edit_answer);
	}
}
