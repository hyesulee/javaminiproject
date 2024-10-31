package player;

import card.Deck;
import game.Game;

public class ComputerPlayerThread extends Thread {
    private Player player;
    private Deck deck;
    private Game game;

    public ComputerPlayerThread(Player player, Deck deck, Game game) {
        this.player = player;
        this.deck = deck;
        this.game = game;
    }

    @Override
    public void run() {
        // 게임이 종료되지 않고, 플레이어의 점수가 17 미만일 때 카드 추가
        while (!game.isGameOver() && player.getScore() < 17) {
            player.addCard(deck.drawCard());
            System.out.println(player.getName() + "님이 히트를 선택하여 받은 카드: " + player.getHand() + " (총 점수: " + player.getScore() + ")");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // 점수가 21을 초과하지 않으면 스탠드 메시지를 출력
        if (!game.isGameOver()) {
            System.out.println(player.getName() + "님이 스탠드하였습니다.");
        }
    }
}
