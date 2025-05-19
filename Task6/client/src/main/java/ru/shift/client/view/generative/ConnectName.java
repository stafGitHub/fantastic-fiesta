/*
 * Created by JFormDesigner on Sun May 04 20:22:00 OMST 2025
 */

package ru.shift.client.view.generative;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author sasha
 */
public class ConnectName extends JFrame {
    public ConnectName() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        jPanel = new JPanel();
        jLabel = new JLabel();
        jTextField = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(400, 300));
        setPreferredSize(new Dimension(400, 300));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                //======== panel1 ========
                {
                    jPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[]" +
                        "[fill]" +
                        "[fill]" +
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
                        "[]"));

                    //---- label1 ----
                    jLabel.setText("\u0418\u043c\u044f");
                    jPanel.add(jLabel, "cell 2 4");

                    //---- textField1 ----
                    jTextField.setPreferredSize(new Dimension(200, 34));
                    jPanel.add(jTextField, "cell 4 4 8 1");
                }
                contentPanel.add(jPanel);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));

                //---- okButton ----
                okButton.setText("\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u0442\u044c\u0441\u044f");
                buttonBar.add(okButton);
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    protected JPanel dialogPane;
    protected JPanel contentPanel;
    protected JPanel jPanel;
    protected JLabel jLabel;
    protected JTextField jTextField;
    protected JPanel buttonBar;
    protected JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
