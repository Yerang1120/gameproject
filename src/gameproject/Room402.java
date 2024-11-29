package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Room402 extends JPanel {
    private final MainFrame mainFrame;

    public Room402(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);
        
        setFocusable(true); // 키 이벤트를 받을 수 있게 설정
        requestFocusInWindow();

        // 오른쪽 위에 현재 위치 표시 라벨 추가
        JLabel locationLabel = new JLabel("병실 402호", SwingConstants.RIGHT);
        locationLabel.setForeground(new Color(0xBBF3C0));
        locationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        locationLabel.setBounds(680, 10, 180, 30); // 오른쪽 위에 배치
        add(locationLabel); // 반드시 먼저 추가하여 다른 컴포넌트 위에 나타나도록 함

        // 병실 내부 구조를 표시하는 패널 추가
        RoomDrawingPanel roomPanel = new RoomDrawingPanel();
        roomPanel.setBounds(0, 0, 900, 600);
        add(roomPanel);

        // 문 클릭 이벤트: 바로 복도로 이동
        roomPanel.addClickableArea(50, 70, 300, 430, "문 클릭됨", () -> mainFrame.switchToPanel(new FourthFloorHallway(mainFrame)));

        // 이불 클릭 이벤트
        roomPanel.addClickableArea(690, 360, 210, 40, "비어있다...", () -> showSimpleMessage("비어있다..."));

        // 상단 서랍 클릭 이벤트
        roomPanel.addClickableArea(390, 320, 120, 70, "비어있다...", () -> showSimpleMessage("비어있다..."));

        // 하단 서랍 클릭 이벤트
        roomPanel.addClickableArea(390, 410, 120, 70, "비어있다...", () -> showSimpleMessage("비어있다..."));
    }

    /**
     * 간단한 메시지를 표시
     */
    private void showSimpleMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "알림",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    /**
     * 병실 내부 구조를 그리는 패널
     */
    private class RoomDrawingPanel extends JPanel {
        private final java.util.List<ClickableArea> clickableAreas = new java.util.ArrayList<>();

        public RoomDrawingPanel() {
            setOpaque(false); // 배경을 투명하게 설정하여 뒤에 추가된 라벨을 가리지 않음

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (ClickableArea area : clickableAreas) {
                        if (area.contains(e.getX(), e.getY())) {
                            if (area.action != null) {
                                area.action.run();
                            }
                            break;
                        }
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    boolean onClickableArea = false;
                    for (ClickableArea area : clickableAreas) {
                        if (area.contains(e.getX(), e.getY())) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            onClickableArea = true;
                            break;
                        }
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

        /**
         * 클릭 가능한 영역 추가
         */
        public void addClickableArea(int x, int y, int width, int height, String message, Runnable action) {
            clickableAreas.add(new ClickableArea(x, y, width, height, message, action));
        }
    }

    /**
     * 클릭 가능한 영역을 정의하는 클래스
     */
    private static class ClickableArea {
        int x, y, width, height;
        String message;
        Runnable action;

        public ClickableArea(int x, int y, int width, int height, String message, Runnable action) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.message = message;
            this.action = action;
        }

        public boolean contains(int clickX, int clickY) {
            return clickX >= x && clickX <= x + width && clickY >= y && clickY <= y + height;
        }
    }
}
