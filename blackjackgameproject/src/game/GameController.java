package game;

import java.util.Scanner;

public class GameController {
    public static final int MAX_PLAYERS = 4;
    private Scanner scanner = new Scanner(System.in);

    public void runGame() {
        while (true) {
            String mode = selectMode();
            Game game = createGame(mode);

            game.startGame();  // `startGame` 메서드가 `Game` 클래스에 있는 턴 로직을 포함합니다.

            if (!askReplay()) {
                System.out.println("게임을 종료합니다.");
                break;
            }
        }
        scanner.close();
    }

    private String selectMode() {
        String modeInput;
        while (true) {
            System.out.print("싱글 또는 멀티플레이 모드 중 선택하세요 (싱글:1/멀티:2): ");
            modeInput = scanner.nextLine().trim();
            if (modeInput.equals("1") || modeInput.equals("2")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. 1 또는 2를 입력하세요.");
            }
        }
        return modeInput.equals("1") ? "single" : "multi";
    }

    private Game createGame(String mode) {
        if (mode.equals("single")) {
            System.out.print("플레이어 이름을 입력하세요: ");
            String playerName = scanner.nextLine().trim();
            return new Game(playerName);
        } else {
            System.out.print("플레이어 이름을 입력하세요: ");
            String mainPlayerName = scanner.nextLine().trim();
            System.out.print("다른 플레이어들의 이름을 입력하세요 (쉼표로 구분): ");
            String otherPlayers = scanner.nextLine().trim();
            int numComputerPlayers = getNumComputerPlayers();
            return new Game(mainPlayerName, otherPlayers.split(","), numComputerPlayers);
        }
    }

    private int getNumComputerPlayers() {
        System.out.print("추가로 컴퓨터와 대결하시겠습니까? (예:1/아니오:2): ");
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.equals("1")) { 
                return promptComputerPlayerCount();
            } else if (response.equals("2")) { 
                return 0;
            } else {
                System.out.print("잘못된 입력입니다. 예:1 또는 아니오:2를 입력하세요: ");
            }
        }
    }

    private int promptComputerPlayerCount() {
        System.out.print("컴퓨터 플레이어 수를 입력하세요 (최대 " + MAX_PLAYERS + "명, 없으면 0 입력): ");
        int numComputerPlayers = 0;
        while (true) {
            try {
                numComputerPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numComputerPlayers >= 0 && numComputerPlayers <= MAX_PLAYERS) {
                    break;
                } else {
                    System.out.print("0에서 " + MAX_PLAYERS + " 사이의 숫자를 입력해주세요: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("유효한 숫자를 입력하세요: ");
            }
        }
        return numComputerPlayers;
    }

    private boolean askReplay() {
        System.out.print("다시 플레이하시겠습니까? (yes:1/no:2): ");
        String replay = scanner.nextLine().trim();
        return replay.equalsIgnoreCase("yes") || replay.equals("1");
    }
}
