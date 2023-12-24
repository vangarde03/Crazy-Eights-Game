
/** Game.java
*   Game class for playing crazy eights in commandline
*   To be used with Player, Card, Deck classes
*   @author: vangarde03
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

class Game {

    private char currentSuit; // need in case an 8 is played
    private Card faceUp;
    private Scanner input;
    private Player p1;
    private ArrayList<Card> compHand;
    public Deck cards; // changed to public
    private Boolean computerChangedSuit, playerChangedSuit;

    // sets up the Game object for play
    public Game() {
        cards = new Deck();
        p1 = new Player();
        compHand = new ArrayList<Card>();
        computerChangedSuit = false;
        playerChangedSuit = false;
        input = new Scanner(System.in);

    }

    // Plays a game of crazy eights.
    // Returns true to continue playing and false to stop playing
    public boolean play() {

        System.out.println(startGameMessage());
        cards.shuffle();
        faceUp = cards.deal();
        int counter = 0;
        p1.playerDeckCreation(cards, faceUp);
        compHandCreation(cards);
        System.out.println(compHand.toString());

        Card holder2 = new Card('y', 89);
        Boolean breakingOut = false;
        // Create computer's hand
        while ((cards.canDeal()) && (p1.hand.size() != 0) && (compHand.size() != 0)) {

            String cheat = "cheating";
            String keepTrying = "";
            if (counter != 0) {
                faceUp = holder2;
            }
            counter++;
            System.out.println(faceCard(faceUp));

            playerChangedSuit = false;
            System.out.println(p1.playerHeader());
            System.out.println(p1.handToString());

            Card holder = new Card('n', 33);
            while (cheat.equals("cheating")) {
                Card playerCard = p1.playsTurn(cards);
                System.out.println(whatCardWasPlayed(playerCard));
                keepTrying = playerCardDrawCheck(playerCard);

                if (keepTrying.equals("true")) {
                    continue;
                } else if (keepTrying.equals("yes")) {
                    breakingOut = true;
                    break;
                }

                cheat = p1.antiCheat(playerCard, faceUp, currentSuit, computerChangedSuit);
                System.out.println(gameAntiCheat(cheat, playerCard));
                holder = playerCard;
            }

            if (keepTrying.equals("yes")) {
                break;
            }
            if (!(breakingOut)) {
                faceUp = holder;
                computerChangedSuit = false;
                Card computerCard = computerPlayTurn(cards);
                holder2 = computerCard;

                if ((computerCard.getRank() == 400) && (computerCard.getSuit() == 'i')) {
                    continue;
                }
                if ((computerCard.getRank() == 33) && (computerCard.getSuit() == 'b')) {
                    break;
                }
                System.out.println(computerCardPlayString(computerCard));
                if (p1.hand.size() == 0) {
                    break;
                } else if (compHand.size() == 0) {
                    break;
                }
            } else {
                continue;
            }

        }

        System.out.println(gameWonMessage("no more cards")); // should return a string

        System.out.println("Would you like to play again? (type 'y' or 'n')");
        while (true) {
            String prompt;
            prompt = input.nextLine();
            if (prompt.equals("y")) {
                return true;
            } else if (prompt.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter a valid input!!");
            }
        }

        // Personal Notes:
        // create the deck
        // shuffle the deck
        // pull out the top card
        // have the person play
        // print the up card
        // print the player's hand
    }

    private String gameWonMessage(String message) {
        String defaultString = "none";
        if (message.equals("player")) {
            return "\n Congrats human! You won the game!! :D";
        } else if (message.equals("computer")) {
            return "\n The computer won the game X(";
        } else if (message.equals("no more cards")) {
            if (p1.hand.size() < compHand.size()) {
                return "\nThe deck ran out! Congrats human! You won the game!! :D";
            }
            if (compHand.size() < p1.hand.size()) {
                return "\nThe deck ran out! The computer won the game X(";
            }
        }
        return defaultString;

    }

    private String computerCardPlayString(Card computerCard) {
        if ((computerCard.getRank() == 77) && (computerCard.getSuit() == 'x')) {
            return "";
        }
        if (computerChangedSuit == true) {
            return "";
        }
        return "\nThe computer played a " + computerCard.toString() + "\n";
    }

    private String whatCardWasPlayed(Card playerCard) {
        if ((playerCard.getRank() == 99) && (playerCard.getSuit() == 'z')) {
            return "";
        }
        return "\nYou played a " + playerCard.toString();
    }

    private String gameEnd() {
        return "yes";
    }

    private String compDrawCard() {
        Card specialCard = new Card('z', 99);
        String x;
        x = computerCardDrawCheck(specialCard);
        if (x.equals("can draw")) {
            computerAddCard(cards);
        }
        if (x.equals("yes")) {
            return "game over";
        }
        return "default string";
    }

    private String computerCardDrawCheck(Card computerCard) {
        Boolean defaultBool = false;
        if ((computerCard.getSuit() == 'z') && (computerCard.getRank() == 99)) {
            if (cards.canDeal() == false) {
                return gameEnd();
            }
        }
        return "can draw";
    }

    private String playerCardDrawCheck(Card playerCardInput) { // checks if the player wants to draw a card
        String defaultBool = "false";
        if ((playerCardInput.getSuit() == 'z') && (playerCardInput.getRank() == 99)) {
            Boolean canGameDeal = cards.canDeal();
            if (canGameDeal == false) {
                return gameEnd();
            }
            p1.addCard(cards, faceUp);
            System.out.println("\n####*You decided to draw a card!*####\n");
            System.out.println(faceCard(faceUp));
            System.out.println(p1.playerHeader());
            System.out.println(p1.handToString());
            return "true";
        } else if (!((playerCardInput.getSuit() == 'z') && (playerCardInput.getRank() == 99))) {
            return "false";
        }
        return defaultBool;
    }

    private String gameAntiCheat(String cheating, Card playerCard) { // takes the output from the player anticheat and
                                                                     // works with it
        String defaultString = "";
        if (cheating == "cheating") {
            p1.hand.add(playerCard);
            System.out.println("\n");
            System.out.println(faceCard(faceUp));
            System.out.println(p1.playerHeader());
            System.out.println(p1.handToString());
            return "Please follow the rules bro :(";
        } else if (cheating.equals("h") || cheating.equals("s") || cheating.equals("d") || cheating.equals("c")) {
            return changingTheSuit(cheating);
        }
        return defaultString;
    }

    private String changingTheSuit(String playerCheat) {
        String suitString = "***/Changed suit/***";
        switch (playerCheat) {
            case "c":
                currentSuit = 'c';
                playerChangedSuit = true;
                break;

            case "d":
                currentSuit = 'd';
                playerChangedSuit = true;
                break;

            case "h":
                currentSuit = 'h';
                playerChangedSuit = true;
                break;

            case "s":
                currentSuit = 's';
                playerChangedSuit = true;
                break;

            default:
                currentSuit = 'n';
                break;
        }
        return suitString;
    }

    private String startGameMessage() { // the starting message
        String description = "\nWelcome to Crazy Eights! You'll start with 7 cards.\n";
        String description2 = "\nYour job is to match a card in your hand with the up card.\n";
        String description3 = "\nYou can match it by suit or rank.\n";
        String description4 = "\nIf you run out of cards, you win!\n";
        String description5 = "\nIf you make it through the whole deck, then whoever has the fewest cards left wins!\n";
        String description6 = "\nGood luck!! :)\n";
        return description + description2 + description3 + description4 + description5 + description6;
    }

    private String faceCard(Card faceUpCard) { // Returns the current suit
        String suitString;
        String description;
        Boolean b00l = true;
        while (b00l) {
            if (computerChangedSuit == false) {
                currentSuit = faceUpCard.getSuit();
            } else if (computerChangedSuit == true) {
                break;
            }
            if (((faceUp.getRank() == 00) && (faceUp.getSuit() == 'v'))) {
                faceUp = cards.deal();
            }
            if (!((faceUp.getRank() == 00) && (faceUp.getSuit() == 'v'))) {
                b00l = false;
            }
        }

        switch (currentSuit) {
            case 'c':
                suitString = "Clubs";
                break;
            case 'd':
                suitString = "Diamonds";
                break;
            case 'h':
                suitString = "Hearts";
                break;
            case 's':
                suitString = "Spades";
                break;
            default:
                suitString = "Invalid suit";
        }
        if (computerChangedSuit == true) {
            description = "\nThe current suit is " + suitString;
        }
        if ((faceUpCard.getSuit() != 'c') && (faceUpCard.getSuit() != 'h') && (faceUpCard.getSuit() != 's')
                && (faceUpCard.getSuit() != 'd')) {
            switch (currentSuit) {
                case 'i':
                    suitString = "Clubs";
                    break;
                case 'u':
                    suitString = "Diamonds";
                    break;
                case 'p':
                    suitString = "Hearts";
                    break;
                case 'o':
                    suitString = "Spades";
                    break;
                default:
                    int x = 0;
            }
            description = "\nThe current suit is " + suitString;
        } else {
            description = "** The UP card is the " + faceUp.toString() + " **\nThe current suit is " + suitString;
        }
        return description;
    }

    /*
     * Naive computer player AI that does one of two actions:
     * 1) Plays the first card in their hand that is a valid play
     * 2) If no valid cards, draws until they can play
     */

    private void computerAddCard(Deck deck) {
        Boolean duplicate = true;
        int stuff = 0;
        Card[] gameDeck = deck.getCardDeck();
        System.out.println();
        Boolean b00l = true;
        while (b00l) {
            double randomSelection = (Math.random() / 52) * 52 * 52;
            stuff = (int) randomSelection;
            if (!((gameDeck[stuff].getRank() == 00) && (gameDeck[stuff].getSuit() == 'v'))) {
                b00l = false;
            }
            if ((gameDeck[stuff].getRank() == faceUp.getRank()) && (gameDeck[stuff].getSuit() == faceUp.getSuit())) {
                b00l = true;
            }
        }
        compHand.add(gameDeck[stuff]);
        Card dummyCard = new Card('v', 00);
        gameDeck[stuff] = dummyCard;
        deck.settingDeck(gameDeck);
        // when the player can't match the card, pick a random one from the deck
        // build in code that detects if the deck is empty
    }

    private void compHandCreation(Deck deck) {
        int i = 0;
        while (i < 7) {
            computerAddCard(deck);
            i++;
        }
    }

    private Card computerPlayTurn(Deck deck) {
        Card cardToPlay = new Card('b', 33);
        Card handlerCard = cardToPlay;
        int selection = 0;
        String gameOverCase = "";
        String selectionDetection = "";
        Card specialCard;
        while (!(selectionDetection.equals("valid")) && !(selectionDetection.equals("Clubs"))
                && !(selectionDetection.equals("Hearts")) && !(selectionDetection.equals("Diamonds"))
                && !(selectionDetection.equals("Spades"))) { // prevent this from executing twice
            cardToPlay = compHand.get(selection); // generates an out of bounds error
            selectionDetection = compAntiCheat(cardToPlay, faceUp, selection);
            if (selectionDetection.equals("increase")) {
                selection++;
                if (selection == compHand.size()) {
                    if (compDrawCard().equals("game over")) {
                        gameOverCase = "game over";
                        break;
                    }
                }
                continue;
            }
            if (selectionDetection.equals("valid")) {
                break;
            }
            switch (selectionDetection) {
                case "Clubs":
                    specialCard = new Card('i', 400);
                    computerChangedSuit = true;
                    if (!gameOverCase.equals("game over")) {
                        compHand.remove(selection);
                    } else {
                        return handlerCard;
                    }
                    return specialCard;
                case "Hearts":
                    specialCard = new Card('u', 400);
                    computerChangedSuit = true;
                    if (!gameOverCase.equals("game over")) {
                        compHand.remove(selection);
                    } else {
                        return handlerCard;
                    }
                    return specialCard;
                case "Diamonds":
                    specialCard = new Card('p', 400);
                    computerChangedSuit = true;
                    if (!gameOverCase.equals("game over")) {
                        compHand.remove(selection);
                    } else {
                        return handlerCard;
                    }
                    return specialCard;
                case "Spades":
                    specialCard = new Card('o', 400);
                    computerChangedSuit = true;
                    if (!gameOverCase.equals("game over")) {
                        compHand.remove(selection);
                    } else {
                        return handlerCard;
                    }
                    return specialCard;
                default:
                    computerChangedSuit = false;
                    specialCard = new Card('x', 42);

            }
        }
        if (!gameOverCase.equals("game over")) {
            compHand.remove(selection);
        } else {
            return handlerCard;
        }
        return cardToPlay;
    }

    private String compAntiCheat(Card computerCard, Card currentCardinPlay, int selectionNum) {
        char compCardSuit = computerCard.getSuit();
        int compCardRank = computerCard.getRank();
        char cardInPlaySuit = currentCardinPlay.getSuit();
        int cardInPlayRank = currentCardinPlay.getRank();
        if (playerChangedSuit == true) {
            if (compCardRank == 8) {
                return compChangeSuit(); // also a string
            } else if (compCardSuit != currentSuit) {
                return "increase";
            } else {
                return "valid";
            }
        }
        if (compCardRank == 8) {
            return compChangeSuit(); // also a string
        }
        if ((compCardSuit != cardInPlaySuit) && (compCardRank != cardInPlayRank)) {
            return "increase";
        }
        return "valid";
    }

    private String compChangeSuit() {
        System.out.println("\nThe Computer placed an 8!!");
        String computerSuitChoice = "";
        double choosing = Math.random();
        if ((choosing >= 0) && (choosing <= 0.25)) {
            computerSuitChoice = "Clubs";
            currentSuit = 'c';
        } else if ((choosing > 0.25) && (choosing <= 0.5)) {
            computerSuitChoice = "Diamonds";
            currentSuit = 'd';
        } else if ((choosing > 0.5) && (choosing <= 0.75)) {
            computerSuitChoice = "Hearts";
            currentSuit = 'h';
        } else if ((choosing > 0.75) && (choosing <= 1)) {
            computerSuitChoice = "Spades";
            currentSuit = 's';
        }
        System.out.println("\nThe Computer chose the suit, */* " + computerSuitChoice + " */*");
        computerChangedSuit = true;
        return computerSuitChoice;
    }

}