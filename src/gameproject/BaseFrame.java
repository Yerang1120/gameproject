package gameproject;

import javax.swing.*;

public class BaseFrame extends JFrame {
    public BaseFrame(String title, int width, int height) {
        setTitle(title); // 창 제목 설정
        setSize(width, height); // 창 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 동작 설정

        // 화면 중앙에 위치
        setLocationRelativeTo(null);
    }
}
