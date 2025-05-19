/*
 * Created by JFormDesigner on Tue Apr 29 16:19:11 OMST 2025
 */

package ru.shift.client.view.generative;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author sasha
 */
public class Chat extends JFrame {
    public Chat() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        usersPanel = new JPanel();
        jScrollPane = new JScrollPane();
        listUsers = new JList();
        chatPanel = new JPanel();
        textPane = new JScrollPane();
        chatPane = new JTextPane();
        messagePanel = new JPanel();
        messageField = new JTextField();
        pushButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(800, 500));
        setPreferredSize(new Dimension(800, 500));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== Users ========
            {
                usersPanel.setMinimumSize(null);
                usersPanel.setPreferredSize(new Dimension(200, 88));
                usersPanel.setBorder(new TitledBorder("\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0438"));
                usersPanel.setForeground(Color.black);
                usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.X_AXIS));

                //======== ScrolPanel ========
                {

                    //---- listUsers ----
                    listUsers.setPreferredSize(new Dimension(150, 1000));
                    listUsers.setLayoutOrientation(JList.VERTICAL_WRAP);
                    jScrollPane.setViewportView(listUsers);
                }
                usersPanel.add(jScrollPane);
            }
            dialogPane.add(usersPanel, BorderLayout.LINE_START);

            //======== Chat ========
            {
                chatPanel.setBorder(new TitledBorder("\u0427\u0430\u0442"));
                chatPanel.setMinimumSize(new Dimension(910, 30));
                chatPanel.setMaximumSize(new Dimension(2147483647, 30));
                chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
                chatPane.setEditable(false);

                //======== textPane ========
                {
                    textPane.setViewportView(chatPane);
                }
                chatPanel.add(textPane);

                //======== Message ========
                {
                    messagePanel.setPreferredSize(new Dimension(900, 31));
                    messagePanel.setMinimumSize(new Dimension(900, 31));
                    messagePanel.setMaximumSize(new Dimension(2147483647, 30));
                    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));

                    //---- messageField ----
                    messageField.setPreferredSize(new Dimension(200, 31));
                    messageField.setMinimumSize(new Dimension(900, 31));
                    messagePanel.add(messageField);

                    //---- PushButton ----
                    pushButton.setText("push");
                    messagePanel.add(pushButton);
                }
                chatPanel.add(messagePanel);
            }
            dialogPane.add(chatPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    protected JPanel dialogPane;
    protected JPanel usersPanel;
    protected JScrollPane jScrollPane;
    protected JList listUsers;
    protected JPanel chatPanel;
    protected JScrollPane textPane;
    protected JTextPane chatPane;
    protected JPanel messagePanel;
    protected JTextField messageField;
    protected JButton pushButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
