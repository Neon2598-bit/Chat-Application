import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ChatApplication extends JFrame {

    public static ArrayList<Message> sharedMessages = new ArrayList<>();

    public static ArrayList<ChatWindow> allChatWindows = new ArrayList<>();

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color MESSAGE_SENDER_COLOR = new Color(46, 204, 113);
    private static final Color MESSAGE_RECEIVER_COLOR = new Color(255, 255, 255);

    public ChatApplication() {
        setTitle("Chat Application - Add Participants");
        setSize(600,500);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10)); // 10px Margins
        add(mainPanel);
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20,20,20,20)); // 20px Margins

        //Title
        JLabel titleLabel = new JLabel ("Welcome TO Chat Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //Center Panel With Instruction And Input
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(new EmptyBorder(30,40,30,40));

        JLabel instructionLabel = new JLabel ("Enter Participant Names To Start Chatting : ");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(instructionLabel);

        centerPanel.add (Box.createRigidArea(new Dimension(0,20))); // Space(20px verticaly) Between Instruction And Input Field

        //Name Input Field
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setMaximumSize(new Dimension(300,40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR,2), // Line Border 'Blue color' with size 2px
                BorderFactory.createEmptyBorder(5,10,5,10) // Space Between Line Border And Text
        ));
        centerPanel.add(nameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //Add Participant Button
        JButton addButton = new JButton ("Add Participant");
        addButton.setFont(new Font("Segoe UI",Font.BOLD,16));
        addButton.setBackground(PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setPreferredSize(new Dimension(200,45));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setCursor(new Cursor(Cursor.MOVE_CURSOR));

        //Hover Effect
        addButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(SECONDARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(PRIMARY_COLOR);
            }
        });

        centerPanel.add(addButton);

        centerPanel.add(Box.createRigidArea(new Dimension(0,30)));

        //Participant Count Label
        JLabel participantCountLabel = new JLabel ("Active Participans : 0 ", SwingConstants.CENTER);
        participantCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        participantCountLabel.setForeground(new Color(127,140,141));
        participantCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(participantCountLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button Action
        ActionListener addParticipantAction = e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                // Create New Chat Window For THis Participant
                ChatWindow chatWindow = new ChatWindow(name);
                allChatWindows.add(chatWindow);
                chatWindow.setVisible(true);

                // Update Participant Count
                participantCountLabel.setText("Active Participans : " + allChatWindows.size());

                // Cleat Te Field
                nameField.setText("");
                nameField.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please Enter A Participant Name!","Name Required",JOptionPane.WARNING_MESSAGE);
            }
        };

        addButton.addActionListener(addParticipantAction);
        nameField.addActionListener(addParticipantAction);

        add(mainPanel);
    }

    // Method To Broadcast Message To All ChatWindows
    public static void broadcastMessage(Message message) {
        sharedMessages.add(message);
        for (ChatWindow window : allChatWindows) {
            window.updateDisplay();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ChatApplication().setVisible(true));
    }
}

// Message Class To Store Message Details
class Message {
    private String senderName;
    private String message;
    private String timeStamp;

    Message(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
        this.timeStamp = java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("hh:mm")
        );
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}

// Chat Window Class For Each Participant
class ChatWindow extends JFrame {
    private String participantName;
    private JTextArea messageArea;
    private JTextField messageField;
    private JButton sendButton;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CHAT_BACKGROUND = new Color(255, 255, 255);
    private static final Color SENDER_COLOR = new Color(46, 204, 113);

    ChatWindow(String participantName) {
        this.participantName = participantName;

        setTitle ("Chat With " + participantName);
        setSize(500,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel (new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder (new EmptyBorder(15,20,15,20));

        // HeaderPanel BorderLayout Ekata West Side Ekata Add Kara 'Typing' Label Eka
        JLabel headerLabel = new JLabel ("Typing : "+ participantName);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // HeaderPanel BorderLayout Ekata East Side Ekata Add Kara 'Online' Label Eka
        JLabel onlineLabel = new JLabel ("* Online");
        onlineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        onlineLabel.setForeground(new Color (46,204,113));
        headerPanel.add(onlineLabel, BorderLayout.EAST);

        mainPanel.add (headerPanel, BorderLayout.NORTH);

        // Chat Display Area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBackground(CHAT_BACKGROUND);
        messageArea.setBorder(new EmptyBorder(20,20,20,20));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add (scrollPane, BorderLayout.CENTER);

        // Input Area
        JPanel inputPanel = new JPanel(new BorderLayout(10,0));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(new EmptyBorder(15,15,15,15));

        messageField = new JTextField();
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189,195,199),2),
                BorderFactory.createEmptyBorder(10,15,10,15)
        ));

        sendButton = new JButton ("Send =>");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sendButton.setBackground(PRIMARY_COLOR);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorderPainted(false);
        sendButton.setPreferredSize(new Dimension(100,45));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                sendButton.setBackground(SECONDARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                sendButton.setBackground(PRIMARY_COLOR);
            }
        });

        inputPanel.add (messageField,BorderLayout.CENTER);
        inputPanel.add (sendButton,BorderLayout.EAST);

        mainPanel.add (inputPanel,BorderLayout.SOUTH);

        // Send Messages
        ActionListener sendMessage = e -> {
            String messageText = messageField.getText().trim();

            if (!messageText.isEmpty()) {
                Message messaage = new Message (participantName, messageText);
                ChatApplication.broadcastMessage(messaage);

                messageField.setText("");
                messageField.requestFocus();
            }
        };

        sendButton.addActionListener(sendMessage);
        messageField.addActionListener(sendMessage);

        add (mainPanel);

        // Update The Display With Existing Messages
        updateDisplay();
    }

    public void updateDisplay () {
        messageArea.setText("");

        for (Message message : ChatApplication.sharedMessages) {
            String formattedMessage;

            if (message.getSenderName().equals(participantName)) {

                formattedMessage = String.format("[%s] You : %s\n\n",
                        message.getTimeStamp(), message.getMessage());

            } else {
                formattedMessage = String.format ("[%s] %s : %s\n\n",
                        message.getTimeStamp(), message.getSenderName(), message.getMessage());
            }

            messageArea.append(formattedMessage);
        }

        // Auto-Scroll To Bottom
        messageArea.setCaretPosition (messageArea.getDocument().getLength());
    }
}