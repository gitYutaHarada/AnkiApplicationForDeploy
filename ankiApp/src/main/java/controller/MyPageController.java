package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.UserBean;
import data_access_object.CreateUserDAO;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/MyPageController")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyPageController() {
		super();
		// TODO Auto-generated constructor stub
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession(true);
		UserBean userbean = (UserBean) session.getAttribute("userbean");
		String userName = userbean.getName();

		CreateUserDAO createuser_dao = new CreateUserDAO();

		String action = request.getParameter("action");

		if ("create".equals(action)) {
			String create_fileName = request.getParameter("create_fileName");
			userbean.addFile(create_fileName);
			createuser_dao.addFileName(userbean.getName(), create_fileName);

		} else if ("remove".equals(action)) {
			String remove_fileName = request.getParameter("remove_fileName");
			int deleteFile_count = createuser_dao.deleteData(userName, remove_fileName);
			request.setAttribute("deleteFile_count", deleteFile_count);
		}

		List<String> fileNamesList = createuser_dao.getAllFileName(userName);
		userbean.setFileNamesList(fileNamesList);
		session.setAttribute("userbean", userbean);

		RequestDispatcher requestdispatcher = request.getRequestDispatcher("myPage.jsp");
		requestdispatcher.forward(request, response);

	}

}
