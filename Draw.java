import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Draw extends JFrame {
    private DrawPanel drawPanel;
    private JButton clearButton, eraserButton;
    private JComboBox<String> colorPicker;
    private JSlider brushSizeSlider;

    public Draw() {
        setTitle("Drawing Pad");
        setSize(700, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        // Toolbar for extra features
        JPanel toolbar = new JPanel();
        
        // Color Picker Dropdown
        String[] colors = {"Black", "Red", "Green", "Blue", "Orange", "Pink"};
        colorPicker = new JComboBox<>(colors);
        colorPicker.addActionListener(e -> drawPanel.setColor((String) colorPicker.getSelectedItem()));
        toolbar.add(new JLabel("Color: "));
        toolbar.add(colorPicker);

        // Brush Size Slider
        brushSizeSlider = new JSlider(1, 20, 5);
        brushSizeSlider.setMajorTickSpacing(5);
        brushSizeSlider.setPaintTicks(true);
        brushSizeSlider.setPaintLabels(true);
        brushSizeSlider.addChangeListener(e -> drawPanel.setBrushSize(brushSizeSlider.getValue()));
        toolbar.add(new JLabel("Brush Size: "));
        toolbar.add(brushSizeSlider);

        // Eraser Button
        eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> drawPanel.setEraser());
        toolbar.add(eraserButton);

        // Clear Canvas Button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> drawPanel.clearCanvas());
        toolbar.add(clearButton);

        add(toolbar, BorderLayout.NORTH);

        setVisible(true);
    }

    class DrawPanel extends JPanel {
        private int prevX, prevY;
        private boolean drawing = false;
        private Color currentColor = Color.BLACK;
        private int brushSize = 5;
        private Image image;
        private Graphics2D graphics2D;

        public DrawPanel() {
            setBackground(Color.WHITE);

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                    drawing = true;
                }
                public void mouseReleased(MouseEvent e) {
                    drawing = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (drawing) {
                        if (graphics2D == null) {
                            image = createImage(getSize().width, getSize().height);
                            graphics2D = (Graphics2D) image.getGraphics();
                            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            graphics2D.setColor(Color.WHITE);
                            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
                            graphics2D.setColor(currentColor);
                        }

                        graphics2D.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        graphics2D.drawLine(prevX, prevY, e.getX(), e.getY());
                        prevX = e.getX();
                        prevY = e.getY();
                        repaint();
                    }
                }
            });
        }

        public void setColor(String colorName) {
            switch (colorName) {
                case "Red": currentColor = Color.RED; break;
                case "Green": currentColor = Color.GREEN; break;
                case "Blue": currentColor = Color.BLUE; break;
                case "Orange": currentColor = Color.ORANGE; break;
                case "Pink": currentColor = Color.PINK; break;
                default: currentColor = Color.BLACK; break;
            }
            graphics2D.setColor(currentColor);
        }

        public void setEraser() {
            currentColor = Color.WHITE; // Set to background color
            graphics2D.setColor(currentColor);
        }

        public void setBrushSize(int size) {
            brushSize = size;
        }

        public void clearCanvas() {
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setColor(currentColor);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
        }
    }
}
