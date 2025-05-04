/*
 * Created by JFormDesigner on Tue Apr 29 16:19:11 OMST 2025
 */

package ru.shift.client.view;

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
        Users = new JPanel();
        ScrolPanel = new JScrollPane();
        ListUsers = new JList();
        Chat = new JPanel();
        textPane = new JScrollPane();
        ChatPane = new JTextPane();
        Message = new JPanel();
        messageField = new JTextField();
        PushButton = new JButton();

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
                Users.setMinimumSize(null);
                Users.setPreferredSize(new Dimension(200, 88));
                Users.setBorder(new TitledBorder("\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0438"));
                Users.setForeground(Color.black);
                Users.setLayout(new BoxLayout(Users, BoxLayout.X_AXIS));

                //======== ScrolPanel ========
                {

                    //---- ListUsers ----
                    ListUsers.setPreferredSize(new Dimension(150, 1000));
                    ListUsers.setLayoutOrientation(JList.VERTICAL_WRAP);
                    ScrolPanel.setViewportView(ListUsers);
                }
                Users.add(ScrolPanel);
            }
            dialogPane.add(Users, BorderLayout.LINE_START);

            //======== Chat ========
            {
                Chat.setBorder(new TitledBorder("\u0427\u0430\u0442"));
                Chat.setMinimumSize(new Dimension(910, 30));
                Chat.setMaximumSize(new Dimension(2147483647, 30));
                Chat.setLayout(new BoxLayout(Chat, BoxLayout.Y_AXIS));

                //======== textPane ========
                {
                    textPane.setViewportView(ChatPane);
                }
                Chat.add(textPane);

                //======== UserMessage ========
                {
                    Message.setPreferredSize(new Dimension(900, 31));
                    Message.setMinimumSize(new Dimension(900, 31));
                    Message.setMaximumSize(new Dimension(2147483647, 30));
                    Message.setLayout(new BoxLayout(Message, BoxLayout.X_AXIS));

                    //---- messageField ----
                    messageField.setPreferredSize(new Dimension(200, 31));
                    messageField.setMinimumSize(new Dimension(900, 31));
                    Message.add(messageField);

                    //---- PushButton ----
                    PushButton.setText("push");
                    Message.add(PushButton);
                }
                Chat.add(Message);
            }
            dialogPane.add(Chat, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    protected JPanel dialogPane;
    protected JPanel Users;
    protected JScrollPane ScrolPanel;
    protected JList ListUsers;
    protected JPanel Chat;
    protected JScrollPane textPane;
    protected JTextPane ChatPane;
    protected JPanel Message;
    protected JTextField messageField;
    protected JButton PushButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
