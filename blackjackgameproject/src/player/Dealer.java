package player;

import card.Deck;

public class Dealer extends Player {
    public Dealer(String name) {
        super(name);
    }

    public void takeTurn(Deck deck) {
        System.out.println("딜러의 턴입니다.");
        while (getScore() < 17) {
            addCard(deck.drawCard());
            System.out.println("딜러가 카드를 받았습니다. 현재 카드: " + getHand() + ", 점수: " + getScore());
        }
        
        // 최종 점수와 카드 출력
        System.out.println("딜러가 스탠드하였습니다. 최종 카드: " + getHand() + ", 최종 점수: " + getScore());
    }
}
