package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rooftop extends JPanel {
    private final MainFrame mainFrame;

    public Rooftop(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true); // 키 이벤트를 받을 수 있게 설정
        requestFocusInWindow();

        // 현재 위치 표시 라벨
        JLabel locationLabel = new JLabel("옥상", SwingConstants.RIGHT);
        locationLabel.setForeground(new Color(0xBBF3C0));
        locationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        locationLabel.setBounds(680, 10, 180, 30); // 오른쪽 위에 배치
        add(locationLabel);

        // Rooftop 내부 구조 그리기
        RooftopDrawingPanel rooftopPanel = new RooftopDrawingPanel();
        rooftopPanel.setBounds(0, 0, 900, 600);
        add(rooftopPanel);
    }

    /**
     * 옥상 내부 구조를 그리는 패널
     */
    private class RooftopDrawingPanel extends JPanel {
        private final Rectangle elevatorArea = new Rectangle(20, 100, 180, 300); // 엘리베이터 영역
        private final Rectangle flowerbed1Area = new Rectangle(660, 325, 125, 75); // 화단 1
        private final Rectangle flowerbed2Area = new Rectangle(785, 325, 125, 75); // 화단 2

        public RooftopDrawingPanel() {
            setOpaque(false);

            // 마우스 클릭 이벤트 추가
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Point clickPoint = e.getPoint();

                    if (elevatorArea.contains(clickPoint)) {
                        mainFrame.switchToPanel(new ElevatorScreen(mainFrame)); // 엘리베이터 화면으로 이동
                    } else if (flowerbed1Area.contains(clickPoint)) {
                        handleFlowerbed1Click();
                    } else if (flowerbed2Area.contains(clickPoint)) {
                        handleFlowerbed2Click();
                    }
                }
            });

            // 마우스 이동 이벤트 추가
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    Point movePoint = e.getPoint();
                    if (elevatorArea.contains(movePoint) || flowerbed1Area.contains(movePoint) || flowerbed2Area.contains(movePoint)) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 손 모양으로 변경
                    } else {
                        setCursor(Cursor.getDefaultCursor()); // 기본 커서로 변경
                    }
                }
            });
        }

        private void handleFlowerbed1Click() {
            if (!mainFrame.getInventory().hasItem("장난감 삽")) {
                showSimpleMessage("흙을 파헤칠 무언가가 필요할 것 같다...");
                return;
            }
            showSimpleMessage("비어있다...");
        }

        private void handleFlowerbed2Click() {
            if (!mainFrame.getInventory().hasItem("장난감 삽")) {
                showSimpleMessage("흙을 파헤칠 무언가가 필요할 것 같다...");
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                this,
                "밧줄이 있다.\n가져가시겠습니까?",
                "화단 2",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                mainFrame.getInventory().addItem("밧줄"); // 밧줄을 아이템 창에 추가
                mainFrame.getInventory().updateItems(); // 아이템 창 갱신
                showSimpleMessage("밧줄을 가져갔습니다.");
            }
        }

        private void showSimpleMessage(String message) {
            JOptionPane.showMessageDialog(
                this,
                message,
                "알림",
                JOptionPane.PLAIN_MESSAGE
            );
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 선 색상 설정
            g.setColor(new Color(0xBBF3C0));

            // 옥상 바닥
            g.drawLine(0, 400, 900, 400);

            // 엘리베이터 문
            g.drawRect(20, 100, 180, 300); // 엘리베이터 본체
            g.drawLine(110, 100, 110, 400); // 엘리베이터 문
            g.drawRect(80, 60, 60, 30); // 층수 표시 칸
            g.drawString("R", 105, 80); // 층수 텍스트 추가

            // 엘리베이터 구분선
            g.drawLine(220, 0, 220, 400);

            // 난간
            g.drawLine(220, 250, 900, 250);
            g.drawLine(265, 250, 265, 400);
            g.drawLine(310, 250, 310, 400);
            g.drawLine(355, 250, 355, 400);
            g.drawLine(400, 250, 400, 400);
            g.drawLine(445, 250, 445, 400);
            g.drawLine(490, 250, 490, 400);
            g.drawLine(535, 250, 535, 400);
            g.drawLine(580, 250, 580, 400);
            g.drawLine(625, 250, 625, 400);
            g.drawLine(670, 250, 670, 325);
            g.drawLine(715, 250, 715, 325);
            g.drawLine(760, 250, 760, 325);
            g.drawLine(805, 250, 805, 325);
            g.drawLine(850, 250, 850, 325);
            g.drawLine(895, 250, 895, 325);

            // 화단
            g.drawRect(660, 325, 125, 75); // 화단 1
            g.drawRect(785, 325, 125, 75); // 화단 2
        }
    }
}
