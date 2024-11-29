package gameproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ending4 extends JPanel {
    private final MainFrame mainFrame;
    private int dialogueIndex = 0;

    private final String[] dialogues = {
        "지하 1층에 도착하자, 자욱한 연기가 흘러들어왔다.",
        "발견한 방독면을 쓰고 이리저리 둘러보자, 잔혹한 실험의 결과물일 '무언가'들이 보였다.",
        "'이건...말도안돼...'",
        "실험실 내부에 있는 전화기로 구조요청을 한 나는, 무사히 탈출할 수 있었다.",
        "..",
        "...속보입니다. 생명공학 회사로 유명한 GENEX CORPERATION에서 사람을 상대로 한 인체실험이 진행되고 있었다는 소식입니다.",
        "생존자 OO씨의 증언으로 사건이 밝혀지게 되었으며...",
        "..."
    };

    public Ending4(MainFrame mainFrame) {
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
        JLabel endingLabel = new JLabel("엔딩 4", SwingConstants.CENTER);
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
        	mainFrame.getCollection().unlockEnding(3, "엔딩 4"); // 첫 번째는 인덱스, 두 번째는 엔딩 이름

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
