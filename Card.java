/** Card.java
*    Models a typical playing card
*    @author: vangarde03
*/

class Card{
    
    private char suit;
    private int rank;

    // Initializes a card instance
    public Card(char suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }

    // Accessor for suit
    public char getSuit(){
        return suit;
    }
    
    // Accessor for rank
    public int getRank(){
        return rank;
    }

    // Returns a human-readable form of the card (e.g. King of Diamonds)
    public String toString(){
        String suitString;
        String rankString;
        
        switch(rank){
        case 1:
            rankString="Ace of ";
            break;
        case 11:
            rankString="Jack of ";
            break;
        case 12:
            rankString="Queen of ";
            break;
        case 13:
            rankString="King of ";
            break;
        default:
            rankString = rank + " of ";
        }
        switch(suit){
        case 'c':
            suitString="Clubs";
            break;
        case 'd':
            suitString="Diamonds";
            break;
        case 'h':
            suitString="Hearts";
            break;
        case 's':
            suitString="Spades";
            break;
        default:
            suitString="Invalid suit";
        }
        return rankString+suitString;
    }
}