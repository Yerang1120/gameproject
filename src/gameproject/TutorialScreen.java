package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TutorialScreen extends JPanel {
    private final MainFrame mainFrame;
    private final String[] tutorialMessages = {
        "저희 GENEX CORPORATION의 실험에 참여하신 것을 환영합니다.",
        "환자분은 병동 내부를 자유롭게 돌아다니실 수 있습니다.",
        "서랍이나 이불 등을 클릭하여 조사할 수 있고, 엘리베이터를 타고 각 층을 이동할 수 있습니다.",
        "또한 얻은 아이템은 'E'키를 눌러 확인하실 수 있습니다.",
        "하지만 섣불리 행동하고 돌아다니지 마시길...",
        "특히 지하 1층은 환자 출입 금지 구역이므로 조심해주시길 바랍니다.",
        "편안히 지내시길 바랍니다."
    };
    private int currentMessageIndex = 0; // 현재 대화창 단계
    private final JLabel dialogueLabel; // 대화창 라벨

    public TutorialScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        // 현재 위치 표시 라벨 추가
        JLabel locationLabel = new JLabel("병실 404호", SwingConstants.RIGHT);
        locationLabel.setForeground(new Color(0xBBF3C0));
        locationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        locationLabel.setBounds(680, 10, 180, 30); // 오른쪽 위에 배치
        add(locationLabel);

        // 병실 내부 구조 그리기
        RoomDrawingPanel roomPanel = new RoomDrawingPanel();
        roomPanel.setBounds(0, 0, 900, 600);
        add(roomPanel);

        // 대화창 추가
        dialogueLabel = new JLabel(tutorialMessages[currentMessageIndex], SwingConstants.CENTER);
        dialogueLabel.setForeground(new Color(0xBBF3C0));
        dialogueLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        dialogueLabel.setBounds(50, 500, 800, 50); // 화면 하단에 배치
        add(dialogueLabel);

        // 화면 클릭 이벤트 처리
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                advanceDialogue(); // 대화 진행
            }
        });
    }

    /**
     * 대화를 진행시키는 메서드
     */
    private void advanceDialogue() {
        currentMessageIndex++;
        if (currentMessageIndex < tutorialMessages.length) {
            dialogueLabel.setText(tutorialMessages[currentMessageIndex]); // 다음 메시지로 변경
        } else {
            mainFrame.switchToPanel(new gameStartScreen(mainFrame)); // 튜토리얼 종료 후 시작 화면으로 이동
        }
    }

    /**
     * 병실 내부 구조를 그리는 패널
     */
    private class RoomDrawingPanel extends JPanel {
        public RoomDrawingPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 배경 색상 설정
            setBackground(Color.BLACK);

            // 선 색상 설정 (0xBBF3C0)
            Color lineColor = new Color(0xBBF3C0);
            g.setColor(lineColor);

            // 미닫이 문 (왼쪽)
            g.drawRect(50, 70, 300, 430); // 문틀
            g.drawRect(300, 250, 30, 100); // 문 손잡이
            g.drawRect(80, 95, 240, 120); // 창문 

            // 바닥
            g.drawLine(0, 500, 900, 500);

            // 침대 (오른쪽)
            g.drawRect(550, 400, 350, 100); // 침대 프레임
            g.drawRect(550, 300, 70, 100); // 침대 헤드
            g.drawRect(620, 360, 50, 40); // 베개
            g.drawRect(690, 360, 210, 40); // 이불

            // 서랍 (침대 아래)
            g.drawRect(370, 300, 160, 200); // 서랍 프레임
            g.drawRect(390, 320, 120, 70); // 상단 서랍
            g.drawOval(440, 345, 20, 20); // 상단 서랍 손잡이
            g.drawRect(390, 410, 120, 70); // 하단 서랍
            g.drawOval(440, 435, 20, 20); // 하단 서랍 손잡이
        }
    }
}
