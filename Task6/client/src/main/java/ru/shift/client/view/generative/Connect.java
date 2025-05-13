/*
 * Created by JFormDesigner on Tue Apr 29 16:05:16 OMST 2025
 */

package ru.shift.client.view.generative;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author sasha
 */
public class Connect extends JFrame {
    public Connect() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        welcomePanel = new JPanel();
        welcomeText = new JLabel();
        connectPanel = new JPanel();
        label1 = new JLabel();
        formattedTextField1 = new JFormattedTextField();
        button1 = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(400, 300));
        setPreferredSize(new Dimension(400, 300));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== WelcomPanel ========
            {
                welcomePanel.setPreferredSize(new Dimension(100, 40));
                welcomePanel.setLayout(new FlowLayout());

                //---- WelcomeText ----
                welcomeText.setText("\u0414\u043e\u0431\u0440\u043e \u043f\u043e\u0436\u0430\u043b\u043e\u0432\u0430\u0442\u044c \u0432 ChatZone");
                welcomeText.setFont(new Font("Inter", Font.PLAIN, 16));
                welcomeText.setPreferredSize(new Dimension(265, 20));
                welcomeText.setHorizontalAlignment(SwingConstants.LEFT);
                welcomeText.setVerticalAlignment(SwingConstants.TOP);
                welcomePanel.add(welcomeText);
            }
            dialogPane.add(welcomePanel, BorderLayout.NORTH);

            //======== ConnectPanel ========
            {
                connectPanel.setPreferredSize(new Dimension(200, 100));
                connectPanel.setMinimumSize(new Dimension(200, 100));
                connectPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- label1 ----
                label1.setText("\u0421\u0435\u0440\u0432\u0435\u0440");
                connectPanel.add(label1, "cell 1 2");

                //---- formattedTextField1 ----
                formattedTextField1.setPreferredSize(new Dimension(200, 34));
                connectPanel.add(formattedTextField1, "cell 2 2 6 1");

                //---- button1 ----
                button1.setText("\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f");
                connectPanel.add(button1, "cell 3 6");
            }
            dialogPane.add(connectPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    protected JPanel dialogPane;
    protected JPanel welcomePanel;
    protected JLabel welcomeText;
    protected JPanel connectPanel;
    protected JLabel label1;
    protected JFormattedTextField formattedTextField1;
    protected JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
