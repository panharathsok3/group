package model.Effects;

import model.Layer;

/**
 * A command that assigns a specified filter to a layer.
 */
public class BulkAssignFilter {

  Layer layer;
  int startRow;

  int startCol;

  int endRow;
  int endCol;

  public void executesFilter() {
    // should iterate through row and columns and set values
    //i to length of rows
    //j to length of columns

    for(int i = this.startRow; i < this.endRow; i++) {
      for(int j = this.startCol; i < this.endCol; i++) {
        //get all the pixels on that layer and apply a filter onto it.

      }
    }


  }





}
