/** Player.java
*   Player class as part of Crazy Eights
*   To be used with Game, Card, Deck classes
*   @author: vangarde03
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

class Player{
    
    public ArrayList<Card> hand; // the player's hand
    private Scanner input, input2;

    public Player(){
        hand = new ArrayList<Card>();
        input = new Scanner(System.in);
        input2 = new Scanner(System.in);
        //select random cards from the deck
        //add them to the player's hand
    }

    // Creates player's hand
    public void playerDeckCreation(Deck deck, Card faceUp){
        int i = 0;
        while(i<7){
            addCard(deck, faceUp);
            i++;
        }
    }

    // Adds a card to the player's hand
    public void addCard(Deck deck, Card faceUp){
        int stuff = 0;
        Card[] gameDeck = deck.getCardDeck();
        Boolean b00l = true;
        while(b00l){
            double randomSelection = (Math.random()/52)*52*52;
            stuff = (int) randomSelection;
            if(!((gameDeck[stuff].getRank() == 00) && (gameDeck[stuff].getSuit() == 'v'))){
                b00l = false;
            }
            if((gameDeck[stuff].getRank() == faceUp.getRank()) && (gameDeck[stuff].getSuit()==faceUp.getSuit())){
                b00l = true;
            }

        }
        hand.add(gameDeck[stuff]);
        Card dummyCard = new Card('v', 00);
        gameDeck[stuff] = dummyCard;
        deck.settingDeck(gameDeck);
        //when the player can't match the card, pick a random one from the deck
    }
   
    // Covers all the logic regarding a human player's turn
    // public so it may be called by the Game class
    public Card playsTurn(Deck deck){
        Card cardToPlay;
        Card specialCard = new Card('z',99); //this lets the program know if the player decided to draw a card and should later allow them to keep on drawing a card until they can play
        int playerInput;
        //up facing card needs to be implemented in the game class
        //index 0 = input 1, index 1 = input 2, etc.
        playerInput = input.nextInt();
        if(playerInput == 1){
            return specialCard;
        }
        Boolean outOfBounds = true;
        while(outOfBounds){
            if (((playerInput-2) < hand.size()) && ((playerInput-2) >= 0)){
                outOfBounds = false;
            }
            else if(playerInput == 1){
                return specialCard;
            }
            else{
                System.out.println("Please enter a valid number!");
                playerInput = input.nextInt();

            }

        }
        cardToPlay = hand.remove(playerInput-2);
        return cardToPlay;
        //Personal Notes:
        //retrieve the card that is at the top of deck
        //have the player play a card from their deck
        //prevent them from cheating
            //check if rank or suit match
            //keep prompting player if they try to cheat
    }

    public String antiCheat(Card playerCard, Card currentCardinPlay, char currentSuit, Boolean computerChangedSuit){ //should be called in the game class
        char playerCardSuit = playerCard.getSuit();
        int playerCardRank = playerCard.getRank();
        char cardInPlaySuit = currentCardinPlay.getSuit();
        int cardInPlayRank = currentCardinPlay.getRank();
        if(computerChangedSuit == true){
            if((playerCardSuit != currentSuit) && (playerCardRank != 8)){
                if(playerCardRank == 8){
                    return changeSuit();
                }
                else{
                    return "cheating";
                }
            }
            else{
                return "no cheating detected";
            }
        }
        
        if((playerCardSuit != cardInPlaySuit) &&  (playerCardRank != cardInPlayRank)){
            if(playerCardRank == 8){
                return changeSuit();
            }
            else{
                return "cheating";
            }
        }
        if (playerCardRank == 8){
            return changeSuit();
        }
        return "no cheating detected";
    }

    public String changeSuit(){
        System.out.println("\nYou placed an 8!! You can change the suit to Spades (s), Hearts (h), Clubs (c), or Diamonds (d)");
        System.out.println("\nPlease type in one of the above letters in order to change the suit\n");
        String playerInput = input2.nextLine();
        Boolean valid = false;
        char playerChar = playerInput.charAt(0);
        while (!valid){
            if((playerChar == 's') || (playerChar == 'h') || (playerChar == 'd') || (playerChar == 'c')){
                valid = true;
            }
        }
        String playerChar2 = "" + playerChar;
        return playerChar2;
    }


    // Accessor for the players hand
    public ArrayList<Card> getHand(){
        return hand;
    }

    // Returns a printable string representing the player's hand
    public String handToString(){
        String description;
        String cardString;
        description = "1\t\tDraw a Card\n";
        int i = 2;
        for(Card cards: hand){
            cardString = cards.toString();
            description = description + String.format(i+"\t\t" + cardString + "\n");
            i++;
        }        
        String string1 = "Your cards are: \n";
        return string1 + description;
        //format the player's hand
    }

    public String playerHeader(){
        return "\nType in '1' to draw a card, or type the number next to the card in your hand that you wish to play\n";
    }


}
