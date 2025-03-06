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

import bean.DataOfFile;
import bean.UserBean;
import dataAccessObject.DataOfFileDAO;
import utils.PageUtils;
import utils.SearchUtils;
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
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		DataOfFile dataOfFile = (DataOfFile) session.getAttribute("dataOfFile");
		DataOfFileDAO dataOfFileDao = new DataOfFileDAO();
		StringUtils stringUtils = new StringUtils();
		SearchUtils searchUtils = new SearchUtils();
		PageUtils pageUtils = new PageUtils();
		
		String createQuestion = request.getParameter("createQuestion");
		String createAnswer = request.getParameter("createAnswer");
		
		if("create".equals(action) && (stringUtils.isEmptyOrSpace(createQuestion) || stringUtils.isEmptyOrSpace(createAnswer))) {
			String msg = "質問や解答へ空白を入れることはできません";
			request.setAttribute("msg", msg);
		}else if ("create".equals(action)) {
			dataOfFileDao.addData(dataOfFile, dataOfFile.getFileId(), createQuestion, createAnswer);
			dataOfFile.setMaxId(dataOfFileDao.getDataOfFileMaxMin(dataOfFile.getFileId(), "max"));
		}else if("delete".equals(action)) {
			String selectQuestion = request.getParameter("selectQuestion");
			String selectAnswer = request.getParameter("selectAnswer");
		    int selectId = Integer.parseInt((String)request.getParameter("selectId"));
		    
		    dataOfFileDao.deleteDataOfFile(dataOfFile, selectId);
			List<Integer> pageElementIds = pageUtils.getPageElementIdsBySelectId(dataOfFile, selectId);
			request.setAttribute("pageElementIds", pageElementIds);
		    request.setAttribute("selectId", selectId);
		}else if("edit".equals(action)) {
		    int selectId = Integer.parseInt((String)request.getParameter("selectId"));
			List<Integer> pageElementIds = pageUtils.getPageElementIdsBySelectId(dataOfFile, selectId);
			request.setAttribute("pageElementIds", pageElementIds);
		    request.setAttribute("selectId", selectId);
		}else if("completeEdit".equals(action)) {
			String selectQuestion = request.getParameter("selectQuestion");
			String selectAnswer = request.getParameter("selectAnswer");
			String editQuestion = request.getParameter("editQuestion");
			String editAnswer = request.getParameter("editAnswer");
			if(editQuestion == "") editQuestion = selectQuestion;
			if(editAnswer == "") editAnswer = selectAnswer;
		    int selectId = Integer.parseInt((String)request.getParameter("selectId"));

		    dataOfFileDao.editFileOfData(dataOfFile, selectId, editQuestion, editAnswer);

		}else if("search".equals(action)){
			String searchWord = request.getParameter("searchWord");
			if(stringUtils.isEmptyOrSpace(searchWord)) {
				String msg = "検索単語が空になっています";
				request.setAttribute("msg", msg);
			}else {
				List <Integer> searchWords = searchUtils.searchWord(dataOfFile, searchWord);
				request.setAttribute("searchWords", searchWords);
			}			
		}else if("complateSearch".equals(action)) {
		}else if("pageTransition".equals(action)){
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			List<Integer> pageElementIds = pageUtils.getPageElementIdsByPageNum(dataOfFile, pageNum);
			request.setAttribute("pageElementIds", pageElementIds);
		}
		session.setAttribute("userBean", userBean);
		session.setAttribute("dataOfFile", dataOfFile);
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("FileEditer.jsp");
		requestdispatcher.forward(request, response);
	}

}
