
package gameoflife;

/**
 * This class represents a model of a single cell in GameOfLife.  The reason this programming choice was made was to 
 * make it more elegant when dealing with the array indexes and make it easier to make non-playable cells that could
 * be like barriers or obstacles that weren't confined to the boarder.
 * @author Ryan Jensen
 * @version Due Feb 10, 2014
 */
public class Cell {
        private boolean currentStateAlive;
        private boolean nextStateAlive;
        private boolean isPlayable;
        
/**
 * This is the default constructor of the cell class.  It initializes a dead cell that is playable/not in the boarder.
 */
    public Cell(){
        this.currentStateAlive = false;
        this.nextStateAlive = false;
        this.isPlayable = true;
    }

/**
* Returns the currentState of a cell.
* @return the current state of a cell alive/dead
*/
    public boolean currentStateAlive(){
        return this.currentStateAlive;
    }

/**
* sets the current state of a cell, but only if the cell is playable.
* @param currentStateAlive the current state you want to change it to
*/
    public void setCurrentStateAlive(boolean currentStateAlive){
        if (this.isPlayable){
            this.currentStateAlive = currentStateAlive;
        }//ensues that only playable cells get updated
    }

/**
* Gets the next state of a cell.
* @return the next state of a cell
*/
    public boolean nextStateAlive(){
        return this.nextStateAlive;
    }

/**
* Sets the nextState of a cell, but only if the cell is playable.
* @param nextStateAlive the next state of the cell
*/
    public void setNextStateAlive(boolean nextStateAlive){
        if (this.isPlayable){
            this.nextStateAlive = nextStateAlive;
        }//ensures that only playable cells get updated
    }

/**
* Gets the playable status of a cell.
* @return whether the cell is playable
*/
    public boolean isPlayable(){
        return this.isPlayable;
    }

/**
* Sets whether the cell is playable or not.  If it is set to not playable the
* currentState and next state are set to dead.
* @param isPlayable whether the cell is in play
*/
    public void setIsPlayable(boolean isPlayable){
        this.isPlayable = isPlayable;
        if (! this.isPlayable){
            this.currentStateAlive = false;
            this.nextStateAlive = false;
        }
    }
        
}
