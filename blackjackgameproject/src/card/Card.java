package card;

/**
 * 카드 클래스
 * 각 카드의 무늬와 값을 저장하고, 정보를 제공하는 메서드를 포함합니다.
 */
public class Card {
    private String suit; // 카드의 무늬 (예: 하트, 다이아몬드, 클럽, 스페이드)
    private int value;   // 카드의 값 (1~13 사이의 정수로 표현)

    /**
     * 카드 생성자
     * 무늬와 값을 받아 카드 객체를 초기화합니다.
     *
     * @param suit 카드의 무늬 (하트, 다이아몬드 등)
     * @param value 카드의 값 (1~13 사이의 정수)
     */
    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * 카드의 무늬를 반환하는 메서드
     * 
     * @return 카드의 무늬
     */
    public String getSuit() {
        return suit;
    }

    /**
     * 카드의 값을 반환하는 메서드
     * 
     * @return 카드의 값
     */
    public int getValue() {
        return value;
    }

    /**
     * 카드 정보를 문자열로 반환하는 메서드
     * 예) "Hearts 5" 또는 "Spades 10"
     *
     * @return 카드의 무늬와 값을 문자열로 반환
     */
    @Override
    public String toString() {
        return suit + " " + value;
    }
}
