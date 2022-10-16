package random;

import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements ActionListener {

	JLabel background;
	JLabel bomb;
	JLabel bombText;
	JButton start;
	MenuBar mb = new MenuBar();
	Menu file = new Menu("File");

	MenuItem file_new = new MenuItem("Insert item");
	MenuItem file_close = new MenuItem("Exit");
	List<String> list = new ArrayList<>();

	static boolean isStartFlag = true;

	public MainFrame() {
		initData();
		setInitLayout();
		addActionListener();
	}

	private void initData() {
		setSize(710, 540);
		setTitle("랜덤뽑기 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		background = new JLabel(new ImageIcon("images/background.png"));
		start = new JButton(new ImageIcon("images/start2.png"));
		bomb = new JLabel(new ImageIcon("images/bomb9.png"));
		bombText = new JLabel();

	}

	private void setInitLayout() {
		setVisible(true);

		// 메뉴
		file.add(file_new);
		file.add(file_close);
		mb.add(file);
		setMenuBar(mb);

		background.setLocation(0, 0);
		background.setSize(710, 540);
		add(background);

		start.setBounds(580, 380, 110, 100);
		start.setBorderPainted(false);
		start.setContentAreaFilled(false);
		background.add(start);

		bomb.setSize(200, 200);
		bomb.setLocation(300, 120);
	}

	private void addActionListener() {
		start.addActionListener(this);
		file.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		System.out.println(e.getActionCommand());

		// 메뉴 아이템 누르면
		// 새파일 - 추첨 항목 변경

		switch (cmd) {
		case "Insert item":
			new Write();
			file.setEnabled(false);
			break;
		case "Exit":
			System.exit(0);
			break;
		}

		if (e.getSource() == start) {
			background.add(bomb);

			Random random = new Random();
			if (list.size() > 0) {
				int result = random.nextInt(list.size());
				System.out.println(result);
				bombText.setText(list.get(result));

				repaint();
			} else {
				bombText.setText("데이터없음 ");
			}
			bombText.setSize(200, 50);
			bombText.setLocation(50, 100);
			bombText.setFont(new Font("맑은고딕", Font.BOLD, 18));
			bombText.setForeground(Color.white);
			bomb.add(bombText);
		}
		repaint();
	}

	// read 메서드
	public String read(String readData) {
		String line = null;
		try {
			File file = new File(readData);
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);

			try {
				int i = 0;
				while ((line = bufferedReader.readLine()) != null) {
					list.add(line);

				}
			} catch (IOException e1) {

				e1.printStackTrace();
			} finally {
				try {
					bufferedReader.close();
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		return line;
	}

	// read 메서드 2
	public String readData(String readData) {
		String a = "";
		try (FileReader fr = new FileReader(readData)) {
			int i;

			while ((i = fr.read()) != -1) {
				// string 으로 받기위해 a의 값을 더해줌
				a = a + (char) i;
			}
			// 텍스트 에리어 화면에 띄움
		} catch (Exception e) {
			System.out.println("에러");
		}
		return a;
	}

	// write 메서드
	public static void write(String writeData, String data) {

		try (FileWriter fw = new FileWriter(writeData)) {

			fw.write("");
			fw.write(data);

		} catch (Exception e) {
			System.out.println("에러");
		}
	}

	class Write extends JFrame implements ActionListener {
		JTextArea textArea = new JTextArea(readData("test.txt"));
		JButton button = new JButton("저장 ");
		JLabel background = new JLabel(new ImageIcon("images/mini.png"));

		public Write() {

			setSize(300, 300);
			setTitle("New File");
			setVisible(true);
			// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			textArea.setFont(new Font("맑은고딕", Font.PLAIN, 12));
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setBounds(30, 60, 230, 120);
			add(scrollPane);
			button.setBounds(180, 20, 85, 30);

			button.setBorderPainted(false);
			button.setContentAreaFilled(false);
			add(button);
			button.addActionListener(this);
			background.setSize(300, 300);
			background.setLocation(0, 0);
			add(background);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button) {
				write("test.txt", "");
				write("test.txt", textArea.getText());
			}
			list.clear();
			read("test.txt");
			dispose();
			file.setEnabled(true);
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
