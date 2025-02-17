package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserBean implements Serializable {
    private int no;
    private String name;
    private String password;
    private List<String> fileNames = new ArrayList<>();;


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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNamesList(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public String getFileName(int i) {
		return fileNames.get(i);
	}

	public boolean removeFile(String fileName) {
		boolean isRemove = fileNames.remove(fileName);
		return isRemove;
	}
	
	public boolean addFile(String fileName) {
		boolean isAdd = fileNames.add(fileName);
		return isAdd;
	}
	
	public int getFileNamesSize() {
		return fileNames.size();
	}

}
