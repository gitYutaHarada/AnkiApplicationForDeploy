package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.UserInformationBean;
import dataAccessObject.UserDAO;
import utils.PasswordUtils;
import utils.StringUtils;

/**
 * Servlet implementation class CreateSuccessController
 */
@WebServlet("/CreateSuccessController")
public class CreateSuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSuccessController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		
		PasswordUtils passwordUtils = new PasswordUtils();
        StringUtils stringUtils = new StringUtils();
		int isValidStringName = (stringUtils.isValidString(name)) ? 1 : 0;
		int isValidStringPass = (stringUtils.isValidString(pass)) ? 1 : 0;
		
		//名前とパスワードが英数字のみなら１を代入して新しいテーブルを作成
        if(isValidStringName == 1 && isValidStringPass == 1) {
    		UserDAO userDao = new UserDAO();
    		//ここでパスワードハッシュ化
    		String hashPass = passwordUtils.hashPass(pass);
            int isSuccessInsert = userDao.createUser(name, hashPass);
            UserInformationBean userInfoDto = userDao.select();
            request.setAttribute("userInfoDto", userInfoDto);
            
          	request.setAttribute("isSuccessInsert", isSuccessInsert);
          	request.setAttribute("isValidStringName", isValidStringName);
          	request.setAttribute("isValidStringPass", isValidStringPass);
            RequestDispatcher requestdispatcher = request.getRequestDispatcher("createUser.jsp");
            requestdispatcher.forward(request, response);
        }else{
          	request.setAttribute("isValidStringName", isValidStringName);
          	request.setAttribute("isValidStringPass", isValidStringPass);
            RequestDispatcher requestdispatcher = request.getRequestDispatcher("createUser.jsp");
            requestdispatcher.forward(request, response);
        }

        
        
	}

}
