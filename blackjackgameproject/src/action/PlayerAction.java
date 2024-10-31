package action;

/**
 * 플레이어의 행동 인터페이스
 * 블랙잭에서의 기본 행동을 정의합니다.
 */
public interface PlayerAction {
    void hit();
    void stand();
    void doubleDown(); // 더블 다운 추가
}
