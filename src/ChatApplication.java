import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatApplication extends JFrame {

    public static ArrayList<Message> sharedMessages = new ArrayList<>();

    public static ArrayList<chatWindow> allChatWindows = new ArrayList<>();

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
        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));

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
                BorderFactory.createLineBorder(PRIMARY_COLOR,2),
                BorderFactory.createEmptyBorder(5,10,5,10)
        ));
        centerPanel.add(nameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //Add Participant Button
        JButton addButton = new JButton ("Add Participant");


    }
}