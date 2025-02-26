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
import dataAccessObject.FileDAO;
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
		UserBean userBean = (UserBean) session.getAttribute("userBean");

		FileDAO fileDao = new FileDAO();

		String action = request.getParameter("action");

		if ("create".equals(action)) {
			String createFileName = request.getParameter("createFileName");
			userBean.addFile(createFileName);
			fileDao.addFileName(userBean.getName(), createFileName);

		} else if ("remove".equals(action)) {
			String removeFileName = request.getParameter("removeFileName");
			System.out.println("removeFileName=" + removeFileName);
			int deleteFileCount = fileDao.deleteFile(userBean.getName(), removeFileName);
			System.out.println(deleteFileCount);
			request.setAttribute("deleteFileCount", deleteFileCount);
		}

		List<String> fileNamesList = fileDao.getAllFileName(userBean.getName());
		userBean.setFileNamesList(fileNamesList);
		session.setAttribute("userBean", userBean);

		RequestDispatcher requestdispatcher = request.getRequestDispatcher("myPage.jsp");
		requestdispatcher.forward(request, response);

	}

}
