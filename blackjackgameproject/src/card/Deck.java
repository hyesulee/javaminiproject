// Deck.java
package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    // 덱 초기화 및 셔플 메서드
    public void resetAndShuffle() {
        cards.clear();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = {"하트", "다이아몬드", "클럽", "스페이드"};
        for (String suit : suits) {
            for (int value = 1; value <= 13; value++) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}
