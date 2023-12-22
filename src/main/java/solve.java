import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class solve extends JFrame {
    private JTextField filename = new JTextField("",5), dir = new JTextField("",5);

    private JButton open = new JButton("Open"), save = new JButton("Save");
    private JTextArea fileshow = new JTextArea(5,5);

    public solve() {
        JPanel p = new JPanel();
        open.addActionListener(new OpenL());
        p.add(open);
        save.addActionListener(new SaveL());
        p.add(save);
        Container cp = getContentPane();
        cp.add(p, BorderLayout.SOUTH);

        JLabel fileNameLabel = new JLabel("<HTML><U>" + "hello.txt" + "</U></HTML>");
        JTextPane pane = new JTextPane();
        pane.setContentType("text/html");

        pane.setText("<html><h1>My First Heading</h1><p>My first paragraph.</p></body></html>");

        dir.setEditable(false);
        filename.setEditable(false);
        p = new JPanel();
        p.setLayout(new GridLayout(5, 1));
        p.add(filename);
        p.add(dir);
        p.add(fileNameLabel);
        p.add(pane);
        cp.add(p, BorderLayout.NORTH);
    }

    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(solve.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().toString());
                dir.setText(c.getCurrentDirectory().toString());
                fileshow.append("Hello");
                fileshow.append("World");
                JButton btn = new JButton(c.getSelectedFile().toString());


//                fileshow.append(String.valueOf(fileNameLabel));
                for (File file : c.getSelectedFiles()) {
                    fileshow.append(file.getPath() + System.getProperty("line.separator"));
                }
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    }

    class SaveL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fileName ="user.txt";
            File dbFile = new File("database.txt");
            JFileChooser c = new JFileChooser(new File(fileName));
            // Demonstrate "Save" dialog:
            c.setSelectedFile(dbFile);
            int rVal = c.showSaveDialog(solve.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    }

    public static void main(String[] args) {
        run(new solve(), 250, 110);
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
} ///:~
