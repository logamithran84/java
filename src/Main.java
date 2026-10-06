import javax.swing.*;
import java.awt.*;
import java.io.File;

class SwingFileList extends JFrame {

    JTextField pathField;
    JButton listButton;
    JList<String> fileList;
    JLabel sizeLabel;
    DefaultListModel<String> listModel;

    File currentDirectory;

    public SwingFileList() {

        setTitle("Swing File List");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ---------------- TOP PANEL ----------------
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        JLabel pathLabel = new JLabel("Directory Path:");
        pathLabel.setFont(new Font("Arial", Font.BOLD, 16));

        pathField = new JTextField();
        pathField.setFont(new Font("Arial", Font.PLAIN, 16));

        listButton = new JButton("List Files");
        listButton.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(pathLabel, BorderLayout.WEST);
        topPanel.add(pathField, BorderLayout.CENTER);
        topPanel.add(listButton, BorderLayout.EAST);

        // ---------------- FILE LIST ----------------
        JLabel filesLabel = new JLabel("Files:");
        filesLabel.setFont(new Font("Arial", Font.BOLD, 16));

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(fileList);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(filesLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // ---------------- SIZE LABEL ----------------
        sizeLabel = new JLabel("Selected File Size: ");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sizeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(sizeLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // List Files button event
        listButton.addActionListener(e -> listFiles());

        // File selection event
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showFileSize();
            }
        });
    }

    // Method to list files
    private void listFiles() {

        String path = pathField.getText().trim();

        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a directory path.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        currentDirectory = new File(path);

        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid directory path!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        listModel.clear();

        File[] files = currentDirectory.listFiles();

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No files found in the directory.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                listModel.addElement(file.getName());
            }
        }

        sizeLabel.setText("Selected File Size: ");
    }

    // Method to display selected file size
    private void showFileSize() {

        String selectedFile = fileList.getSelectedValue();

        if (selectedFile == null) {
            return;
        }

        File file = new File(currentDirectory, selectedFile);

        long size = file.length();

        sizeLabel.setText(
                "Selected File Size: " + size + " bytes"
        );
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            SwingFileList frame = new SwingFileList();
            frame.setVisible(true);
        });
    }
}