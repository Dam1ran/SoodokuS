package creation.dataworks;

import creation.sudokudata.Difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SudokuGenerator extends Thread {

    private final AtomicBoolean abort = new AtomicBoolean(false);

    public boolean isSolved() { return solved.get(); }

    private final AtomicBoolean solved = new AtomicBoolean(false);

    private int[][]     cells;
    public void setCells(int[][] cells) {

        for (int row = 0; row < 9; row++) System.arraycopy(cells[row], 0, this.cells[row], 0, 9);

    }

    public int[][] getCells() { return cells; }


    private int[] sortedIndexes = new int[81];


    public SudokuGenerator() {

        this.cells = new int[9][9];
        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {
            this.cells[row][col]=0;
        }

    }


    public void abort(){

        abort.set(true);

    }

    public void  generateInitialPattern() {

        int initialClues=0;

        while(initialClues<18) {
            int row = getRandomNumberInRange(0, 8);
            int col = getRandomNumberInRange(0, 8);

            int number = getRandomNumberInRange(1, 9);

            if (isAbsent(row, col, number) && this.cells[row][col]==0) {
                this.cells[row][col]=number;
                initialClues++;
            }

        }

    }

    private boolean isAbsent(int aRow, int aCol, int aNumber) {

        int smallRow = aRow - aRow%3;
        int smallCol = aCol - aCol%3;

        for(int sRow = smallRow;sRow<smallRow+3;sRow++)
            for(int sCol = smallCol;sCol<smallCol+3;sCol++)

                if (cells[sRow][sCol] == aNumber) return false;


        for(int step = 0; step<9;step++) {

            if (cells[step][aCol] == aNumber) return false;
            if (cells[aRow][step] == aNumber) return false;

        }

        return true;

    }

    private int getRandomNumberInRange(int aMin, int aMax) {

        if (aMin >= aMax) { throw new IllegalArgumentException("Max must be greater than Min"); }

        return (int)(Math.random() * ((aMax - aMin) + 1)) + aMin;

    }

    public void prepareBoard(Difficulty aDifficulty){

        switch (aDifficulty) {

            case Evil : {sweepBoard(getRandomNumberInRange(14,18)); break;}
            case Hard : {sweepBoard(getRandomNumberInRange(19,26)); break;}
            case Baby :
            case Fair : {sweepBoard(getRandomNumberInRange(27,36)); break;}
            case Easy : {sweepBoard(getRandomNumberInRange(37,54)); break;}
            case Poor :
            default   : break;
        }


    }

    private void sweepBoard(int aClues){

        int clues=81;

        while(clues!=aClues) {

            int row = getRandomNumberInRange(0, 8);
            int col = getRandomNumberInRange(0, 8);

            if ( cells[row][col]!=0) {

                cells[row][col] = 0;
                clues--;

            }

        }


    }

    private boolean checkInvariant(){


        ArrayList<Integer> numberList = new ArrayList<>();

        for(int myIndex=0;myIndex<81;myIndex++) {

            int aXcross = myIndex / 9;
            int aYcross = myIndex % 9;

            if(cells[aXcross][aYcross]==0) {

                int smallRow = aXcross - aXcross % 3;
                int smallCol = aYcross - aYcross % 3;


                for (int number = 1; number <= 9; number++) {

                    searcher:
                    {

                    //square check
                    for (int row = smallRow; row < smallRow + 3; row++)
                        for (int col = smallCol; col < smallCol + 3; col++)

                            if (cells[row][col] == number) break searcher;

                    //cross check
                    for (int step = 0; step < 9; step++) {

                        if (cells[aXcross][step] == number) break searcher;
                        if (cells[step][aYcross] == number) break searcher;

                    }

                    numberList.add(number);

                    }

                }

            }

        }

        return numberList.isEmpty();
    }

    private boolean solve() {

        Pair[] pairArray = new Pair[81];
        int counter=0;
        int idx=0;
        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {

            counter=0;
            idx=row*9+col;
            for(int i=1;i<=9;i++) if(isAbsent(row,col,i)) counter++;

            if(cells[row][col]==0)
                 pairArray[idx]= new Pair(idx, counter);
            else pairArray[idx]= new Pair(idx,-1);

        }

        Arrays.sort(pairArray);

        for(int i=0;i<81;i++) sortedIndexes[i]= pairArray[i].index;

        if(abort.get()) { return false; }

        for(int index=0;index<81;index++)
        {int row= sortedIndexes[index]/9; int col= sortedIndexes[index]%9;

            if (cells[row][col] == 0) {

                for (int number=1;number<=9;number++) {

                    if(isAbsent(row, col, number) ) {

                        cells[row][col] = number;

                        if (solve()) {
                            return true;
                        } else {

                            cells[row][col] = 0;

                        }
                    }

                }

                return false;

            }


        }

        return true;

    }

    public boolean isFull(){

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) if(cells[row][col]==0) return false;
        return true;
    }

    private void sortWeights(){

        ArrayList<Integer> al = new ArrayList<>();

        for(int index=0;index<81;index++) {int row = index/9; int col = index%9;

            int counter=0;

            for(int i=1;i<=9;i++) if(isAbsent(row,col,i)) counter++;

            al.add(counter);

        }

        System.out.println("wights: "+al);

    }


    @Override
    public void run() {

      solved.set(solve());

    }

}