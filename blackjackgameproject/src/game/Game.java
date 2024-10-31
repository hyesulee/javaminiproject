package game;

import card.Deck;
import player.ComputerPlayerThread;
import player.Dealer;
import player.Player;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Game {
    private Deck deck;
    private List<Player> players;
    private List<ComputerPlayerThread> computerPlayers;
    private Dealer dealer;
    private Scanner scanner = new Scanner(System.in);
    private boolean gameOver = false;

    public Game(String mainPlayerName) {
        this(mainPlayerName, new String[0], 0); 
    }

    public Game(String mainPlayerName, String[] otherPlayerNames, int numComputerPlayers) {
        this.deck = new Deck();
        this.dealer = new Dealer("딜러");
        this.players = new ArrayList<>();
        this.computerPlayers = new ArrayList<>();

        players.add(new Player(mainPlayerName));
        for (String name : otherPlayerNames) {
            players.add(new Player(name.trim()));
        }

        for (int i = 1; i <= numComputerPlayers; i++) {
            Player computer = new Player("컴퓨터" + i);
            ComputerPlayerThread computerThread = new ComputerPlayerThread(computer, deck, this);
            computerPlayers.add(computerThread);
            players.add(computer); 
        }

        deck.shuffle();
    }

    public void startGame() {
        deck.resetAndShuffle();
        dealer.clearHand();
        players.forEach(Player::clearHand);

        System.out.println("게임 시작!");
        players.forEach(this::setPlayerBetAmount);

        // 각 플레이어에게 카드 두 장씩 배분
        for (Player player : players) {
        	  System.out.println("--------------------------------------------------------------");
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
            System.out.println(player.getName() + "님이 받은 카드: " + player.getHand() + " (총 점수: " + player.getScore() + ")");
        }

        // 딜러 카드 배분
        System.out.println("--------------------------------------------------------------");
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        System.out.println("딜러가 받은 카드: " + dealer.getHand().get(0) + " 와 [비공개 카드]");
        System.out.println("--------------------------------------------------------------");
        // 모든 플레이어의 턴 실행
        for (Player player : players) {
            if (gameOver) break;
            takeTurn(player);
        }

        // 컴퓨터 플레이어의 턴 실행
        if (!gameOver) {
            computerPlayers.forEach(Thread::start);
            // 딜러의 턴 실행
            dealer.takeTurn(deck);
        }

        // 게임 종료
        endGame();
    }

    private void takeTurn(Player player) {
        System.out.println(player.getName() + "님의 턴입니다. 현재 배팅 금액: $" + player.getBetAmount());

        if (player.getName().startsWith("컴퓨터")) {
            while (player.getScore() < 17 && !gameOver) {
                hit(player);
                if (player.getScore() > 21) {
                    System.out.println(player.getName() + "님이 총 " + player.getScore() + "점으로 버스트되었습니다.");
                    return;
                }
               
            }
            // 스탠드 메시지와 보유 카드, 총 점수 출력
            if (!gameOver) {
            	  System.out.println("--------------------------------------------------------------");
                System.out.println(player.getName() + "님이 스탠드하였습니다. 현재 보유 카드: " + player.getHand() + ", 총 점수: " + player.getScore());
                System.out.println("--------------------------------------------------------------");
            }
        } else {
            boolean playerTurnOver = false;
            while (!playerTurnOver && !gameOver) {
                int action = getPlayerAction(player);
                switch (action) {
                    case 1 -> {
                    	  System.out.println("--------------------------------------------------------------");
                        hit(player);
                        if (player.getScore() > 21) {
                            System.out.println(player.getName() + "님이 총 " + player.getScore() + "점으로 버스트되었습니다.");
                            playerTurnOver = true;
                        }
                        System.out.println("--------------------------------------------------------------");
                    }
                    case 2 -> {
                    	  System.out.println("--------------------------------------------------------------");
                        playerTurnOver = true;
                        System.out.println(player.getName() + "님이 스탠드하였습니다. 현재 보유 카드: " + player.getHand() + ", 총 점수: " + player.getScore());
                        System.out.println("--------------------------------------------------------------");
                    }
                    case 3 -> {
                    	  System.out.println("--------------------------------------------------------------");
                        doubleDown(player);
                        System.out.println("--------------------------------------------------------------");
                        playerTurnOver = true;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                }
            }
        }
    }



    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        if (gameOver) return;
        gameOver = true;
        System.out.println("\n게임 종료!");

        int dealerScore = dealer.getScore();
        int totalBetAmount = calculateTotalBetAmount();
        Player winner = null;

        // 최종 점수 요약
        System.out.println("\n최종 점수:");

        // 각 플레이어의 최종 점수를 출력
        for (Player player : players) {
            int playerScore = player.getScore();
            System.out.println(player.getName() + "님의 총점: " + playerScore);

            // 버스트 처리 및 승패 결정
            if (playerScore > 21) {
                System.out.println(player.getName() + "님이 총 " + playerScore + "점으로 버스트되었습니다.");
            } else if (playerScore <= 21 && (dealerScore > 21 || playerScore > dealerScore)) {
                if (winner == null || playerScore > winner.getScore()) {
                    winner = player;
                }
            }
        }

        // 딜러의 점수 처리
        if (dealerScore > 21) {
            System.out.println("딜러가 총 " + dealerScore + "점으로 버스트되었습니다!");
        } else {
            System.out.println("딜러의 최종 점수: " + dealerScore);
        }

        // 승리 처리
        if (winner != null) {
            System.out.println("\n" + winner.getName() + "님이 승리하여 $" + totalBetAmount + "을 가져갑니다!");
            winner.addWinnings(totalBetAmount);
        } else {
            System.out.println("\n딜러가 모든 배팅 금액을 가져갑니다.");
        }
    }


    private int getPlayerAction(Player player) {
        System.out.print(player.getName() + "님, 행동을 선택하세요 (히트:1, 스탠드:2, 더블 다운:3): ");
        while (true) {
            try {
                int action = Integer.parseInt(scanner.nextLine().trim());
                if (action >= 1 && action <= 3) return action;
                else System.out.print("1, 2, 3 중 하나를 선택하세요: ");
            } catch (NumberFormatException e) {
                System.out.print("유효한 숫자를 입력하세요: ");
            }
        }
    }

    private void setPlayerBetAmount(Player player) {
        if (player.getName().startsWith("컴퓨터")) {
            int betAmount = new Random().nextInt(151) + 50;
            player.setBetAmount(betAmount);
            System.out.println(player.getName() + "님의 배팅 금액이 $" + betAmount + "으로 자동 설정되었습니다.");
        } else {
            System.out.print(player.getName() + "님의 배팅 금액을 입력하세요: ");
            while (true) {
                try {
                    int betAmount = Integer.parseInt(scanner.nextLine().trim());
                    if (betAmount > 0) {
                        player.setBetAmount(betAmount);
                        System.out.println(player.getName() + "님의 배팅 금액이 $" + betAmount + "으로 설정되었습니다.");
                        break;
                    } else {
                        System.out.print("배팅 금액은 0보다 커야 합니다. 다시 입력하세요: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("유효한 숫자를 입력하세요: ");
                }
            }
        }
    }

    public void hit(Player player) {
        player.addCard(deck.drawCard());
        System.out.println(player.getName() + "님이 히트를 선택하여 받은 카드: " + player.getHand() + " (총 점수: " + player.getScore() + ")");
    }

    public void doubleDown(Player player) {
        int newBetAmount = player.getBetAmount() * 2;
        player.setBetAmount(newBetAmount);
        System.out.println(player.getName() + "님의 배팅 금액이 $" + newBetAmount + "으로 두 배가 되었습니다.");
        player.addCard(deck.drawCard());
        System.out.println(player.getName() + "님이 더블 다운으로 받은 카드: " + player.getHand() + " (총 점수: " + player.getScore() + ")");
    }

    private int calculateTotalBetAmount() {
        return players.stream().mapToInt(Player::getBetAmount).sum();
    }
}
