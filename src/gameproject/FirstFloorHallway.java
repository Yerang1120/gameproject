package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FirstFloorHallway extends JPanel {
    private final MainFrame mainFrame;
    private final List<ClickableArea> clickableAreas = new ArrayList<>();

    public FirstFloorHallway(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(Color.BLACK);
        setLayout(null);

        // 위치 라벨 (오른쪽 위)
        JLabel locationLabel = new JLabel("병동 1층 복도", SwingConstants.RIGHT);
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
        addClickableArea(220, 150, 150, 350, () -> mainFrame.switchToPanel(new Room101(mainFrame))); // 101호
        addClickableArea(390, 150, 150, 350, () -> mainFrame.switchToPanel(new Room102(mainFrame))); // 102호
        addClickableArea(560, 150, 150, 350, () -> mainFrame.switchToPanel(new Room103(mainFrame))); // 103호

        // 간호사실 클릭 시 비밀번호 입력 처리
        addClickableArea(730, 150, 150, 350, this::handleNurseRoomAccess);
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
        g.drawString("1", 110, 80); // 층수 텍스트 추가

        // 101호
        g.drawRect(220, 150, 150, 350); // 101호 문
        g.drawRect(230, 170, 130, 100); // 101호 창문
        g.drawRect(340, 320, 20, 40); // 101호 손잡이
        g.drawRect(265, 110, 60, 30); // 101호 호수칸
        g.drawString("101", 285, 130); // 101호 텍스트 추가

        // 102호
        g.drawRect(390, 150, 150, 350); // 102호 문
        g.drawRect(400, 170, 130, 100); // 102호 창문
        g.drawRect(510, 320, 20, 40); // 102호 손잡이
        g.drawRect(435, 110, 60, 30); // 102호 호수칸
        g.drawString("102", 455, 130); // 102호 텍스트 추가

        // 103호
        g.drawRect(560, 150, 150, 350); // 103호 문
        g.drawRect(570, 170, 130, 100); // 103호 창문
        g.drawRect(680, 320, 20, 40); // 103호 손잡이
        g.drawRect(605, 110, 60, 30); // 103호 호수칸
        g.drawString("103", 625, 130); // 103호 텍스트 추가

        // 간호사실
        g.drawRect(730, 150, 150, 350); // 간호사실 문
        g.drawRect(740, 170, 130, 100); // 간호사실 창문
        g.drawRect(850, 320, 20, 40); // 간호사실 손잡이
        g.drawRect(775, 110, 60, 30); // 간호사실 호수칸
        g.drawString("간호사실", 780, 130); // 간호사실 텍스트 추가

        // 바닥
        g.drawLine(0, 500, 900, 500);
    }

    /**
     * 간호사실 접근 처리
     */
    private void handleNurseRoomAccess() {
        String password = JOptionPane.showInputDialog(
            this,
            "비밀번호를 입력하세요:",
            "간호사실",
            JOptionPane.PLAIN_MESSAGE
        );

        if (password == null) {
            return; // 사용자가 취소를 누른 경우 아무 일도 하지 않음
        }

        if (password.equals("2847")) {
            mainFrame.switchToPanel(new NurseRoom(mainFrame)); // 비밀번호가 맞으면 간호사실로 이동
        } else {
            JOptionPane.showMessageDialog(
                this,
                "비밀번호가 틀렸습니다.",
                "오류",
                JOptionPane.ERROR_MESSAGE
            );
        }
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
