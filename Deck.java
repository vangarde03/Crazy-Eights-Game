 /** Deck.java
*   Models a typical deck of playing cards
*   To be used with Card class
*   @author: vangarde03
*/
import java.lang.Math;
class Deck{

    public Card[] deck; // contains the cards to play with          made this public
    public int top; // controls the "top" of the deck to deal from

    // constructs a default Deck
    public Deck(){
        deck = new Card[52];
        int[] rank = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        char[] suit = {'c','h','s','d'};
        int i = 0;
        for(char suitLetter: suit){
            for(int rankNumber: rank){
                Card deckCard = new Card(suitLetter,rankNumber);
                deck[i] = deckCard;
                i++; 
            }
        }
        top = 0;
        //use nested for loops to build a deck
    }

    // Deals the top card off the deck
    public Card deal(){ //this fails to randomize the cards (also make sure to change top to a different card if the card is like v, 00)
        shuffle();
        Card top_card = deck[top];
        top++;
        return top_card;
    }


    // returns true provided there is a card left in the deck to deal
    public boolean canDeal(){ //this will generate an error (change structure so that it checks if the cards a all special cases it ends the game)
        int counter = 0;
        for(Card playing: deck){
            if ((playing.getRank() == 00) && (playing.getSuit() == 'v')){
                counter++;
                }
        }
        if(top>51 || counter>=50){
            return false;
        }
        else{
            return true;
        }
    }

    // Shuffles the deck
    public void shuffle(){
        int stuff = 0;
        int stuff2 = 0;
        Card swap1 = new Card('v',00);
        Card swap2 = new Card('v', 00);
        int x = 0;
        while(x<500){
            double randomSelection = (Math.random()/52)*52*52;
            stuff = (int) randomSelection;
            double randomSelection2 = (Math.random()/52)*52*52;
            stuff = (int) randomSelection2;
            swap1 = deck[stuff];
            swap2 = deck[stuff2];
            deck[stuff] = swap2;
            deck[stuff2] = swap1;
            x++;
        }
    }

    //returns the current deck
    public Card[] getCardDeck(){
        return deck;
    }

    //resets the deck based on certain player & compuer actions
    public void settingDeck(Card[] inputDeck){
        deck = inputDeck;
    }

    // Returns a string representation of the whole deck
    public String toString(){
        String header = "Cards in Deck:\n";
        String list = "";
        for(Card inDeck: deck){
           list = list + "\n -->   " + inDeck.toString();
       }
       return header + list;
    }

}