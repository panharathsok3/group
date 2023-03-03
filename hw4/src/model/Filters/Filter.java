package model.Filters;

public class Filter {

  private final String filterType; //the type of filter.

  public Filter(String filterType) {
    this.filterType = filterType;
  }

  @Override
  public String toString() {
    return this.filterType;
  }



}
