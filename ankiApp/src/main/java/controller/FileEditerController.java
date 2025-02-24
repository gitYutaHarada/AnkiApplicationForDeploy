package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.DataOfFile;
import bean.UserBean;
import data_access_object.DataOfFileDAO;
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
		DataOfFile dataoffile = (DataOfFile) session.getAttribute("dataoffile");
		DataOfFileDAO dataoffile_dao = new DataOfFileDAO();
		StringUtils stringutils = new StringUtils();
		String create_question = request.getParameter("create_question");
		String create_answer = request.getParameter("create_answer");
		
		System.out.println(action);

		if("create".equals(action) && (stringutils.isEmptyOrSpace(create_question) || stringutils.isEmptyOrSpace(create_answer))) {
			String msg = "質問や解答へ空白を入れることはできません";
			request.setAttribute("msg", msg);
		}else if ("create".equals(action)) {
			dataoffile_dao.addData(dataoffile, userbean.getName(), dataoffile.getFileName(), create_question,
					create_answer);
			dataoffile.setMaxId(dataoffile_dao.getDataOfFile_max_min(dataoffile.getFileName(), userbean.getName(), "max"));
		}else if("delete".equals(action)) {
			String select_question = request.getParameter("select_question");
			String select_answer = request.getParameter("select_answer");
		    int select_id = Integer.parseInt((String)request.getParameter("select_id"));
			dataoffile_dao.deleteFileOfData(dataoffile, select_id, userbean.getName());
			dataoffile.setMaxId(dataoffile_dao.getDataOfFile_max_min(dataoffile.getFileName(), userbean.getName(), "max"));
		    request.setAttribute("select_id", select_id);
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

		    dataoffile_dao.editFileOfData(dataoffile, select_id, userbean.getName(), edit_question, edit_answer);

		}
		
		session.setAttribute("userbean", userbean);
		session.setAttribute("dataoffile", dataoffile);
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("FileEditer.jsp");
		requestdispatcher.forward(request, response);
	}

}
