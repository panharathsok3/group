package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Features;
import model.IPixel;

/**
 * The GUI that the user will interact with and uses the Features interface as the controller which
 * performs actions from the listeners in the GUI.
 */
public class JFrameView extends JFrame implements GUIView, ActionListener, ListSelectionListener {

  private JPanel mainPanel, imagePanel;
  private JScrollPane mainScrollPane;
  private JButton newProject, addLayer, addImageToLayer, setFilter, saveProject, saveImage, load;
  private JComboBox<String> effectsOptions;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private JList<String> listOfStrings;
  private int height;
  private int width;
  private int layerNum;
  private String currSelectedLayer, currSelectedFilter;
  private DefaultListModel<String> dataForListOfStrings;

  /**
   * The GUI that will be displayed to the user. Also sets up listeners and other things to display.
   */
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

    int scaleHeight = 300;
    int scaleWidth = 400;
    this.mainScrollPane.setPreferredSize(new Dimension(width - scaleWidth,
        height - scaleHeight));
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
    commands.setBorder(BorderFactory.createTitledBorder("Actions"));
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

    //Selection lists
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    this.mainPanel.add(selectionListPanel);

    this.dataForListOfStrings = new DefaultListModel<>();
    this.listOfStrings = new JList<>(this.dataForListOfStrings);
    this.listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.listOfStrings.addListSelectionListener(this);
    selectionListPanel.add(listOfStrings);

    //a drop-down menu to show the list of filter options.
    this.effectsOptions = new JComboBox<>(new String[]{"normal", "brighten-value",
        "brighten-luma", "brighten-intensity", "darken-value", "darken-luma",
        "darken-intensity", "red-component", "green-Component", "blue-component",
        "inversion-difference", "brightening-screen", "darken-multiply"});

    this.effectsOptions.setActionCommand("set-filter");
    this.effectsOptions.addActionListener(this);

    this.setFilter = new JButton("Set filter on current Layer");
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


    setVisible(true);

  }

  public void errorMessage(String message) {
    JOptionPane.showMessageDialog(null,
            message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    switch (arg0.getActionCommand()) {
      case "new-project":
      case "save-image":
      case "load-project":
      case "save-project":
      case "add-image-to-layer":
        break;
      case "add-layer":
        this.layerNum++;
        DefaultListModel<Integer> dataForListOfIntegers = new DefaultListModel<>();
        dataForListOfIntegers.addElement(layerNum);
        break;
      case "set-filter":
        int optionIndex = this.effectsOptions.getSelectedIndex();
        this.currSelectedFilter = this.effectsOptions.getItemAt(optionIndex);
        break;
      default:
        this.errorMessage("Action doesn't exist");
        setVisible(true);
        throw new IllegalStateException("action doesn't exist");

    }
  }

  @Override
  public void addFeatures(Features features) {

    this.newProject.addActionListener(e -> {
      try {
        features.newProject(
            JOptionPane.showInputDialog("Enter your project name"),
            JOptionPane.showInputDialog("Enter the height"),
            JOptionPane.showInputDialog("Enter the width"),
            JOptionPane.showInputDialog("Does your project have an alpha value? Answer yes or no"));
        this.resetLayers();
        this.dataForListOfStrings.addElement("Background");
        this.currSelectedLayer = "Background";
      } catch (IllegalArgumentException ex) {
        //do nothing
      }

    });


    this.addLayer.addActionListener(e -> {
      try {
        this.throwErrorIfProjectNotMade(features);

        String layerName = "Layer " + this.layerNum;
        features.addLayer(layerName);
        this.dataForListOfStrings.addElement(layerName);
        this.currSelectedLayer = layerName;
      } catch (IllegalArgumentException ex) {
        //do nothing
      } catch (IllegalStateException ex) {
        this.errorMessage("A project has not been made");
      }
    });


    this.saveImage.addActionListener(e -> {
      try {
        this.throwErrorIfProjectNotMade(features);
        features.saveImage(this.returnFilePathOfSelectedFile());
      } catch (IllegalStateException ex) {
        this.errorMessage("A project has not been made");
      } catch (IllegalArgumentException ex) {
        //do nothing
      }
    });


    this.saveProject.addActionListener(e -> {
      try {
        this.throwErrorIfProjectNotMade(features);

        String projectType = JOptionPane.showInputDialog("What kind of project is this? eg: ppm, "
            + "png, jpeg, etc. We currently only support ppm");

        features.saveProject(this.returnFilePathOfSelectedFile(), projectType);
      } catch (IllegalStateException ex) {
        this.errorMessage("A project has not been made");
      } catch (IllegalArgumentException ex) {
        //do nothing
      }
    });


    this.load.addActionListener(e -> {
      try {
        features.loadProject(this.returnFilePathOfSelectedFile());
      }  catch (IllegalArgumentException ise) {
        //do nothing
      }
    });

    this.addImageToLayer.addActionListener(e -> {
      try {

        this.throwErrorIfProjectNotMade(features);

        features.addImageToLayer(
            JOptionPane.showInputDialog("Enter the layer you want to add the image to"),
            this.returnFilePathOfSelectedFile(),
            JOptionPane.showInputDialog("Enter the x position"),
            JOptionPane.showInputDialog("Enter the y position"));
      } catch (IllegalStateException ex) {
        this.errorMessage("A project has not been made");
      } catch (IllegalArgumentException ex) {
        //do nothing
      }
    });

    this.setFilter.addActionListener(e -> {

      try {
        this.throwErrorIfProjectNotMade(features);
        features.setFilter(this.currSelectedLayer, this.currSelectedFilter);
      } catch (IllegalStateException ex) {
        this.errorMessage("A project has not been made");
      } catch (IllegalArgumentException ex) {
        //do nothing
      }
    });
  }

  @Override
  public void updateLayers(int layerNumber) {

    this.dataForListOfStrings.addElement("Background");
    for (int i = 1; i < layerNumber; i++) {
      this.dataForListOfStrings.addElement("Layer " + this.layerNum);
      this.layerNum++;
    }
    this.currSelectedLayer = this.dataForListOfStrings.get(layerNumber - 1);
  }

  /**
   * Returns the file path of a selected file.
   * @return the file path of a selected file
   */
  private String returnFilePathOfSelectedFile() {
    JFileChooser fileChooser = new JFileChooser("./");
    int returnValue = fileChooser.showSaveDialog(JFrameView.this);
    String filePath = null;
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      filePath = file.getAbsolutePath();
    }
    return filePath;
  }

  @Override
  public void resetLayers() {
    this.dataForListOfStrings.removeAllElements();
  }


//  private Map<String, Consumer<Features>> getActionsForCommands() {
//    Map<String, Consumer<Features>> effectOptions =
//            new HashMap<>();
//
//    effectOptions.put("Brighten", features -> {
//      String[] effects = {"luma", "intensity", "value"};
//      int chosen = JOptionPane.showOptionDialog(null,
//              "Pick one", "Color picker",
//              JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//              effects, effects[0]);
//
////      this.setFilter.addActionListener(e -> features.setFilter
////              (JOptionPane.showInputDialog("Enter the layer you want to transform name"),
////                      JOptionPane.showInputDialog("Enter the filter you want to apply")));
//
//      String ext = effects[chosen].toLowerCase();
//      String layerName = null;
//
//      switch (effects[chosen]) {
//        case "":
//      }
//    });
//    return effectOptions;
//  }

  @Override
  public void getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
        int r = imageToAdd.get(x).get(y).getRedComponent();
        int g = imageToAdd.get(x).get(y).getGreenComponent();
        int b = imageToAdd.get(x).get(y).getBlueComponent();

        int a = imageToAdd.get(x).get(y).getAlphaComponent();

        int argb = a << 24;
        argb |= r << 16;
        argb |= g << 8;
        argb |= b;
        image.setRGB(y, x, argb);
      }
    }

    this.imageLabel.setIcon(new ImageIcon(image));
    this.repaint();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    this.currSelectedLayer = this.listOfStrings.getSelectedValue();
  }

  /**
   * Helper method that throws error when the project has not been made yet.
   * @param features the Feature that is being used
   */
  private void throwErrorIfProjectNotMade(Features features) {
    if (!features.projectMade()) {
      throw new IllegalStateException("project has not been made");
    }
  }
}
