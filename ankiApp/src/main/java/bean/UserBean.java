package bean;

import java.io.Serializable;
import java.util.HashMap;

public class UserBean implements Serializable {
    private int userId;
    private String name;
    private String password;
    private HashMap<Integer, String> fileNames = new HashMap<>();;


    public UserBean() {
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
        return password;
    }

	public HashMap<Integer, String> getFileNames() {
		return fileNames;
	}

	public void setFileNamesMap(HashMap<Integer, String> fileNames) {
		this.fileNames = fileNames;
	}

	public String getFileName(int fileId) {
		return fileNames.get(fileId);
	}

	public boolean removeFile(int fileId) {
		fileNames.remove(fileId);
		return !fileNames.containsKey(fileId);
	}
	
	public boolean addFile(int fileId, String fileName) {
		fileNames.put(fileId, fileName);
		return fileNames.containsKey(fileId);
	}
	
	public int getFileNamesSize() {
		return fileNames.size();
	}

}
