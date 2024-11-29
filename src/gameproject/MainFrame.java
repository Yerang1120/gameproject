package gameproject;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Inventory inventory; // 아이템 창
    private CollectionScreen collectionScreen; // 수집 화면
    private boolean inventoryVisible; // 아이템 창 표시 여부

    public MainFrame() {
        setTitle("GENEX Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 배치
        setResizable(false);
        setLayout(null); // 절대 레이아웃으로 설정

        // 아이템 창 초기화
        inventory = new Inventory();
        inventory.setBounds(0, 0, 900, 600); // Inventory 위치와 크기 설정
        add(inventory);
        inventory.setVisible(false); // 초기에는 숨김

        // 수집 화면 초기화
        collectionScreen = new CollectionScreen(this);

        // 초기 화면 설정
        gameStartScreen startScreen = new gameStartScreen(this);
        startScreen.setBounds(0, 0, 900, 600);
        add(startScreen);

        setVisible(true);
    }

    /**
     * 패널 전환 메서드
     */
    public void switchToPanel(JPanel panel) {
        getContentPane().removeAll(); // 기존 컴포넌트 제거
        panel.setBounds(0, 0, 900, 600); // 새 패널 위치와 크기 설정
        add(panel);

        // 항상 Inventory와 CollectionScreen을 추가하고 Z-order 조정
        add(inventory);
        add(collectionScreen);
        setComponentZOrder(inventory, 0); // Inventory를 최상위로 설정
        inventory.setVisible(inventoryVisible); // 현재 상태에 따라 가시성 설정

        panel.setFocusable(true); // 패널에서 키 이벤트를 받을 수 있도록 설정
        panel.requestFocusInWindow(); // 패널에 포커스 요청
        panel.addKeyListener(new KeyAdapterForInventory()); // 키 이벤트 전달

        revalidate();
        repaint();
    }

    /**
     * 아이템 창 열기/닫기 토글
     */
    public void toggleInventory() {
        inventoryVisible = !inventoryVisible;
        inventory.setVisible(inventoryVisible);
        if (inventoryVisible) {
            inventory.updateItems(); // 아이템 목록 업데이트
        }
    }

    /**
     * Inventory 객체를 반환
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * CollectionScreen 객체를 반환
     */
    public CollectionScreen getCollection() {
        return collectionScreen;
    }

    /**
     * KeyAdapter 클래스: E 키를 처리
     */
    private class KeyAdapterForInventory extends java.awt.event.KeyAdapter {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_E) {
                toggleInventory(); // 아이템 창 열기/닫기
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new); // MainFrame 실행
    }
}
