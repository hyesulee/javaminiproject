package game;

import card.Card;

/**
 * 유틸리티 클래스
 * 게임 내에서 사용하는 유용한 기능들을 제공합니다.
 */
public class Utils {
    
    /**
     * 카드 정보를 출력하는 메서드
     *
     * @param card 출력할 카드 객체
     */
    public static void displayCard(Card card) {
        System.out.println("카드: " + card);
    }
}
