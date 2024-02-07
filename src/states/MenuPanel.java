package states;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * MenuPanel
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 *  @desc The Menu State is a class that lets the game know that the current state of the game is Menu, meaning that
 *       the player can interact with the game but in a very limited fashion, and only while this state is active. It 
 *       uses the StateMethods interface which is the default for all Game State classes.
 */
import javax.swing.JPanel;

import helpers.PanelInterface;

public class MenuPanel extends JPanel implements PanelInterface {
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JButton startButton;
    private JButton exitButton;
    private Image backgroundImage;

    public MenuPanel(JFrame mainFrame) {
        this.frame = mainFrame;
        setPreferredSize(new Dimension(600, 600));
        backgroundImage = new ImageIcon(getClass().getResource("/SUDOKU1.png")).getImage();

        setLayout(new BorderLayout()); // Set the BorderLayout

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel();
            }
        });
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        return buttonPanel;

    }

    @Override
    public void changePanel() {
        DifficultyPanel difficultyPanel = new DifficultyPanel(frame);
        frame.setContentPane(difficultyPanel);
        frame.revalidate();

    }
}
