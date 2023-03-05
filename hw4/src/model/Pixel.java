package model;

import java.util.ArrayList;

/**
 * Represents a pixel of an image.
 */
public class Pixel {
  private int redComponent;
  private int greenComponent;
  private int blueComponent;
  private int alphaComponent;
  static final int maxValue = 255;


  /**
   * Creates a color using rgb values.
   *
   * @param redComponent   the red pixel value
   * @param greenComponent the greenComponent pixel value
   * @param blueComponent  the blue pixel value
   * @throws IllegalArgumentException if the components are negative
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent)
      throws IllegalArgumentException {
    if (redComponent < 0 || greenComponent < 0 || blueComponent < 0) {
      throw new IllegalArgumentException("The components can't be negative");
    }
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = 255;
  }

  /**
   * Creates a color using rgb values and an alpha component for transparency.
   *
   * @param redComponent   the red pixel value
   * @param greenComponent the green pixel value
   * @param blueComponent  the blue pixel value
   * @throws IllegalArgumentException if the components are negative
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent, int alphaComponent)
      throws IllegalArgumentException {
    if (redComponent < 0 || greenComponent < 0 || blueComponent < 0 || alphaComponent < 0) {
      throw new IllegalArgumentException("The components can't be negative");
    }
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = alphaComponent;
  }


  /**
   * Returns the red component of this color.
   * @return the red component of this color
   */
  public int getRedComponent() {
    return this.redComponent;
  }

  /**
   * Returns the green component of this color.
   * @return the green component of this color
   */
  public int getGreenComponent() {
    return this.greenComponent;
  }

  /**
   * Returns the blue component of this color.
   * @return the blue component of this color
   */
  public int getBlueComponent() {
    return this.blueComponent;
  }

  /**
   * Returns the alpha component of this color.
   * @return the alpha component of this color
   */
  public int getAlphaComponent() {
    return this.alphaComponent;
  }

  /**
   * Returns the maximum value of the rgb component.
   * @return the maximum value of the rgb component
   */
  public int value() {
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }


  /**
   * Returns the average of the rgb components.
   * @return the average of the rgb components
   */
  public int intensity() {
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  /**
   * Returns the weighted sum of the rgb components.
   * @return the weighted sum of the rgb components
   */
  public int luma() {
    return (int) Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
            + (0.0722 * this.blueComponent));
  }

  /**
   * Modifies the component by adding or subtracting it by a given value.
   * @param component the component to change
   * @param value the value to be applied to the component
   * @param add true if and only if the value is being added to and false otherwise which makes it
   *            subtract instead
   * @throws IllegalArgumentException if the given component is null or if the given value is
   *                                  less than zero
   */
  public void modifyComponent(String component, int value, boolean add)
      throws IllegalArgumentException {
    if (component == null || value < 0) {
      throw new IllegalArgumentException("the component cannot be null and the value cannot be "
          + "negative");
    }
    if (add) {
      this.add(component, value);
    }
    else {
      this.subtract(component, value);
    }
  }

  /**
   * Adds the value to the given component.
   * @param component the component to be added to
   * @param value the value to add to the component
   */
  private void add(String component, int value) {
    switch (component) {
      case "red":
        this.redComponent += value;
        this.checkBounds(component);
        break;
      case "blue":
        this.blueComponent += value;
        this.checkBounds(component);
        break;
      case "green":
        this.greenComponent += value;
        this.checkBounds(component);
        break;
      default:
        throw new IllegalArgumentException("choose one of the three components to add to");
    }
  }

  /**
   * Subtracts the value to the given component.
   * @param component the component to be added to
   * @param value the value to add to the component
   */
  private void subtract(String component, int value) {
    switch (component) {
      case "red":
        this.redComponent -= value;
        this.checkBounds(component);
        break;
      case "blue":
        this.blueComponent -= value;
        this.checkBounds(component);
        break;
      case "green":
        this.greenComponent -= value;
        this.checkBounds(component);
        break;
      default:
        throw new IllegalArgumentException("choose one of the three components to add to");
    }
  }

  /**
   * Checks if the given component is higher than the max value or if it's lower than zero and set
   * it to either the max value if it exceeds it or set it to zero if it goes below zero.
   * @param component the component to check the bounds for
   */
  private void checkBounds(String component) {
    switch (component) {
      case "red":
        if (this.redComponent > 255) {
          this.redComponent = 255;
        }
        if (this.redComponent < 0) {
          this.redComponent = 0;
        }
        break;
      case "blue":
        if (this.blueComponent > 255) {
          this.blueComponent = 255;
        }
        if (this.blueComponent < 0) {
          this.blueComponent = 0;
        }
        break;
      case "green":
        if (this.greenComponent > 255) {
          this.greenComponent = 255;
        }
        if (this.greenComponent < 0) {
          this.greenComponent = 0;
        }
        break;
      default:
        throw new IllegalArgumentException("choose one of the three components to check");
    }
  }






}
