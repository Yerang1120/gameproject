package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gameStartScreen extends JPanel {
    private final MainFrame mainFrame;

    public gameStartScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        // 제목
        JLabel titleLabel = new JLabel("GENEX CORP.", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0xBBF3C0));
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBounds(300, 100, 300, 50);
        add(titleLabel);

        // 게임 시작
        JLabel startLabel = createClickableLabel("게임시작", 350, 250);
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.switchToPanel(new Room404(mainFrame)); // Room404로 이동
            }
        });
        add(startLabel);

        // 튜토리얼
        JLabel tutorialLabel = createClickableLabel("튜토리얼", 350, 320);
        tutorialLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.switchToPanel(new TutorialScreen(mainFrame)); // TutorialScreen으로 이동
            }
        });
        add(tutorialLabel);

        // 수집
        JLabel collectionLabel = createClickableLabel("수집", 350, 390);
        collectionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.switchToPanel(new CollectionScreen(mainFrame)); // CollectionScreen으로 이동
            }
        });
        add(collectionLabel);

        // 게임 종료
        JLabel exitLabel = createClickableLabel("게임종료", 350, 460);
        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0); // 프로그램 종료
            }
        });
        add(exitLabel);
    }

    /**
     * 클릭 가능한 JLabel 생성
     */
    private JLabel createClickableLabel(String text, int x, int y) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        label.setBounds(x, y, 200, 30);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }
}
