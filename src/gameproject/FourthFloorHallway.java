package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FourthFloorHallway extends JPanel {
    private final MainFrame mainFrame;
    private final List<ClickableArea> clickableAreas = new ArrayList<>();

    public FourthFloorHallway(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(Color.BLACK);
        setLayout(null);
        
        setFocusable(true); // 키 이벤트를 받을 수 있게 설정
        requestFocusInWindow();

        // 위치 라벨 (오른쪽 위)
        JLabel locationLabel = new JLabel("병동 4층 복도", SwingConstants.RIGHT);
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
        addClickableArea(220, 150, 150, 350, () -> mainFrame.switchToPanel(new Room401(mainFrame))); // 401호
        addClickableArea(390, 150, 150, 350, () -> mainFrame.switchToPanel(new Room402(mainFrame))); // 402호
        addClickableArea(560, 150, 150, 350, () -> mainFrame.switchToPanel(new Room403(mainFrame))); // 403호
        addClickableArea(730, 150, 150, 350, () -> mainFrame.switchToPanel(new Room404(mainFrame))); // 404호
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
        g.drawString("4", 110, 80); // 층수 텍스트 추가

        // 401호
        g.drawRect(220, 150, 150, 350); // 401호 문
        g.drawRect(230, 170, 130, 100); // 401호 창문
        g.drawRect(340, 320, 20, 40); // 401호 손잡이
        g.drawRect(265, 110, 60, 30); // 401호 호수칸
        g.drawString("401", 285, 130); // 401호 텍스트 추가

        // 402호
        g.drawRect(390, 150, 150, 350); // 402호 문
        g.drawRect(400, 170, 130, 100); // 402호 창문
        g.drawRect(510, 320, 20, 40); // 402호 손잡이
        g.drawRect(435, 110, 60, 30); // 402호 호수칸
        g.drawString("402", 455, 130); // 402호 텍스트 추가

        // 403호
        g.drawRect(560, 150, 150, 350); // 403호 문
        g.drawRect(570, 170, 130, 100); // 403호 창문
        g.drawRect(680, 320, 20, 40); // 403호 손잡이
        g.drawRect(605, 110, 60, 30); // 403호 호수칸
        g.drawString("403", 625, 130); // 403호 텍스트 추가

        // 404호
        g.drawRect(730, 150, 150, 350); // 404호 문
        g.drawRect(740, 170, 130, 100); // 404호 창문
        g.drawRect(850, 320, 20, 40); // 404호 손잡이
        g.drawRect(775, 110, 60, 30); // 404호 호수칸
        g.drawString("404", 795, 130); // 404호 텍스트 추가

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
