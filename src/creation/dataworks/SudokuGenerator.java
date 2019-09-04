package creation.dataworks;

import creation.frames.Parent;
import creation.sudokudata.Difficulty;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SudokuGenerator extends Thread {

    private final AtomicBoolean abort = new AtomicBoolean(false);
    private final AtomicBoolean solved = new AtomicBoolean(false);

    public boolean isSolved() { return solved.get(); }

    private int[][]     cells;
    public void setCells(int[][] cells) {

        for (int row = 0; row < 9; row++) System.arraycopy(cells[row], 0, this.cells[row], 0, 9);

    }
    public int[][] getCells() { return cells; }

    private  Random random;
    private int[] vec = new int[9];

    private int[] sortedIndexes = new int[81];
    private int[] vecSmallStep = new int[9];


    public SudokuGenerator() {


        this.cells = new int[9][9];
        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {
            this.cells[row][col]=0;
        }

        for(int number=1;number<=9;number++) {
           vec[number-1]=number;
           vecSmallStep[number-1] =(number-1)-(number-1)%3;

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

        int smallRow = vecSmallStep[aRow];
        int smallCol = vecSmallStep[aCol];

        for (int row=0;row<3;row++) for (int col=0;col<3;col++) {

            if (cells

                    [smallRow+row]
                    [smallCol+col]

                    == aNumber) return false;

        }

        for(int step = 0; step<9;step++) {

            if (cells[step][aCol] == aNumber) return false;
            if (cells[aRow][step] == aNumber) return false;

        }

        return true;

    }

    private void shuffle(int[] array) {
        if (random == null) random = new Random();
        int count = array.length;
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public int getRandomNumberInRange(int aMin, int aMax) {

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

    private boolean sweepBoard(){
/*
        int[] squareFillRatio=new int[9];

        for(int row=0;row<9;row+=3) for(int col=0;col<9;col+=3){

            int smallRow = row-row%3;
            int smallCol = col-col%3;

            for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
                for (int sCol = smallCol; sCol < smallCol + 3; sCol++){

                    if (cells[sRow][sCol]!=0)  squareFillRatio[(smallRow*3+smallCol)/3]++;

                }

        }

        int[] smallSpiral = new int[]{4,1,5,7,3,0,8,2,6};


        int index = 0;
        int max = squareFillRatio[smallSpiral[index]];

        for (int i=1; i<9; i++) {

            if (squareFillRatio[smallSpiral[i]] > max) {
                max = squareFillRatio[smallSpiral[i]];
                index = i;
            }

        }*/

//==============================================
        int[] numbersInstances = new int[9];
        for(int i = 0; i <81;i++) {

            if(cells[i/9][i%9]!=0)
                numbersInstances[cells[i/9][i%9]-1]++;

        }

        int maxN = numbersInstances[0];
        int number = 0;
        for (int i=1; i<9; i++) {

            if (numbersInstances[i] > maxN) {
                maxN = numbersInstances[i];
                number = i;
            }

        }

        System.out.println("max number: "+(number+1));

//============================================================================

       /* int row = getRandomNumberInRange((smallSpiral[index]/3)*3,(smallSpiral[index]/3)*3+2);
        int col = getRandomNumberInRange((smallSpiral[index]%3)*3,(smallSpiral[index]%3)*3+2);*/

  /*      int row = aIndex/9;
        int col = aIndex%9;

        int smallRow = row - row % 3;
        int smallCol = col - col % 3;*/
       /*
       int squareRow = (smallSpiral[index]/3)*3;
       int squareCol = (smallSpiral[index]%3)*3;*/

       int rindex = getRandomNumberInRange(0,80);

       /*for(int sRow=0;sRow < 9;sRow++) {
           for(int sCol=0;sCol < 9;sCol++) {*/

               if(cells[rindex/9][rindex%9]==(number+1)) {
                   cells[rindex/9][rindex%9] = 0;
                   return true;
               }

/*           }
       }*/


           /*for(int sCol=squareCol;sCol < squareCol+3;sCol++) {

           if(cells[sRow][sCol]==(number+1)) cells[sRow][sCol]=0;
           return true;

       }*/

       return false;

      /*
        if(cells[row][col]!=0) {
            cells[row][col] = 0;
            return true;
        }
        else{
            return false;
        }*/

    }

    private void sweepBoard(int aClues){

        int clues=81;

        while(clues!=aClues) {

            if ( sweepBoard()) {

                clues--;

            }

        }

    }


    private boolean solve() {

        Pair[] pairArray = new Pair[81];
        int counter;
        int idx = 0;
        for (int row = 0; row < 9; row++) for (int col = 0; col < 9; col++) {

            counter = 0;
            for (int i = 1; i <= 9; i++) if (isAbsent(row, col, i)) counter++;

            if (cells[row][col] == 0)
                pairArray[idx] = new Pair(idx, counter);
            else pairArray[idx] = new Pair(idx, -1);

            idx++;

        }

        Arrays.sort(pairArray);

        for (int i = 0; i < 81; i++) sortedIndexes[i] = pairArray[i].index;


        if(abort.get()) { return false; }

        for(int index=0;index<81;index++)
        {int row = sortedIndexes[index]/9; int col = sortedIndexes[index]%9;

            if (cells[row][col] == 0) { shuffle(vec); int[] shuffledRange=new int[9];
                System.arraycopy(vec, 0, shuffledRange, 0, 9);

                for (int numberZ=1;numberZ<=9;numberZ++) {int number = shuffledRange[numberZ-1];

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

    @Override
    public void run() {

        if(!Parent.fullCheck(cells)) solved.set(solve());

    }


}