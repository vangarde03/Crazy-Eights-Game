/** CrazyEights.java
*   Plays a game of crazy eights in commandline
*   Keeps playing while the game play method returns true
*   This file is used to start the game
*   @author: vangarde03
*/

class CrazyEights{
    public static void main(String[] args){
        boolean keepPlaying = true;

        while(keepPlaying){
            Game g = new Game();
            keepPlaying = g.play();
        }
    }
}