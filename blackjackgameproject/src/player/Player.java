// Player.java
package player;

import card.Card;
import card.Deck;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private List<Card> hand;// 플레이어의 손에 있는 카드 목록
    private Scanner scanner = new Scanner(System.in);
    private int betAmount; // 현재 배팅 금액
    private int totalWinnings = 0;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }
//금액 설정 메서드
    public void setBetAmount(int amount) {
        this.betAmount = amount;
    }
//현재 배팅금액 반환 메서드
    public int getBetAmount() {
        return betAmount;
    }

    public int getScore() {
        int score = hand.stream().mapToInt(Card::getValue).sum();
        long aceCount = hand.stream().filter(card -> card.getValue() == 1).count();

        while (aceCount > 0 && score + 10 <= 21) {
            score += 10;
            aceCount--;
        }

        return score;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void addWinnings(int amount) {
        totalWinnings += amount;
        System.out.println(name + "님의 현재 총 보유 금액: $" + totalWinnings);
    }

    // 플레이어의 턴을 진행하는 메서드
    public void takeTurn(Deck deck) {
        System.out.println(name + "님의 턴입니다. 현재 배팅 금액: $" + betAmount);
        while (true) {
            System.out.print(name + "님, 행동을 선택하세요 (hit/stand/double): ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("hit")) {
                addCard(deck.drawCard());
                System.out.println(name + "님의 현재 카드: " + hand + ", 점수: " + getScore());
                if (getScore() > 21) {
                    System.out.println(name + "님이 버스트되었습니다!");
                    break;
                }
            } else if (action.equals("stand")) {
                System.out.println(name + "님이 스탠드하였습니다.");
                break;
            } else if (action.equals("double")) {
                betAmount *= 2;  // 더블 다운 시 배팅 금액 두 배로 증가
                addCard(deck.drawCard());
                System.out.println(name + "님의 현재 카드: " + hand + ", 점수: " + getScore());
                if (getScore() > 21) {
                    System.out.println(name + "님이 버스트되었습니다!");
                }
                System.out.println("더블 다운으로 인해 턴이 종료됩니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. 'hit', 'stand' 또는 'double'을 입력해주세요.");
            }
        }
    }
}
