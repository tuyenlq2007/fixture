package test;

import java.util.List;

public class InsertSort {
	List<Integer> sortedList;
	InsertSort(List<Integer> unsortedList){
		this.sortedList = unsortedList;
	}
	public List<Integer> getSortedList() {
		// TODO Auto-generated method stub
		for(int i=0; i<this.sortedList.size();i++) {
			int j = i;
			while(j>0) {
				if (this.sortedList.get(j) < this.sortedList.get(j-1)){
					int temp = this.sortedList.get(j) ;
					this.sortedList.set(j, this.sortedList.get(j-1));
					this.sortedList.set(j-1, temp);
				}
				j--;
			}
		}
		return this.sortedList;
	}
}
