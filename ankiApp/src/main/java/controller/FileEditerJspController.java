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
import dataAccessObject.DataOfFileDAO;
import dataAccessObject.FileDAO;

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
		//今までのセッションの引継ぎ
		HttpSession session = request.getSession();
		//選択されたファイルの内容をデータベースから取ってきてBeanクラスに保存してセッション管理する。
		DataOfFileDAO dataOfFileDao = new DataOfFileDAO();
		FileDAO fileDao = new FileDAO();
	    String fileName = request.getParameter("fileName");
	    DataOfFile dataOfFile = new DataOfFile();
	    
	    dataOfFile.setFileId(fileDao.getFileIdByFileName(fileName));
		dataOfFile.setFileName(fileName);
		dataOfFileDao.setDataOfFile(dataOfFile, dataOfFile.getFileId());

		session.setAttribute("dataOfFile", dataOfFile);
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("FileEditer.jsp");
		requestdispatcher.forward(request, response);
	}

}
