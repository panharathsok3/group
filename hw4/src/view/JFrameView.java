package view;

import controller.Features;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFrameView extends JFrame implements GUIView {

  private JPanel mainPanel = new JPanel();;
  private JScrollPane mainScrollPane;
  private JButton newProject, addLayer, addImageToLayer, setFilter, saveProject, saveImage, load;
  private JComboBox<String> effectsOptions;

  public JFrameView() {
    super();
    this.setTitle("Collager Project");

    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

    int height = (int) screensize.getHeight();
    int width = (int) screensize.getWidth();

    this.setSize(width, height);
    this.setLayout(new FlowLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));


    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);


    //image panel
    JPanel imagePanel = new JPanel();
    mainPanel.add(imagePanel);

    //show an image with a scrollbar

    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout());
    //imagePanel.setMaximumSize(null);


    String[] images = {"src/swingdemo/Jellyfish.jpg"};
    JLabel[] imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];

//    JLabel imageLabel = new JLabel();
//    JScrollPane imageScrollPane = new JScrollPane();
//    imageLabel.setIcon(new ImageIcon(images[0]));
//    imagePanel.add(imageLabel);

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);

      imageLabel[i].setIcon(new ImageIcon(images[i]));

//      if(i < images.length) {
//        imageLabel[i].setIcon(new ImageIcon(images[i]));
//      } else {
//        imageLabel[i].setIcon(new ImageIcon(createImageFromScratch()));
//      }

      imageScrollPane[i].setPreferredSize(new Dimension(width / 2, height / 2));
      imagePanel.add(imageScrollPane[i]);
    }

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

    //adding to the bottom of the main panel
    mainPanel.add(commands, BorderLayout.SOUTH);


   //pack();
    setVisible(true);

  }

  /**
   * These are call backs that respond to operations on buttons in the view.
   * SIDE EFFECTS: performs an event based on what button on a panel is pressed.
   * @param features the object we are calling in this function to make the view operational.
   */
  @Override
  public void addFeatures(Features features) {

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
}
