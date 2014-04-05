package com.example.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataUtils {

	static Integer[] ran = new Integer[9];
	static Random random = new Random();
	
	public static final ArrayList<Integer> getSudokuList(){
		
		ArrayList<Integer> resList = new ArrayList<Integer>();
		Integer[][] array = new Integer[9][9];
		while (run(array) != 1) {

		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				resList.add(array[i][j]);
			}
			
		}
		return resList;

	}
	static int randomFromList(final List<Integer> list) {
		int size = list.size();
		int index = random.nextInt(size);
		return list.get(index);
	}

	static ArrayList<Integer> removeDesList(final ArrayList<Integer> source,
			ArrayList<Integer> list) {
		int size = list.size();
		ArrayList<Integer> elementArrayList = source;
		for (int i = 0; i < size; i++) {
			if (elementArrayList.contains(list.get(i))) {
				elementArrayList.remove((Integer) list.get(i));
			}
		}
		return elementArrayList;
	}

	static int run(Integer[][] array) {

		for (int i = 0; i < 9; i++) {
			ran[i] = i + 1;
		}
		List<Integer> list = Arrays.asList(ran);
		Collections.shuffle(list);

		int num = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = ran[num];
				num++;
			}
		}
		ArrayList<Integer> delArray = new ArrayList<Integer>();
		ArrayList<Integer> delDiagArray = new ArrayList<Integer>();
		ArrayList<Integer> sourceArray = new ArrayList<Integer>();
		ArrayList<Integer> destArray = new ArrayList<Integer>();
		sourceArray.addAll(list);

		int row = 3;
		for (row = 3; row < 9; row++) {
			delDiagArray.clear();
			for (int i = 0; i < row; i++) {
				delArray.clear();
				sourceArray.clear();
				sourceArray.addAll(list);
				for (int j = 0; j < row; j++) {
					if (!delArray.contains(array[j][i]))
						delArray.add(array[j][i]);
				}
				for (int k = 0; k < i; k++) {
					if (!delArray.contains(array[row][k]))
						;
					delArray.add(array[row][k]);
				}
				if (row % 3 != 0) {
					int rownum = row / 3;
					int currentI = i / 3;
					for (int x = rownum * 3; x < row; x++) {
						for (int y = currentI * 3; y < currentI + 3; y++) {
							if (!delArray.contains(array[x][y]))
								delArray.add(array[x][y]);
						}
					}
				}
				destArray = removeDesList(sourceArray, delArray);
				if (destArray.size() == 0)
					return 0;
				int v = randomFromList(destArray);
				array[row][i] = v;
				if (!delDiagArray.contains(v))
					delDiagArray.add(v);
			}

			for (int i = 0; i < row; i++) {
				delArray.clear();
				sourceArray.clear();
				sourceArray.addAll(list);
				for (int j = 0; j < row; j++) {
					if (!delArray.contains(array[i][j]))
						delArray.add(array[i][j]);
				}
				for (int k = 0; k < i; k++) {
					if (!delArray.contains(array[k][row]))
						;
					delArray.add(array[k][row]);
				}
				if (row % 3 != 0) {
					int rownum = row / 3;
					int currentI = i / 3;
					for (int x = rownum * 3; x < row; x++) {
						for (int y = currentI * 3; y < currentI + 3; y++) {
							if (!delArray.contains(array[y][x]))
								delArray.add(array[y][x]);
						}
					}
				}
				destArray = removeDesList(sourceArray, delArray);
				if (destArray.size() == 0)
					return 0;
				int v = randomFromList(destArray);
				array[i][row] = v;
				if (!delDiagArray.contains(v))
					delDiagArray.add(v);
			}
			sourceArray.clear();
			sourceArray.addAll(list);
			if (row % 3 != 0) {
				int rownum = row / 3;
				for (int x = rownum * 3; x <= row; x++) {
					for (int y = rownum * 3; y <= row; y++) {
						if (x == row && y == row) {

						} else {
							if (!delDiagArray.contains(array[x][y])) {
								delDiagArray.add(array[x][y]);
							}
						}
					}
				}
			}

			destArray = removeDesList(sourceArray, delDiagArray);
			if (destArray.size() == 0)
				return 0;
			array[row][row] = randomFromList(destArray);
		}

		if (row == 9) {
			return 1;
		} else {
			return 0;
		}

	}

}
