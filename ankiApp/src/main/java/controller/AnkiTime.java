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
import dataAccessObject.DataOfFileDAO;
import utils.StringUtils;
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
		String questionAnswer = (String)request.getParameter("questionAnswer");
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		DataOfFile dataOfFile = (DataOfFile) session.getAttribute("dataOfFile");
		DataOfFileDAO dataOfFileDao = new DataOfFileDAO();
		StringUtils stringUtils = new StringUtils();
		
		dataOfFile.setMinId(dataOfFileDao.getDataOfFileMaxMin(dataOfFile.getFileId(), "min"));
		dataOfFile.setMaxId(dataOfFileDao.getDataOfFileMaxMin(dataOfFile.getFileId(), "max"));
		String action = request.getParameter("action");
		
		
		if(id == dataOfFile.getMaxId() && "next".equals(action)) {
			questionAnswer = "last";
			id++;
		}else if("back".equals(action)) {
			System.out.println("back");
			id = stringUtils.backOrNextId(dataOfFile, "back", id);
			questionAnswer = "question";
		}else if("convertQuestion".equals(action)) {
			questionAnswer = "question";
		}else if("convertAnswer".equals(action)) {
			questionAnswer = "answer";
		}else if("next".equals(action)) {
			id = stringUtils.backOrNextId(dataOfFile, "next", id);
			questionAnswer = "question";
		}else if("convertFirst".equals(action)) {
			id = dataOfFile.getMinId();
			questionAnswer = "question";	
		}
		
		//formで戻るボタンが押されたら
		//id--かつquestionAnswer＝"question"
		//formで次へボタンが押されたら
		//id++かつquestionAnswer＝"answer"
		//formで答えへか質問へを押されたら
		//questionAnswerの番号を変更
		request.setAttribute("id", id);
		request.setAttribute("questionAnswer", questionAnswer);

		session.setAttribute("userBean", userBean);
		session.setAttribute("dataOfFile", dataOfFile);
		RequestDispatcher requestdipatcher = request.getRequestDispatcher("AnkiTime.jsp");
		requestdipatcher.forward(request, response);
		
	}

}
