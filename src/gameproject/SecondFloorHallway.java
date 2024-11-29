package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SecondFloorHallway extends JPanel {
    private final MainFrame mainFrame;
    private final List<ClickableArea> clickableAreas = new ArrayList<>();

    public SecondFloorHallway(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(Color.BLACK);
        setLayout(null);

        // 위치 라벨 (오른쪽 위)
        JLabel locationLabel = new JLabel("병동 2층 복도", SwingConstants.RIGHT);
        locationLabel.setForeground(new Color(0xBBF3C0));
        locationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        locationLabel.setBounds(680, 10, 180, 30); // 오른쪽 위에 배치
        add(locationLabel);

        // 마우스 이벤트 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (ClickableArea area : clickableAreas) {
                    if (area.contains(e.getX(), e.getY())) {
                        area.action.run();
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

        // 클릭 가능한 영역 추가
        addClickableArea(20, 100, 180, 400, () -> mainFrame.switchToPanel(new ElevatorScreen(mainFrame))); // 엘리베이터
        addClickableArea(220, 150, 150, 350, () -> mainFrame.switchToPanel(new Room201(mainFrame))); // 201호
        addClickableArea(390, 150, 150, 350, () -> mainFrame.switchToPanel(new Room202(mainFrame))); // 202호
        addClickableArea(560, 150, 150, 350, () -> mainFrame.switchToPanel(new Room203(mainFrame))); // 203호

        // 원장실 클릭 가능한 영역 추가
        addClickableArea(730, 150, 150, 350, () -> handleDirectorRoomEntry());
    }

    /**
     * 원장실 입장 처리
     */
    private void handleDirectorRoomEntry() {
        if (mainFrame.getInventory().hasItem("카드키")) {
            mainFrame.switchToPanel(new HospitalDirectorRoom(mainFrame)); // 원장실로 이동
        } else {
            showSimpleMessage("카드키가 필요합니다.");
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

        // 선 색상 설정 (0xBBF3C0)
        Color lineColor = new Color(0xBBF3C0);
        g.setColor(lineColor);

        // 엘리베이터
        g.drawRect(20, 100, 180, 400); // 엘리베이터 본체
        g.drawLine(110, 100, 110, 500); // 엘리베이터 문
        g.drawRect(80, 60, 60, 30); // 층수 표시 칸
        g.drawString("2", 110, 80); // 층수 텍스트 추가

        // 201호
        g.drawRect(220, 150, 150, 350); // 201호 문
        g.drawRect(230, 170, 130, 100); // 201호 창문
        g.drawRect(340, 320, 20, 40); // 201호 손잡이
        g.drawRect(265, 110, 60, 30); // 201호 호수칸
        g.drawString("201", 285, 130); // 201호 텍스트 추가

        // 202호
        g.drawRect(390, 150, 150, 350); // 202호 문
        g.drawRect(400, 170, 130, 100); // 202호 창문
        g.drawRect(510, 320, 20, 40); // 202호 손잡이
        g.drawRect(435, 110, 60, 30); // 202호 호수칸
        g.drawString("202", 455, 130); // 202호 텍스트 추가

        // 203호
        g.drawRect(560, 150, 150, 350); // 203호 문
        g.drawRect(570, 170, 130, 100); // 203호 창문
        g.drawRect(680, 320, 20, 40); // 203호 손잡이
        g.drawRect(605, 110, 60, 30); // 203호 호수칸
        g.drawString("203", 625, 130); // 203호 텍스트 추가

        // 204호
        g.drawRect(730, 150, 150, 350); // 원장실 문
        g.drawRect(740, 170, 130, 100); // 원장실 창문
        g.drawRect(850, 320, 20, 40); // 원장실 손잡이
        g.drawRect(775, 110, 60, 30); // 원장실 호수칸
        g.drawString("원장실", 787, 130); // 원장실 텍스트 추가

        // 바닥
        g.drawLine(0, 500, 900, 500);
    }

    /**
     * 클릭 가능한 영역 추가 메서드
     */
    private void addClickableArea(int x, int y, int width, int height, Runnable action) {
        clickableAreas.add(new ClickableArea(x, y, width, height, action));
    }

    /**
     * 클릭 가능한 영역 클래스
     */
    private static class ClickableArea {
        int x, y, width, height;
        Runnable action;

        public ClickableArea(int x, int y, int width, int height, Runnable action) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.action = action;
        }

        public boolean contains(int clickX, int clickY) {
            return clickX >= x && clickX <= x + width && clickY >= y && clickY <= y + height;
        }
    }
}
