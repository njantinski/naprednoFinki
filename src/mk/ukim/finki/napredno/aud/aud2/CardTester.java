package mk.ukim.finki.napredno.aud.aud2;

import java.util.Objects;
import java.util.Random;

class PlayingCard{
    enum TYPE{
        HEARTS,
        DIAMONDS,
        SPADES,
        ACES
    }
    private int value;
    private TYPE color;

    public PlayingCard(int value, TYPE color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public TYPE getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayingCard)) return false;
        PlayingCard that = (PlayingCard) o;
        return value == that.value &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, color);
    }

    @Override
    public String toString() {
        return "PlayingCard{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }
}

class PlayingDeck{
    private PlayingCard[] cards;
    private boolean[] picked;
    private int total;

    public PlayingDeck() {
        cards = new PlayingCard[52];
        for(int i = 0; i < PlayingCard.TYPE.values().length; i++){
            for(int j = 0; j < 13; j++){
                cards[i*13 + j] =new PlayingCard(j + 1, PlayingCard.TYPE.values()[i]);
            }
        }
        picked = new boolean[52];
        total = 52;
    }

    public int leftCards(){
        return total;
    }
    public PlayingCard dealCard(){
        if(total == 0){
            return null;
        }
        Random random = new Random();
        int a = random.nextInt(51) + 1;
        while(picked[a] == true){
            return dealCard();
        }
        picked[a] = true;
        total --;
        return cards[a-1];
    }

}


public class CardTester {
    public static void main(String[] args) {
        PlayingDeck spil = new PlayingDeck();
        while(spil.dealCard().equals(new PlayingCard(10, PlayingCard.TYPE.HEARTS))){
            spil.dealCard();
        }
        System.out.println(spil.leftCards());
    }
}
