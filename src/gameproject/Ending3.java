package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ending3 extends JPanel {
    private final MainFrame mainFrame;
    private int dialogueIndex = 0;

    private final String[] dialogues = {
        "지하 1층에 내리자, 앞이 보이지 않을 정도로 자욱한 연기가 흘러나왔다.",
        "연기를 점점 들이마시자, 눈앞이 점차 흐릿해져갔다.",
        "...",
        "그리고 저 멀리서, 누군가 걸어오는 것이 보였다.",
        "그 사람은, 아니 사람인가?",
        "사람이라고는 생각할 수 없는 외형을 가진 '무언가'가 나의 팔을 붙잡고 안쪽으로 질질 끌고갔다.",
        "..."
    };

    public Ending3(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.BLACK);
        setLayout(null);

        DialoguePanel dialoguePanel = new DialoguePanel();
        dialoguePanel.setBounds(30, 350, 840, 100);
        add(dialoguePanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dialogueIndex < dialogues.length - 1) {
                    dialogueIndex++;
                    dialoguePanel.repaint();
                } else if (dialogueIndex == dialogues.length - 1) {
                    dialogueIndex++;
                    remove(dialoguePanel);
                    repaint();
                    showEndingText();
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    private void showEndingText() {
        JLabel endingLabel = new JLabel("Metamorphosis", SwingConstants.CENTER);
        endingLabel.setForeground(new Color(0xBBF3C0));
        endingLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        endingLabel.setBounds(0, 200, 900, 100);
        add(endingLabel);

        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        backButton.setBackground(new Color(0x333333));
        backButton.setForeground(new Color(0xBBF3C0));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        backButton.addActionListener(e -> {
        	mainFrame.getCollection().unlockEnding(2, "엔딩 3"); // 첫 번째는 인덱스, 두 번째는 엔딩 이름
            mainFrame.switchToPanel(new gameStartScreen(mainFrame)); // 시작 화면으로 이동
        });
        add(backButton);

        repaint();
    }

    private class DialoguePanel extends JPanel {
        public DialoguePanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 직사각형 박스
            g.setColor(new Color(0x333333));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(0xBBF3C0));
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

            // 텍스트
            g.setColor(new Color(0xBBF3C0));
            g.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
            drawTextWithLineBreaks(g, dialogues[dialogueIndex], 10, 30, getWidth() - 20);
        }

        private void drawTextWithLineBreaks(Graphics g, String text, int x, int y, int maxWidth) {
            FontMetrics metrics = g.getFontMetrics();
            int lineHeight = metrics.getHeight();
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                if (metrics.stringWidth(line + word) > maxWidth) {
                    g.drawString(line.toString(), x, y);
                    y += lineHeight;
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                }
            }
            g.drawString(line.toString(), x, y);
        }
    }
}
