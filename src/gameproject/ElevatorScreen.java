package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ElevatorScreen extends JPanel {
    private final MainFrame mainFrame;

    public ElevatorScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        // 엘리베이터 본체 그리기
        add(new ElevatorDrawingPanel());

        setFocusable(true); // 키 이벤트를 받을 수 있게 설정
        requestFocusInWindow();
    }

    /**
     * 엘리베이터 내부를 그리는 패널
     */
    private class ElevatorDrawingPanel extends JPanel {
        public ElevatorDrawingPanel() {
            setBounds(0, 0, 900, 600);
            setOpaque(false);

            // 마우스 이벤트 추가
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (new Rectangle(770, 100, 100, 50).contains(e.getPoint())) {
                        mainFrame.switchToPanel(new Rooftop(mainFrame)); // 옥상
                    } else if (new Rectangle(770, 170, 100, 50).contains(e.getPoint())) {
                        mainFrame.switchToPanel(new FourthFloorHallway(mainFrame)); // 4층
                    } else if (new Rectangle(770, 240, 100, 50).contains(e.getPoint())) {
                        mainFrame.switchToPanel(new ThirdFloorHallway(mainFrame)); // 3층
                    } else if (new Rectangle(770, 310, 100, 50).contains(e.getPoint())) {
                        mainFrame.switchToPanel(new SecondFloorHallway(mainFrame)); // 2층
                    } else if (new Rectangle(770, 380, 100, 50).contains(e.getPoint())) {
                        mainFrame.switchToPanel(new FirstFloorHallway(mainFrame)); // 1층
                    } else if (new Rectangle(770, 450, 100, 50).contains(e.getPoint())) {
                        if (mainFrame.getInventory().hasItem("방독면")) {
                            mainFrame.switchToPanel(new Ending4(mainFrame)); // 엔딩 4
                        } else {
                            mainFrame.switchToPanel(new Ending3(mainFrame)); // 엔딩 3
                        }
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    boolean onClickableArea = false;

                    // 클릭 가능한 영역 검사
                    if (new Rectangle(770, 100, 100, 50).contains(e.getPoint()) || // 옥상
                        new Rectangle(770, 170, 100, 50).contains(e.getPoint()) || // 4층
                        new Rectangle(770, 240, 100, 50).contains(e.getPoint()) || // 3층
                        new Rectangle(770, 310, 100, 50).contains(e.getPoint()) || // 2층
                        new Rectangle(770, 380, 100, 50).contains(e.getPoint()) || // 1층
                        new Rectangle(770, 450, 100, 50).contains(e.getPoint())) { // 지하 1층
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        onClickableArea = true;
                    }

                    if (!onClickableArea) {
                        setCursor(Cursor.getDefaultCursor());
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 선 색상 설정
            g.setColor(new Color(0xBBF3C0));

            // 엘리베이터 본체
            g.drawRect(150, 0, 600, 600); // 엘리베이터 외곽
            g.drawLine(450, 0, 450, 600); // 엘리베이터 문 경계선
            g.drawRect(15, 200, 120, 200); // 왼쪽 버튼 영역
            g.drawRect(25, 220, 100, 160); // 카드키 인식 영역

            // 버튼 그리기
            g.drawRect(770, 100, 100, 50); // 옥상 버튼
            g.drawRect(770, 170, 100, 50); // 4층 버튼
            g.drawRect(770, 240, 100, 50); // 3층 버튼
            g.drawRect(770, 310, 100, 50); // 2층 버튼
            g.drawRect(770, 380, 100, 50); // 1층 버튼
            g.drawRect(770, 450, 100, 50); // 지하 1층 버튼

            // 버튼 텍스트 추가
            g.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            g.drawString("옥상", 805, 130);
            g.drawString("4층", 805, 200);
            g.drawString("3층", 805, 270);
            g.drawString("2층", 805, 340);
            g.drawString("1층", 805, 410);
            g.drawString("지하 1층", 790, 480);

            // 위치 표시 라벨
            g.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            g.drawString("엘리베이터", 780, 50);
        }
    }
}
