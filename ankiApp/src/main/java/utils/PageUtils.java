package utils;

import java.util.ArrayList;
import java.util.List;

import bean.DataOfFile;

public class PageUtils {
	public List<Integer> getPageElementIds(DataOfFile dataOfFile, int firstElementId){
		List<Integer> pageElementIds = new ArrayList<>();
		int elementId = firstElementId;
		while(elementId < (dataOfFile.getMaxId() + 1) && pageElementIds.size() < 5) {
			if(dataOfFile.isElement(elementId)) {
				pageElementIds.add(elementId);
			}
			elementId++;
		}
		return pageElementIds;
		
	}
}
