package view;

import controller.Features;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.IPixel;

public class JFrameView extends JFrame implements GUIView, ActionListener {

  private JPanel mainPanel, imagePanel;
  private JScrollPane mainScrollPane;
  private JButton newProject, addLayer, addImageToLayer, setFilter, saveProject, saveImage, load;
  private JComboBox<String> effectsOptions;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private int height;
  private int width;
  private String projectName;

  public JFrameView() {
    super();
    this.setTitle("Collager Project");

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int height = (int) screenSize.getHeight();
    int width = (int) screenSize.getWidth();

    this.height = (int) (height / 1.5f);
    this.width = width / 2;

    this.setSize(width, height);
    this.setLayout(new FlowLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    //scroll bars around this main panel
    this.mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);


    //image panel
    this.imagePanel = new JPanel();
    this.mainPanel.add(this.imagePanel);

    //show an image with a scrollbar

    //a border around the panel with a caption
    this.imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    this.imagePanel.setLayout(new GridLayout());
    //imagePanel.setMaximumSize(null);

    this.imageLabel = new JLabel();
    this.imageScrollPane = new JScrollPane(this.imageLabel);

    this.imageLabel.setIcon(new ImageIcon());

    this.imageScrollPane.setPreferredSize(new Dimension(this.width, this.height));
    this.imagePanel.add(this.imageScrollPane);

//    String[] images = {"src/swingdemo/Jellyfish.jpg"};
//    JLabel[] imageLabel = new JLabel[images.length];
//    JScrollPane[] imageScrollPane = new JScrollPane[images.length];
//
//    JLabel imageLabel = new JLabel();
//    JScrollPane imageScrollPane = new JScrollPane();
//    imageLabel.setIcon(new ImageIcon(images[0]));
//    imagePanel.add(imageLabel);
//
//    for (int i = 0; i < imageLabel.length; i++) {
//      imageLabel[i] = new JLabel();
//      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
//
//      imageLabel[i].setIcon(new ImageIcon(images[i]));
//
//      if(i < images.length) {
//        imageLabel[i].setIcon(new ImageIcon(images[i]));
//      } else {
//        imageLabel[i].setIcon(new ImageIcon(createImageFromScratch()));
//      }
//
//      imageScrollPane[i].setPreferredSize(new Dimension(this.width, this.height));
//      this.imagePanel.add(imageScrollPane[i]);
//    }

    //Commands and image effects
    JPanel commands = new JPanel();
    commands.setLayout(new GridLayout());
    commands.setBorder(BorderFactory.createTitledBorder("Effects"));
    commands.setBackground(Color.LIGHT_GRAY);

    this.saveProject = new JButton("Save A Project");
    this.addLayer = new JButton("Add a new Layer");
    this.addImageToLayer = new JButton("Add an image to a Layer");
    this.setFilter = new JButton("Set a filter on a Layer");
    this.load = new JButton("LoadA Project ");
    this.saveImage = new JButton("Save an Image");
    this.newProject = new JButton("New Project");

    this.newProject.setActionCommand("new-project");
    this.newProject.addActionListener(this);

    //a drop-down menu to show the list of filer options.
    this.effectsOptions = new JComboBox<>(
            new String[] {"Brighten-value","Brighten-luma","Brighten-intensity","Darken-value",
                    "Darken-luma","Darken-intensity","Red-Component", "Green-Component",
                    "Blue-Component", "Inversion-difference", "Brightening-screen","Darken-multiply"});

    commands.add(this.newProject);
    commands.add(this.addLayer);
    commands.add(this.addImageToLayer);
    commands.add(this.setFilter);
    commands.add(this.saveProject);
    commands.add(this.saveImage);
    commands.add(this.load);
    commands.add(this.effectsOptions);

    this.mainPanel.add(commands, BorderLayout.SOUTH);


   //pack();
    setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    switch (arg0.getActionCommand()) {
      case "new-project":
        this.projectName = JOptionPane.showInputDialog("Enter your project name");
        break;
      default:
        throw new IllegalStateException("action doesn't exist");
    }
  }

  /**
   * These are call backs that respond to operations on buttons in the view.
   * SIDE EFFECTS: performs an event based on what button on a panel is pressed.
   * @param features the object we are calling in this function to make the view operational.
   */
  @Override
  public void addFeatures(Features features) {
    this.newProject.addActionListener(e -> features.newProject(this.projectName));
    this.load.addActionListener(e -> {
       JFileChooser fileChooser =
              new JFileChooser("");

      FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("PPM",
              "ppm");

      fileChooser.setFileFilter(extensionFilter);

      File fileName = fileChooser.getSelectedFile();
      String path = fileName.getAbsolutePath();
      try{
        features.loadProject(path);
      } catch (IllegalStateException exception) {
        throw new IllegalStateException(exception.getMessage());
      }
    });
  }

  @Override
  public Image getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for(int x = 0; x < image.getWidth(); x++) {
      for(int y = 0; y < image.getHeight(); y++) {
        int r = imageToAdd.get(x).get(y).getRedComponent();
        int g = imageToAdd.get(x).get(y).getGreenComponent();
        int b = imageToAdd.get(x).get(y).getBlueComponent();

        int a = imageToAdd.get(x).get(y).getAlphaComponent();
        if(y * image.getWidth() + x >= 35000) {
          a = 100;
        }
        if (y * image.getWidth() +x >= 90000) {
          a = 0;
        }
        if (y * image.getWidth() + x >= 110000) {
          a = 255;
        }

        int argb = a << 24;
        argb |= r << 16;
        argb |= g << 8;
        argb |= b;
        image.setRGB(x, y, argb);
      }
    }
    return image;
  }

  @Override
  public void displayImage(Image image) {
    this.imageLabel.setIcon(new ImageIcon(image));
    this.repaint();
  }

  @Override
  public int getImageBorderHeight() {
    return this.height;
  }

  @Override
  public int getImageBorderWidth() {
    return this.width;
  }
}
