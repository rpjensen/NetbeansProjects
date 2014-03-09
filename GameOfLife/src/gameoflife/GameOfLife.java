
package gameoflife;


/**
 * This class creates a Game of Life rectangle array and runs the simulation for a given number of cycles.
 * @author Ryan Jensen
 * @version Due Feb 10, 2014
 */
public class GameOfLife {

    private Cell[][] gameModel;
    private int[][] uiMatrix;
    private int width;
    private int height;
    private int resolution;
    private double threshold;
    private LifeWindow uiWindow;

    /**
     * This is the default constructor. The game initializes with width, height,
     * resolution 10, 1/3 cells living.
     *
     * @param width The width of the board you choose
     * @param height The height of the board you choose
     */
    public GameOfLife(int width, int height) {
        if ((width > 4 && height > 4) && (width <= 30 && width <= 30)) {
            this.width = width;
            this.height = height;
        } else {
            this.width = 20;
            this.height = 20;
        }//lets the tester set a width/height between 5 and 30 else default 20x20 
        this.resolution = 25;
        this.threshold = 0.3333;
        this.uiMatrix = new int[this.width][this.height];
        this.gameModel = new Cell[this.width + 2][this.height + 2];
        //now we create memory for the cells in the model, create a boarder of non-playable cells,
        //and initilize the first generation cells.
        for (int i = 0; i < gameModel.length; i++) {
            for (int j = 0; j < gameModel[i].length; j++) {
                gameModel[i][j] = new Cell();
            }
        }//initilize the cells in the model
        for (int i = 0; i < gameModel.length; i++) {
            gameModel[i][0].setIsPlayable(false);
            gameModel[i][gameModel[i].length - 1].setIsPlayable(false);
        }//set the boarder for top row and bottom row
        for (int j = 0; j < gameModel[0].length; j++) {
            gameModel[0][j].setIsPlayable(false);
            gameModel[gameModel.length - 1][j].setIsPlayable(false);
        }//set the boarder for left row and right row
        for (int i = 0; i < gameModel.length; i++) {
            for (int j = 0; j < gameModel[i].length; j++) {
                boolean belowThreshold = false;
                if (Math.random() < this.threshold) {
                    belowThreshold = true;
                }
                gameModel[i][j].setCurrentStateAlive(belowThreshold);
            }
        }//makes about 1/3 of the cells in the game alive.
        this.updateUIMatrixFromModel();
        this.uiWindow = new LifeWindow(this.uiMatrix, this.resolution);
    }
    
/**
 * Runs a game of life simulation for a positive integer number of cycles.
 * @param cycles the number of cycles
 */
    public void run(int cycles){
        if (cycles >= 1){
            
            int cycleCount = 0;
            do {
                this.uiWindow.displayLife();
                for (int i = 0; i < gameModel.length; i++){
                    for (int j = 0; j < gameModel[i].length; j++){
                        this.setNextState(i, j);
                    }
                }
                this.setCurrentStateFromNextState();
                this.updateUIMatrixFromModel();
                cycleCount++;
            }while (cycleCount < cycles);
        }

    }    
    
/**
 * This method sets the next state of a cell at index i,j.
 * @param i The width index
 * @param j The height index
 */
    private void setNextState(int i, int j){
        int numLivingNeighbors = numberOfLivingNeighbors(i, j);
        if (gameModel[i][j].currentStateAlive()){
            if (numLivingNeighbors >= 4){
                gameModel[i][j].setNextStateAlive(false);
            }else if (numLivingNeighbors < 2){
                gameModel[i][j].setNextStateAlive(false);
            }else {
                gameModel[i][j].setNextStateAlive(true);
            }    
        }else {
            if (numLivingNeighbors == 3){
                gameModel[i][j].setNextStateAlive(true);
            }else {
                gameModel[i][j].setNextStateAlive(false);
            }
        }
    }
    
/**
 * A method to get the number of living neighbors around a given cell.
 * @param widthI The width index of the cell
 * @param heightJ The height index of the cell
 * @return The number of living neighbors around the cell at index widthI, heightJ.
 */
    private int numberOfLivingNeighbors(int widthI, int heightJ){
        int numLivingNeighbors = 0;
        if (! gameModel[widthI][heightJ].isPlayable()){
            return 0;
        }//avoid array out of bounds exceptions
        for (int i = (widthI - 1); i <= (widthI + 1); i++ ){
            for (int j = (heightJ - 1); j <= (heightJ + 1); j++){
                if ((i != widthI) || (j != heightJ)){
                    if (gameModel[i][j].currentStateAlive()){
                        numLivingNeighbors++;
                    }
                }//don't include self
            }
        }//for the 8 cells around the cell at index widthI,heightJ
        return numLivingNeighbors;
    }
    
/**
 * This method updates the array of integers that is to be referenced by the LifeWindow class.
 */
    private void updateUIMatrixFromModel(){
        
        for (int i = 1; i <= this.width; i++){
            for (int j = 1; j <= this.height; j++){
                if (gameModel[i][j].currentStateAlive()){
                    uiMatrix[i-1][j-1] = 1;
                }else {
                    uiMatrix[i-1][j-1] = 0;
                }
            }
        }//note cell(1,1) in the model becomes int(0,0) in the uiMatrix, in general cell(i,j) becomes int(i-1,j-1)
    }
    
/**
 * Goes through all cells in the model and sets the current state to the next state.
 * This method should only be called after all cells next state has been set.
 */
    private void setCurrentStateFromNextState(){
        for (int i = 0; i < gameModel.length; i++){
            for (int j = 0; j < gameModel[i].length; j++){
                gameModel[i][j].setCurrentStateAlive(gameModel[i][j].nextStateAlive());
            }
        }  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife(20, 20);
        gameOfLife.run(200);

    }
}
