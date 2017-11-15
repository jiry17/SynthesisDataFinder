package com.antonylhz.shuati.leetcode.revised;

import java.util.HashMap;
public class ValidSudoku {
	public static void main(String[] args) {
		char[][] board = new char[][] {
			".87654321".toCharArray(),
			"2........".toCharArray(),
			"3........".toCharArray(),
			"4........".toCharArray(),
			"5........".toCharArray(),
			"6........".toCharArray(),
			"7........".toCharArray(),
			"8........".toCharArray(),
			"9........".toCharArray()
		};
		(new ValidSudoku()).isValidSudoku(board);
	}
    public boolean isValidSudoku(char[][] board) {
        if(board==null||board.length!=9||board[0].length!=9) return false;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int temp;
        for(int i=0; i<9; i++) {
            map.clear();
            for(int j=0; j<9; j++) {
                if(!Character.isDigit(board[i][j])) continue;
                temp = Integer.parseInt(""+board[i][j]);
                if(map.containsKey(temp)) return false;
                map.put(temp, temp);
            }
            map.clear();
            for(int j=0; j<9; j++) {
                if(!Character.isDigit(board[j][i])) continue;
                temp = Integer.parseInt(""+board[j][i]);
                if(map.containsKey(temp)) return false;
                map.put(temp, temp);
            }
        }
        for(int istart=0; istart<9; istart+=3)
            for(int jstart=0; jstart<9; jstart+=3) {
                map.clear();
                for(int i=0; i<3; i++)
                    for(int j=0; j<3; j++) {
                        if(!Character.isDigit(board[istart+i][jstart+j])) continue;
                        temp = Integer.parseInt(""+board[istart+i][jstart+j]);
                        if(map.containsKey(temp)) return false;
                        map.put(temp, temp);
                    }
            }
        return true;
    }
}
