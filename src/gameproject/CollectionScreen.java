package gameproject;

import javax.swing.*;
import java.awt.*;

public class CollectionScreen extends JPanel {
    private EndingBox[] endingBoxes; // 엔딩 정보를 표시하는 박스들

    public CollectionScreen(MainFrame mainFrame) {
        setBackground(Color.BLACK);
        setLayout(null);

        // 맨 위 공간
        JLabel titleLabel = new JLabel("수집", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0xBBF3C0));
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        titleLabel.setBounds(395, 20, 100, 50);
        add(titleLabel);

        // 왼쪽 화살표
        JLabel backArrow = new JLabel("<", SwingConstants.CENTER);
        backArrow.setForeground(new Color(0xBBF3C0));
        backArrow.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        backArrow.setBounds(20, 20, 50, 50);
        backArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainFrame.switchToPanel(new gameStartScreen(mainFrame)); // 게임 시작 화면으로 돌아가기
            }
        });
        add(backArrow);

        // 네 개의 엔딩 박스
        endingBoxes = new EndingBox[4];
        int startX = 25;
        int startY = 100;
        int width = 400;
        int height = 200;
        int gap = 40;

        for (int i = 0; i < endingBoxes.length; i++) {
            EndingBox endingBox = new EndingBox("???"); // 초기에는 ???로 설정
            endingBox.setBounds(
                startX + (i % 2) * (width + gap),
                startY + (i / 2) * (height + gap),
                width, height
            );
            endingBoxes[i] = endingBox;
            add(endingBox);
        }
    }

    /**
     * 특정 엔딩을 해제하여 엔딩 이름을 표시
     */
    public void unlockEnding(int index, String endingName) {
        if (index >= 0 && index < endingBoxes.length) {
            endingBoxes[index].setEndingName(endingName); // 해당 엔딩 박스의 이름 업데이트
        }
    }

    /**
     * 엔딩 정보를 표시하는 커스터마이즈된 JPanel
     */
    private static class EndingBox extends JPanel {
        private JLabel endingLabel; // 엔딩 이름 라벨

        public EndingBox(String endingName) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(new Color(0xBBF3C0)));
            setLayout(new BorderLayout());

            endingLabel = new JLabel(endingName, SwingConstants.CENTER);
            endingLabel.setForeground(new Color(0xBBF3C0));
            endingLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
            add(endingLabel, BorderLayout.CENTER);
        }

        /**
         * 엔딩 이름 업데이트
         */
        public void setEndingName(String endingName) {
            endingLabel.setText(endingName);
        }
    }
}
