import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
/**
 * Simple demo to show off Byteman Thermostat interface
 */
public class Demo extends JFrame
{
    private static final long serialVersionUID = -6249169437391023105L;
    private static DateFormat timeFormat =  DateFormat.getTimeInstance();
    private JTextArea textArea;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    public Demo()
    {
        super("Demo");

        Dimension d = new Dimension(450, 500);
        setSize(d);
        setPreferredSize(d);
        setMinimumSize(d);
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // control panel contains buttons to run slow and fast tasks
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        JButton fast = new JButton("fast");
        fast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Demo.this.runFastTask();
            }
        });
        JButton slow = new JButton("slow");
        slow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Demo.this.runSlowTask();
            }
        });
        controlPanel.add(slow);
        controlPanel.add(fast);

        // create a scrollable text area
        final Insets paddingInsets = new Insets(5, 5, 5, 5);
        textArea = new JTextArea();
        textArea.setName("Task Timings");
        textArea.setMargin(paddingInsets);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(new LineBorder(Color.BLACK));
        textArea.setMinimumSize(new Dimension(300, 400));
        textArea.setPreferredSize(new Dimension(300, 400));
        // JScrollPane scrollpane = new JScrollPane(textArea);

        // add the control panel and the scrollable
        // text area to this frame

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        add(controlPanel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.95;
        add(textArea, gridBagConstraints);
        // add(scrollpane, gridBagConstraints);
    }


    private static void createAndShowGui() {
        Demo demo = new Demo();
        demo.pack();
        demo.setVisible(true);
    }

    private void runFastTask()
    {
        runTask(getFastTask());
    }

    private void runSlowTask()
    {
        runTask(getSlowTask());
    }

    private void runTask(Task task)
    {
        String name = task.getName();
        long start = System.currentTimeMillis();
        appendText(timeFormat.format(new Date(start)));
        appendText(" task " + name + " started\n");
        try {
            task.start();
            task.join();
        } catch (Exception e) {
            // do nothing
        }
        long end = System.currentTimeMillis();
        appendText(timeFormat.format(new Date(end)));
        appendText(" task " + name + " endeded\n");
        appendText(" " + (end - start) + " msecs elapsed\n");
    }

    private void appendText(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(text);
            }
        });
    }

    public Task getFastTask() {
        return new Task("fast") {
            @Override
            public void run() {
                doWork(39);
                ioWait();
                doWork(40);
                ioWait();
                doWork(41);
                ioWait();
            }
        };
    }

    public Task getSlowTask() {
        return new Task("slow") {
            @Override
            public void run() {
                doWork(35);
                ioWait();
                doWork(40);
                ioWait();
                doWork(45);
                ioWait();
            }
        };
    }
}
