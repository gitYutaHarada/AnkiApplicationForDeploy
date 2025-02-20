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
import utils.Utils;
/**
 * Servlet implementation class AnkiTime
 */
@WebServlet("/AnkiTime")
public class AnkiTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnkiTime() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		String question_answer = (String)request.getParameter("question_answer");
		
		HttpSession session = request.getSession();
		UserBean userbean = (UserBean) session.getAttribute("userbean");
		FileOfData fileofdata = (FileOfData) session.getAttribute("fileofdata");
		CreateUserDAO createuser_dao = new CreateUserDAO();
		Utils utils = new Utils();
		
		fileofdata.setMinId(createuser_dao.getDataOfFile_max_min(fileofdata.getFileName(), userbean.getName(), "min"));
		fileofdata.setMaxId(createuser_dao.getDataOfFile_max_min(fileofdata.getFileName(), userbean.getName(), "max"));
		String action = request.getParameter("action");
		
		
		if(id == fileofdata.getMaxId() && "next".equals(action)) {
			question_answer = "last";
			System.out.println(id);
		}else if("back".equals(action)) {
			id = utils.backOrNextId(fileofdata, "back", id);
			question_answer = "question";
		}else if("convert_question".equals(action)) {
			question_answer = "question";
		}else if("convert_answer".equals(action)) {
			question_answer = "answer";
		}else if("next".equals(action)) {
			id = utils.backOrNextId(fileofdata, "next", id);
			question_answer = "question";
		}else if("convert_first".equals(action)) {
			System.out.println(question_answer);
			id = fileofdata.getMaxId();
			question_answer = "question";
			
		}
		
		//formで戻るボタンが押されたら
		//id--かつquestion_answer＝"question"
		//formで次へボタンが押されたら
		//id++かつquestion_answer＝"answer"
		//formで答えへか質問へを押されたら
		//question_answerの番号を変更
		request.setAttribute("id", id);
		request.setAttribute("question_answer", question_answer);

		session.setAttribute("userbean", userbean);
		session.setAttribute("fileofdata", fileofdata);
		RequestDispatcher requestdipatcher = request.getRequestDispatcher("AnkiTime.jsp");
		requestdipatcher.forward(request, response);
		
	}

}
