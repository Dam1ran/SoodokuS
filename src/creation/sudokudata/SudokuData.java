package creation.sudokudata;

import java.awt.*;
import java.io.Serializable;


public class SudokuData implements Serializable {

    private static final long serialVersionUID = 74269L;

    private String name;

    public int  getFocusPos() { return focusPos; }
    public int getProgress() {return this.progress; }
    public Difficulty getDifficulty() { return difficulty;  }
    public AssistanceLevel getAssistanceLevel() {  return assistanceLevel;  }
    public boolean isSkipNumbers() { return skipNumbers; }
    public boolean isHighlightSkippedNumbers() {  return highlightSkippedNumbers;  }

    public boolean isMarkingNumbers() { return markingNumbers; }
    public int[][] getCells() { return cells; }
    public Color[][] getNumberColors() { return numberColors;  }
    public Color[][] getNumberBackgroundColors() { return numberBackgroundColors; }
    public boolean isGenerated() { return generated;  }
    public String getName() { return name; }


    public void setFocusPos(int focusPos) {  this.focusPos = focusPos;  }

    public void setAssistanceLevel(AssistanceLevel aAssistanceLevel) {

        if(aAssistanceLevel==AssistanceLevel.Full) {
            this.highlightSkippedNumbers=true;
            this.skipNumbers=true;
        }

        if (aAssistanceLevel==AssistanceLevel.Medium) {
            this.highlightSkippedNumbers=true;
            this.skipNumbers=false;
        }

        if (aAssistanceLevel==AssistanceLevel.Low) {
                this.highlightSkippedNumbers=false;
                this.skipNumbers=false;
        }

        this.assistanceLevel = aAssistanceLevel;

    }

    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty;  }

    public void setMarkingNumbers(boolean markingNumbers) { this.markingNumbers = markingNumbers;  }
    public void setCells(int[][] cells) {  this.cells = cells;  }
    public void setGenerated(boolean generated) { this.generated = generated;  }
    public void setName(String name) {   this.name = name;  }

    private boolean highlightSkippedNumbers;
    private boolean skipNumbers;

    private int[][]     cells;

    public void strikeRow(int aStrike) {  this.struckRow[aStrike] = true; }
    public void strikeCol(int aStrike) {  this.struckCol[aStrike] = true; }
    public void strikeSquare(int aStrike) {

        for(int index=0;index<9;index++) {

            if(squareMap[index]==aStrike) this.struckSquare[index] = true;
        }


    }


    public boolean getStruckRow(int aRow){

        return struckRow[aRow];

    }

    public boolean getStruckCol(int aCol){

        return struckCol[aCol];

    }

    public boolean getStruckSquare(int aSquare){

        for(int index=0;index<9;index++) {

            if(squareMap[index]==aSquare) return this.struckSquare[index];

        }

        return true;

    }

    private boolean[]   struckRow = new boolean[9];
    private boolean[]   struckCol = new boolean[9];
    private boolean[]   struckSquare = new boolean[9];
    private int[]       squareMap = new int[]{0,3,6,27,30,33,54,57,60};
    private boolean     struckFirstDiagonal=false;

    public boolean isStruckFirstDiagonal() { return !struckFirstDiagonal;  }

    public void setStruckFirstDiagonal(boolean struckFirstDiagonal) {    this.struckFirstDiagonal = struckFirstDiagonal; }

    public boolean isStruckSecondDiagonal() { return !struckSecondDiagonal;  }

    public void setStruckSecondDiagonal(boolean struckSecondDiagonal) {  this.struckSecondDiagonal = struckSecondDiagonal; }

    private boolean     struckSecondDiagonal=false;


    private Color[][]   numberColors;
    private Color[][]   numberBackgroundColors;

    public long getSecondCounter() { return secondCounter; }

    private long        secondCounter;
    private int         focusPos;
    private int         progress;
    private boolean     markingNumbers;

    public boolean isWon() { return won; }

    public void setWon(boolean won) { this.won = won;  }

    private boolean     won;


    private Difficulty  difficulty;
    private AssistanceLevel  assistanceLevel;

    private boolean generated;


    public SudokuData(){

        this.secondCounter=0L;
        this.focusPos=40;
        this.difficulty=Difficulty.Poor;
        this.generated=false;
        this.markingNumbers=false;
        this.cells = new int[9][9];
        this.numberColors = new Color[9][9];
        this.numberBackgroundColors = new Color[9][9];
        this.progress=0;
        this.highlightSkippedNumbers=true;
        this.skipNumbers=true;
        this.assistanceLevel = AssistanceLevel.Full;
        this.name="Default";
        this.won=false;

    }

    public long incrementSecondCounter(){ return this.secondCounter++; }

    public void clearCells(){

        for(int row=0;row<9;row++)
            for(int col=0;col<9;col++)
                cells[row][col]=0;

    }

    public void setCell(int aRow, int aCol, int aVal){

        this.cells[aRow][aCol]=aVal;

    }

    public void setNumberColor(int aRow, int aCol, Color aColor){

        this.numberColors[aRow][aCol]=aColor;

    }

    public void setNumberBackgroundColor(int aRow, int aCol, Color aColor){

        this.numberBackgroundColors[aRow][aCol]=aColor;

    }

    public int getCell(int aRow, int aCol){

        return this.cells[aRow][aCol];

    }

    public Color getNumberBackgroundColor(int aRow, int aCol){

        return this.numberBackgroundColors[aRow][aCol];

    }

    public void calcProgress(){

        int temp=0;
        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {
            if(cells[row][col]!=0 && !numberColors[row][col].equals(new AppData().getMarkNumberColor())) temp++;
        }

        this.progress=temp;
    }

    public int clearProgress() {this.progress = 0; return this.progress; }

    public Color getNumberColor(int aRow, int aCol) {

        return this.numberColors[aRow][aCol];

    }


}
