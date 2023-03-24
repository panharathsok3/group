package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import model.ILayer;
import model.IPixel;

public class JFrameView extends JFrame implements GUIView, ActionListener {

  private JPanel mainPanel, imagePanel, currentLayers;

  private JScrollPane mainScrollPane;
  private JButton newProject, addLayer, addImageToLayer, setFilter, saveProject, saveImage, load;
  private JComboBox<String> effectsOptions;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private int height;
  private int width;
  private int layerNum;
  private JList<ILayer> listOfLayers;
  private JList<Integer> layerNumbers;


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

    //Commands and image effects
    JPanel commands = new JPanel();
    commands.setLayout(new GridLayout());
    commands.setBorder(BorderFactory.createTitledBorder("Effects"));
    commands.setBackground(Color.LIGHT_GRAY);


    this.addImageToLayer = new JButton("Add an image to a Layer");
    this.addImageToLayer.setActionCommand("add-image-to-layer");
    this.addImageToLayer.addActionListener(this);

    this.load = new JButton("Load A Project");
    this.load.setActionCommand("load-project");
    this.load.addActionListener(this);

    this.saveProject = new JButton("Save A Project");
    this.saveProject.setActionCommand("save-project");
    this.saveProject.addActionListener(this);

    this.newProject = new JButton("New Project");
    this.newProject.setActionCommand("new-project");
    this.newProject.addActionListener(this);

    this.saveImage = new JButton("Save an Image");
    this.saveImage.setActionCommand("save-image");
    this.saveImage.addActionListener(this);

    this.addLayer = new JButton("Add a new Layer");
    this.addLayer.setActionCommand("add-layer");
    this.addLayer.addActionListener(this);
    this.layerNum = 1;

    //a drop-down menu to show the list of filer options.
    this.effectsOptions = new JComboBox<>(
            new String[]
                    {"Normal",
                            "Brighten-value", "Brighten-luma", "Brighten-intensity", "Darken-value",
                            "Darken-luma", "Darken-intensity", "Red-Component", "Green-Component",
                            "Blue-Component", "Inversion-difference", "Brightening-screen", "Darken-multiply"});

    this.effectsOptions.setActionCommand("set-filter");
    this.effectsOptions.addActionListener(this);

    this.setFilter = new JButton("Set a filter on a Layer");
    this.setFilter.setActionCommand("set-filter");
    this.setFilter.addActionListener(this);


    //commands
    commands.add(this.newProject);
    commands.add(this.addLayer);
    commands.add(this.addImageToLayer);
    commands.add(this.setFilter);
    commands.add(this.saveProject);
    commands.add(this.saveImage);
    commands.add(this.load);
    commands.add(this.effectsOptions);

    this.mainPanel.add(commands, BorderLayout.SOUTH);


    //Layers selection list
    this.currentLayers = new JPanel();
    currentLayers.setBorder(BorderFactory.createTitledBorder("List of Layers"));
    currentLayers.setLayout(new BoxLayout(currentLayers, BoxLayout.X_AXIS));
    this.mainPanel.add(currentLayers);


    JLabel layerMessage = new JLabel();
    layerMessage = new JLabel("Layers on your project will appear here");
    currentLayers.add(layerMessage);


    //pack();
    setVisible(true);

  }

  /**
   * Helper method that displays an error message to the screen.
   * SIDE EFFECTS: Displays a pop-up box that has a descriptive error message
   * if the user is trying to perform an action that is not supported.
   *
   * @param message the specific message relating to the command that has been invoked.
   */
  public void errorMessage(String message) {
    JOptionPane.showMessageDialog(null,
            message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void displayMessage(String message) {
    JLabel msgLabel = new JLabel();
    msgLabel.setText(message);
  }

  /**
   * Updates the view every time something new is displayed.
   * SIDE EFFECTS : based on whatever action is invoked.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    switch (arg0.getActionCommand()) {
      case "new-project":
//        this.projectName = JOptionPane.showInputDialog("Enter your project name");
        break;
      case "save-image":
        //String a = JOptionPane.showInputDialog("Enter something");
        break;
      case "load-project":
        break;
      case "save-project":
        break;
      case "add-layer":
        this.layerNum++;
        DefaultListModel<Integer> dataForListOfIntegers = new DefaultListModel<>();
        // for (int i = 0; i < layerNum; i++)
        dataForListOfIntegers.addElement(layerNum);
        layerNumbers = new JList<>(dataForListOfIntegers);
        layerNumbers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currentLayers.add(layerNumbers +"Layer",new JButton());
        break;
      case "add-image-to-layer":
        break;
      case "set-filter":
        break;
      default:
        errorMessage("Action doesn't exist");
        setVisible(true);
        throw new IllegalStateException("action doesn't exist");

    }
  }


  /**
   * These are call backs that respond to operations on buttons in the view.
   * SIDE EFFECTS: performs an event based on what button on a panel is pressed.
   *
   * @param features the object we are calling in this function to make the view operational.
   */
  @Override
  public void addFeatures(Features features) {

    this.newProject.addActionListener(e -> {
      try {
        features.newProject(
                JOptionPane.showInputDialog("Enter your project name"),
                JOptionPane.showInputDialog("Enter the height"),
                JOptionPane.showInputDialog("Enter your width"));
      } catch (IllegalStateException ise) {
        errorMessage(ise.getMessage());
      }
    });


    this.addLayer.addActionListener(e -> {
      try {
        features.addLayer("Layer " + layerNum);
      } catch (IllegalStateException ise) {
        errorMessage(ise.getMessage());
      }
    });


    this.saveImage.addActionListener(e -> {
      final JFileChooser fileChooser = new JFileChooser("./");
      int returnValue = fileChooser.showSaveDialog(JFrameView.this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String fileName = file.getAbsolutePath();
        try {
          features.saveImage(fileName);
        } catch (IllegalStateException ise) {
          errorMessage(ise.getMessage());
        }
      }
    });


    this.saveProject.addActionListener(e -> {
      final JFileChooser fileChooser = new JFileChooser("./");
      int returnValue = fileChooser.showSaveDialog(JFrameView.this);


      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String fileName = file.getAbsolutePath();

        int slash = file.getAbsolutePath().lastIndexOf(File.separator);
        int dot = file.getAbsolutePath().lastIndexOf(".");
        String filePath = file.getAbsolutePath().substring(slash + 1, dot);


        try {
          //(JOptionPane.showInputDialog("SAVE AS: "))
          features.saveProject(fileName, filePath);
        } catch (IllegalStateException ise) {
          errorMessage(ise.getMessage());
        }
      }
    });



    this.load.addActionListener(e -> {
      JFileChooser fileChooser =
              new JFileChooser("./src");

      FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter
              ("PPM, JPEG & JPG ", "ppm", "jpeg", "jpg");

      fileChooser.setFileFilter(extensionFilter);
      int returnValue = fileChooser.showOpenDialog(JFrameView.this);

      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        int slash = file.getAbsolutePath().lastIndexOf(File.separator);
        int dot = file.getAbsolutePath().lastIndexOf(".");

        String filePath = file.getAbsolutePath().substring(slash + 1, dot);
        try {
          features.loadProject(filePath);
        } catch (IllegalStateException ise) {
          errorMessage(ise.getMessage());
        }
        //remove this
        this.refresh();
      }
    });

    this.addImageToLayer.addActionListener(e -> {

      try {
        features.addImageToLayer
                (JOptionPane.showInputDialog("Enter the layer you want to add the image to"),
                        JOptionPane.showInputDialog(load),
                        JOptionPane.showInputDialog("Enter the x position"),
                        JOptionPane.showInputDialog("Enter  the y position"));
      } catch (IllegalStateException ise) {
        errorMessage(ise.getMessage());
      }

    });


    Map<String, Consumer<Features>> effectOptions =
            this.getActionsForCommands();

    this.setFilter.addActionListener(e -> {
      int optionIndex = JFrameView.this.effectsOptions.getSelectedIndex();
      String option = JFrameView.this.effectsOptions.getItemAt(optionIndex);

      if (effectOptions.containsKey(option)) {
        Consumer<Features> commands = effectOptions.get(option);
        commands.accept(features);
      } else {
        errorMessage("Action doesn't exist");
      }
    });


  }

  private Map<String, Consumer<Features>> getActionsForCommands() {
    Map<String, Consumer<Features>> effectOptions =
            new HashMap<>();

    effectOptions.put("Brighten", features -> {
      String[] effects = {"luma", "intensity", "value"};
      int chosen = JOptionPane.showOptionDialog(null,
              "Pick one", "Color picker",
              JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
              effects, effects[0]);

//      this.setFilter.addActionListener(e -> features.setFilter
//              (JOptionPane.showInputDialog("Enter the layer you want to transform name"),
//                      JOptionPane.showInputDialog("Enter the filter you want to apply")));

      String ext = effects[chosen].toLowerCase();
      String layerName = null;

      switch (effects[chosen]) {
        case "red-component":
      }
    });
    return effectOptions;
  }


  @Override
  public Image getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        int r = imageToAdd.get(x).get(y).getRedComponent();
        int g = imageToAdd.get(x).get(y).getGreenComponent();
        int b = imageToAdd.get(x).get(y).getBlueComponent();

        int a = imageToAdd.get(x).get(y).getAlphaComponent();
        if (y * image.getWidth() + x >= 35000) {
          a = 100;
        }
        if (y * image.getWidth() + x >= 90000) {
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
