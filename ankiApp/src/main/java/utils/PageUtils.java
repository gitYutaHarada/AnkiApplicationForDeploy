package utils;

import java.util.ArrayList;
import java.util.List;

import bean.DataOfFile;

public class PageUtils {
	public List<Integer> getPageElementIds(DataOfFile dataOfFile, int pageNum){
		List<Integer> pageElementIds = new ArrayList<>();
		//最初の番号firstElementNum=１の時firstElementNum＝0
		int firstElementNum = (pageNum - 1) * 5;
		int elementId = 0;
		int isElementNum = 0;
		//elementId++で要素番号を探す。
		//あったらpageElementIdsに追加pageElementIdsが五個以下もしくはelementIdが最後の要素まで行ってしまったら終了
		//
		while(elementId < (dataOfFile.getMaxId() + 1) && pageElementIds.size() < 5 && elementId < firstElementNum) {
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
}
