package view;

import controller.Features;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFrameView extends JFrame implements GUIView {

  private JPanel mainPanel = new JPanel();;
  private JScrollPane mainScrollPane;
  private JButton save;

  private JButton saveImage;

  private JButton load ;

  private JButton newProject;

  private JComboBox<String> effectsOptions;

  public JFrameView() {
    super();
    this.setTitle("Collager Project");
    this.setSize(400, 400); //TODO
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


    String[] images = {"src/swingdemo/Jellyfish.jpg", "src/swingdemo/Koala.jpg",
            "src/swingdemo/Penguins.jpg"};
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

      imageScrollPane[i].setPreferredSize(new Dimension(350, 500));
      imagePanel.add(imageScrollPane[i]);
    }

    //Commands and image effects
    JPanel commands = new JPanel();
    commands.setLayout(new GridLayout());
    commands.setBorder(BorderFactory.createTitledBorder("Effects"));
    commands.setBackground(Color.LIGHT_GRAY);

    this.save = new JButton("Save A Project");
    this.load = new JButton("LoadA Project ");
    this.saveImage = new JButton("Save an Image");
    this.newProject = new JButton("New Project");

    //a drop-down menu to show the list of filer options.
    this.effectsOptions = new JComboBox<>(
            new String[] {"Brighten-value","Brighten-luma","Brighten-intensity","Darken-value",
                    "Darken-luma","Darken-intensity","Red-Component", "Green-Component",
                    "Blue-Component", "Inversion-difference", "Brightening-screen","Darken-multiply"});

    commands.add(save);
    commands.add(saveImage);
    commands.add(load);
    commands.add(newProject);
    commands.add(effectsOptions);

    //adding to the bottom of the main panel
    mainPanel.add(commands,BorderLayout.SOUTH);


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
