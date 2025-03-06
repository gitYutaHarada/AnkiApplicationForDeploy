package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataOfFile implements Serializable{
	private int fileId;
	private String fileName;
	private int maxId = 0;
	private int minId = 0;
	private HashMap<Integer, String> question = new HashMap<>();
	private HashMap<Integer, String> answer = new HashMap<>();

	
	public DataOfFile() {
	}
	
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
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
		if(this.question.isEmpty() && this.answer.isEmpty()) setMinId(id);
		this.question.put(id, question);
		this.answer.put(id, answer);
		setMaxId(id);
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

	public void setMaxId(int maxId) {
		this.maxId = maxId;
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
	
	public void editElement(int id, String editQuestion, String editAnswer) {
		this.question.put(id, editQuestion);
		this.answer.put(id, editAnswer);
	}
}
