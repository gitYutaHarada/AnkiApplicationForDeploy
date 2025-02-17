package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.UserInformationBean;
import data_access_object.CreateUserDAO;
import utils.Utils; 

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
		
        Utils utils = new Utils();
		int isValidString_name = (utils.isValidString(name)) ? 1 : 0;
		int isValidString_pass = (utils.isValidString(pass)) ? 1 : 0;
		
		//名前とパスワードが英数字のみなら１を代入して新しいテーブルを作成
        if(isValidString_name == 1 && isValidString_pass == 1) {
    		CreateUserDAO create_dao = new CreateUserDAO();
            int isSuccessInsert = create_dao.createUser(name, pass);
            
            UserInformationBean userInfo_dto = create_dao.select();
            request.setAttribute("userInfo_dto", userInfo_dto);
            
            
          	request.setAttribute("isSuccessInsert", isSuccessInsert);
          	request.setAttribute("isValidString_name", isValidString_name);
          	request.setAttribute("isValidString_pass", isValidString_pass);
            RequestDispatcher requestdispatcher = request.getRequestDispatcher("createUser.jsp");
            requestdispatcher.forward(request, response);
        }else{
          	request.setAttribute("isValidString_name", isValidString_name);
          	request.setAttribute("isValidString_pass", isValidString_pass);
            RequestDispatcher requestdispatcher = request.getRequestDispatcher("createUser.jsp");
            requestdispatcher.forward(request, response);
        }

        
        
	}

}
