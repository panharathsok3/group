package model.Filters;

public class Filter {

  private final String filterType; //the type of filter.


  private final String layerName; //the layer to apply this filter on.
  public Filter(String layerName,String filterType) {
    this.layerName = layerName;
    this.filterType = filterType;
  }

  @Override
  public String toString() {
    return this.filterType;
  }

  //should override hash code here since we are overriding to string.


}
