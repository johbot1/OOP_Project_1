/**
 * Menu
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 *  @desc  An interface for common panel functionality that can be implemented by various panel classes.
*
 */
package helpers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface PanelInterface {
    JFrame frame = null; // Reference to the main frame
    Image backgroundImage = null; // Background image for the panel

    /**
     * Sets the preferred size of the panel.
     *
     * @param d The dimension representing the preferred size.
     */
    public void setPreferredSize(Dimension d);

    /**
     * Sets the layout manager for the panel.
     *
     * @param layout The layout manager to be set.
     */
    public void setLayout(LayoutManager layout);

    /**
     * Creates a button panel for the panel.
     *
     * @return JPanel containing buttons or controls.
     */
    public JPanel createButtonPanel();

    /**
     * Override the paintComponent method to perform custom painting of the panel.
     *
     * @param g The graphics context for painting.
     */
    public void paintComponent(Graphics g);

    /**
     * Method to change the panel or navigate to another panel within the
     * application.
     */
    public void changePanel();
}
