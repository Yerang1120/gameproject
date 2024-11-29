package gameproject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends JPanel {
    private final List<String> items; // 아이템 목록
    private final JTextArea itemList;

    public Inventory() {
        items = new ArrayList<>();
        setBackground(Color.BLACK);
        setLayout(null);
        setBounds(0, 0, 900, 600);

        // 아이템 창 제목
        JLabel title = new JLabel("아이템 창", SwingConstants.CENTER);
        title.setForeground(new Color(0xBBF3C0));
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setBounds(0, 20, 900, 30);
        add(title);

        // 아이템 목록 표시
        itemList = new JTextArea();
        itemList.setEditable(false);
        itemList.setBackground(Color.BLACK);
        itemList.setForeground(new Color(0xBBF3C0));
        itemList.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        itemList.setBounds(100, 70, 700, 400);
        add(itemList);
    }

    /**
     * 아이템 추가 메서드
     */
    public void addItem(String itemName) {
        items.add(itemName);
    }

    /**
     * 특정 아이템이 있는지 확인하는 메서드
     */
    public boolean hasItem(String itemName) {
        return items.contains(itemName);
    }

    /**
     * 아이템 목록 업데이트
     */
    public void updateItems() {
        StringBuilder itemText = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemText.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        itemList.setText(itemText.toString());
    }
}
