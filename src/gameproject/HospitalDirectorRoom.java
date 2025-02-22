package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HospitalDirectorRoom extends JPanel {
    private final MainFrame mainFrame;
    private boolean isGasMaskTaken = false; // 방독면이 이미 가져갔는지 여부

    public HospitalDirectorRoom(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true); // 키 이벤트를 받을 수 있게 설정
        requestFocusInWindow();

        // 병실 내부 구조를 표시하는 패널 추가 (배경 역할)
        RoomDrawingPanel roomPanel = new RoomDrawingPanel();
        roomPanel.setBounds(0, 0, 900, 600);
        add(roomPanel);

        // 오른쪽 위에 현재 위치 표시 라벨 추가
        JLabel locationLabel = new JLabel("원장실", SwingConstants.RIGHT);
        locationLabel.setForeground(new Color(0xBBF3C0));
        locationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        locationLabel.setBounds(680, 10, 180, 30); // 오른쪽 위에 배치
        add(locationLabel); // 반드시 `roomPanel` 뒤에 추가
        setComponentZOrder(locationLabel, 0); // `locationLabel`을 항상 최상위로 설정

        // 문 클릭 이벤트
        roomPanel.addClickableArea(50, 70, 300, 430, "복도로 이동",
            () -> mainFrame.switchToPanel(new SecondFloorHallway(mainFrame)));

        // 책상 위 서랍 클릭 이벤트
        roomPanel.addClickableArea(430, 310, 300, 40, "방독면이 들어있다.\n가져가시겠습니까?",
            this::handleGasMaskPickup);

        // 책상 옆 서랍 클릭 이벤트
        roomPanel.addClickableArea(750, 360, 80, 140, "비어있다...",
            () -> showSimpleMessage("비어있다..."));
    }

    /**
     * 책상 위 서랍 클릭 시 방독면을 가져가는 처리
     */
    private void handleGasMaskPickup() {
        if (isGasMaskTaken) {
            showSimpleMessage("비어있다...");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
            this,
            "방독면이 들어있다.\n가져가시겠습니까?",
            "책상 위 서랍",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            isGasMaskTaken = true; // 방독면을 가져간 상태로 설정
            mainFrame.getInventory().addItem("방독면"); // 아이템 창에 방독면 추가
            mainFrame.getInventory().updateItems(); // 아이템 창 갱신
            showSimpleMessage("방독면을 가져갔습니다.");
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

    /**
     * 병실 내부 구조를 그리는 패널
     */
    private class RoomDrawingPanel extends JPanel {
        private final java.util.List<ClickableArea> clickableAreas = new java.util.ArrayList<>();

        public RoomDrawingPanel() {
            setOpaque(false); // 투명 배경으로 설정
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

            // 책상
            g.drawRect(400, 300, 450, 200); // 책상 본체
            g.drawRect(430, 360, 300, 140); // 책상 앞 서랍
            g.drawRect(430, 310, 300, 40); // 책상 위 서랍
            g.drawRect(750, 360, 80, 140); // 책상 옆 서랍
            g.drawOval(570, 322, 20, 20); // 책상 위 서랍 손잡이
            g.drawOval(760, 420, 20, 20); // 책상 옆 서랍 손잡이
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
