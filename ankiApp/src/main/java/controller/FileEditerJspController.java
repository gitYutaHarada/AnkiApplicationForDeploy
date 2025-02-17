package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.FileOfData;
import bean.UserBean;
import data_access_object.CreateUserDAO;

/**
 * Servlet implementation class FileEditierController
 */
@WebServlet("/FileEditerJspController")
public class FileEditerJspController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileEditerJspController() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String userName = request.getParameter("userName");
		String fileName = request.getParameter("fileName");
		
		//今までのセッションの引継ぎ
		HttpSession session = request.getSession();
		//選択されたファイルの内容をデータベースから取ってきてBeanクラスに保存してセッション管理する。
		CreateUserDAO createuser_dao = new CreateUserDAO();
		
	    UserBean userbean = (UserBean) session.getAttribute("userbean");
		FileOfData fileofdata = new FileOfData();
		createuser_dao.setDataOfFile(fileofdata, fileName, userName);
		fileofdata.setMaxId(createuser_dao.getDataOfFile_max_min(fileName, userName, "max"));
		fileofdata.setFileName(fileName);
		
		session.setAttribute("fileofdata", fileofdata);
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("FileEditer.jsp");
		requestdispatcher.forward(request, response);
	}

}
