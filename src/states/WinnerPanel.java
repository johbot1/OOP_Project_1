package states;

/**
 * WinnerPanel
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 *  @desc The Winner Panel is a congraulatory panel the player sees once completing the game
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import helpers.PanelInterface;

public class WinnerPanel extends JPanel implements PanelInterface {
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JButton menuButton;
    private JButton exitButton;
    private Image backgroundImage;

    public WinnerPanel(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(600, 600));
        backgroundImage = new ImageIcon(getClass().getResource("/Winner.png")).getImage();

        setLayout(new BorderLayout()); // Set the BorderLayout

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
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

        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    @Override
    public void changePanel() {
        MenuPanel menu = new MenuPanel(frame);
        frame.setContentPane(menu);
        frame.revalidate();

    }

}
