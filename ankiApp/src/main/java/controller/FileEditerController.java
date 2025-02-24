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
import utils.StringUtils;

/**
 * Servlet implementation class FileEditerController
 */
@WebServlet("/FileEditerController")
public class FileEditerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileEditerController() {
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
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		UserBean userbean = (UserBean) session.getAttribute("userbean");
		FileOfData fileofdata = (FileOfData) session.getAttribute("fileofdata");
		CreateUserDAO createuser_dao = new CreateUserDAO();
		StringUtils stringutils = new StringUtils();
		String create_question = request.getParameter("create_question");
		String create_answer = request.getParameter("create_answer");
		
		System.out.println(action);

		if("create".equals(action) && stringutils.isEmptyOrSpace(create_question) || stringutils.isEmptyOrSpace(create_answer)) {
			String msg = "質問や解答へ空白を入れることはできません";
			request.setAttribute("msg", msg);
		}else if ("create".equals(action)) {
			createuser_dao.addData(fileofdata, userbean.getName(), fileofdata.getFileName(), create_question,
					create_answer);
			fileofdata.setMaxId(createuser_dao.getDataOfFile_max_min(fileofdata.getFileName(), userbean.getName(), "max"));
		}else if("delete".equals(action)) {
			String select_question = request.getParameter("select_question");
			String select_answer = request.getParameter("select_answer");
		    int select_id = Integer.parseInt((String)request.getParameter("select_id"));
			
			createuser_dao.deleteFileOfData(fileofdata, select_id, userbean.getName());
			fileofdata.setMaxId(createuser_dao.getDataOfFile_max_min(fileofdata.getFileName(), userbean.getName(), "max"));
		}else if("edit".equals(action)) {
		    int select_id = Integer.parseInt((String)request.getParameter("select_id"));
		    
		    request.setAttribute("select_id", select_id);
		}else if("complete_edit".equals(action)) {
			String select_question = request.getParameter("select_question");
			String select_answer = request.getParameter("select_answer");
			String edit_question = request.getParameter("edit_question");
			String edit_answer = request.getParameter("edit_answer");
			if(edit_question == "") edit_question = select_question;
			if(edit_answer == "") edit_answer = select_answer;
		    int select_id = Integer.parseInt((String)request.getParameter("select_id"));

		    createuser_dao.editFileOfData(fileofdata, select_id, userbean.getName(), edit_question, edit_answer);

		}
		
		session.setAttribute("userbean", userbean);
		session.setAttribute("fileofdata", fileofdata);
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("FileEditer.jsp");
		requestdispatcher.forward(request, response);
	}

}
