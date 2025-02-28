package utils;

import java.util.ArrayList;
import java.util.List;

import bean.DataOfFile;

public class PageUtils {
	public List<Integer> getPageElementIdsByPageNum(DataOfFile dataOfFile, int pageNum){
		List<Integer> pageElementIds = new ArrayList<>();
		//最初の番号firstElementNum=１の時firstElementNum＝0
		int firstElementNum = (pageNum - 1) * 5;
		int elementId = 0;
		int isElementNum = 0;
		//elementId++で要素番号を探す。
		//あったらpageElementIdsに追加pageElementIdsが五個以下もしくはelementIdが最後の要素まで行ってしまったら終了
		//
		while(elementId < (dataOfFile.getMaxId() + 1) && pageElementIds.size() < 5) {
			if(dataOfFile.isElement(elementId)) {
				isElementNum++;
				if(firstElementNum < isElementNum) {
					pageElementIds.add(elementId);
				}
			}
			elementId++;
		}
		return pageElementIds;
		
	}
	
	public List<Integer> getPageElementIdsBySelectId(DataOfFile dataOfFile, int selectId){
		List<Integer> pageElementIds = new ArrayList<>();
		
		//pageElementIdsを作るその中にselectIdが含まれていたらreturn　pageElementIds
		//含まれていなかったら初期化してまた新しいpageElementIdsを作ってselectIdがあるかどうか確認
		for(int pageNum = 1; pageNum < (dataOfFile.getDataOfFileSize() / 5) + 2; pageNum++) {
			pageElementIds = getPageElementIdsByPageNum(dataOfFile, pageNum);
			if(pageElementIds.contains(selectId)) {
				return pageElementIds;
			}
		}
		return new ArrayList<>();
	}
}
