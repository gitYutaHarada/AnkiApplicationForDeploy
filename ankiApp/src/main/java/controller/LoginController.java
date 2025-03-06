package controller;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.UserBean;
import dataAccessObject.FileDAO;
import dataAccessObject.UserDAO;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
        UserDAO userDao = new UserDAO();
        
        if("login".equals(action)) {
	    	String name = request.getParameter("name");
	    	String pass = request.getParameter("pass");
	    	//nameとおなじ行のpassを探す
	    	//そのpassとinputPassがtrueの場合ログインできるようにする
	    	if(userDao.isLogin(name, pass)) {
	        	request.setAttribute("name", name);
	        	request.setAttribute("pass", pass);
	        	int userId = userDao.getUserId(name);
	        	
	        	HttpSession session = request.getSession(true);
	        	UserBean userBean = new UserBean();
	    		FileDAO fileDao = new FileDAO();
	    		HashMap <Integer, String> fileNamesList = fileDao.getAllFileName(userId);
	    		
	    		userBean.setUserId(userId);
	    		userBean.setName(name);
	    		userBean.setPassword(pass);
	    		userBean.setFileNamesMap(fileNamesList);
	    		
	    		session.setAttribute("userBean", userBean);
	    		
	    		RequestDispatcher requestdispatcher = request.getRequestDispatcher("myPage.jsp");
	    		requestdispatcher.forward(request, response);
    		}else{
            	String miss = "miss";
            	request.setAttribute("miss", miss);

            	RequestDispatcher requestdispatcher = request.getRequestDispatcher("index.jsp");
        		requestdispatcher.forward(request, response);
            }
        }else if("logout".equals(action)){
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        	    session.invalidate();
        	}
        	RequestDispatcher requestdispatcher = request.getRequestDispatcher("index.jsp");
    		requestdispatcher.forward(request, response);
        } 

		
		
		
	}

}
