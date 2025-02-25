package bean;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInformationBean implements Serializable {
    private ArrayList<UserBean> userBeanList = new ArrayList<>();

    public UserInformationBean(){
    	userBeanList = new ArrayList<UserBean>();
    }

    public void add(UserBean user_bean){
    	userBeanList.add(user_bean);
    }

    public UserBean get(int i){
        return userBeanList.get(i);
    }

    public int size(){
        return userBeanList.size();
    }
}

